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
 * A pen that can house amphibious and semiaquatic animals
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

  /**
   * @inheritDoc
   */
  @Override
  public String getName() {
    return landSection.getName();
  }

  /**
   * @inheritDoc
   */
  @Override
  public PenType getType() {
    return TYPE;
  }

  /**
   * @inheritDoc
   */
  @Override
  public int getLandArea() {
    return landSection.getLandArea();
  }

  /**
   * @inheritDoc
   */
  @Override
  public int getTemperature() {
    // Mean temperature of two sections
    return (landSection.getTemperature() + waterSection.getTemperature()) / 2;
  }

  /**
   * @inheritDoc
   */
  @Override
  public WaterType getWaterType() {
    return waterSection.getWaterType();
  }

  /**
   * @inheritDoc
   */
  @Override
  public int getWaterVolume() {
    return waterSection.getWaterVolume();
  }

  /**
   * @inheritDoc
   */
  @Override
  public Set<Animal> getAnimals() {
    return animals;
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean removeAnimal(Animal animal) {
    return animals.remove(animal);
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean addAnimal(Animal animal) {
    return canLiveHere(animal) && animals.add(animal);
  }

  /**
   * @inheritDoc
   */
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

  /**
   * @inheritDoc
   */
  public boolean isAssigned() {
    return (zookeeperAssignedTo != null);
  }

  /**
   * @inheritDoc
   */
  public Zookeeper getAssignedToZookeeper() {
    return zookeeperAssignedTo;
  }

  /**
   * @inheritDoc
   */
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
  public String toString() {
    return getName();
  }
}