package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.Swimmer;
import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jhalloran on 1/26/18.
 */
public class PartWaterPartDryPen implements Enclosable, Swimmable, Serializable {
  private static final PenType TYPE = PenType.PART_WATER_PART_DRY;
  private final DryPen landSection;
  private final AquariumPen waterSection;
  private Set<Animal> animals = new HashSet<>();
  private Zookeeper zookeeperAssignedTo = null;

  public PartWaterPartDryPen(DryPen landSection, AquariumPen waterSection) {
    this.landSection = landSection;
    this.waterSection = waterSection;
  }

  @Override
  public String getName() {
    return landSection.getName();
  }

  @Override
  public PenType getType() {
    return TYPE;
  }

  @Override
  public int getLandArea() {
    return landSection.getLandArea();
  }

  @Override
  public int getTemperature() {
    // Mean temperature of two sections
    return (landSection.getTemperature() + waterSection.getTemperature()) / 2;
  }

  @Override
  public WaterType getWaterType() {
    return waterSection.getWaterType();
  }

  @Override
  public int getWaterVolume() {
    return waterSection.getWaterVolume();
  }

  @Override
  public Set<Animal> getAnimals() {
    return animals;
  }

  @Override
  public boolean removeAnimal(Animal animal) {
    return animals.remove(animal);
  }

  @Override
  public boolean addAnimal(Animal animal) {
    return canLiveHere(animal) && animals.add(animal);
  }

  @Override
  public boolean canLiveHere(Animal animal) {
    if (!(animal instanceof Swimmer)) {
      return false;
    }
    if (!(animal.getPenTypes().contains(TYPE))) {
      return false;
    }
    if (animals.size() > 0) {
      if (animal.isDangerous()) {
        // Dangerous animals can only go into an empty pen
        return false;
      }
      if (animals.iterator().next().isDangerous()) {
        // If first animal is dangerous, no more animals can be added.
        return false;
      }
    }
    if (animal.getLandAreaRequired() > calculateRemainingLandArea()) {
      return false;
    }
    Swimmer swimmer = (Swimmer) animal;
    if (swimmer.getWaterVolumeRequired() > calculateRemainingWaterVolume()) {
      return false;
    }
    if (!(swimmer.getWaterTypes().contains(waterSection.getWaterType()))) {
      return false;
    }
    return true;
  }

  private int calculateRemainingWaterVolume() {
    int volumeCache = waterSection.getWaterVolume();
    for (Animal animal : animals) {
      Swimmer swimmer = (Swimmer) animal;
      volumeCache = volumeCache - swimmer.getWaterVolumeRequired();
    }
    return volumeCache;
  }

  private int calculateRemainingLandArea() {
    int areaCache = landSection.getLandArea();
    for (Animal animal : animals) {
      areaCache = areaCache - animal.getLandAreaRequired();
    }
    return areaCache;
  }

  public boolean isAssigned() {
    return (zookeeperAssignedTo != null);
  }

  public Zookeeper getAssignedToZookeeper() {
    return zookeeperAssignedTo;
  }

  public boolean assignToZookeeper(Zookeeper zookeeper) {
    if (zookeeper.addPen(this)) {
      if (isAssigned()) {
        zookeeperAssignedTo.removePen(this);
      }
      zookeeperAssignedTo = zookeeper;
      return true;
    }
    return false;
  }

  @Override
  public String toString(){
    return getName();
  }
}