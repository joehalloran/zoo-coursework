package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.Swimmer;
import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jhalloran on 1/9/18.
 */
public class AquariumPen extends AbstractPen implements Swimmable, Serializable {
  private static final PenType TYPE = PenType.AQUARIUM;
  private static final int LAND_AREA = 0;
  private final WaterType waterType;
  private final int waterVolume;
  private Set<Animal> animals = new HashSet<>();

  public AquariumPen(String name, WaterType waterType, int depth, int length, int width, int temperature) {
    super(name, temperature, LAND_AREA);
    this.waterType = waterType;
    waterVolume = depth * length * width;
  }

  @Override
  public PenType getType() {
    return TYPE;
  }

  @Override
  public WaterType getWaterType() {
    return waterType;
  }

  @Override
  public int getWaterVolume() {
    return waterVolume;
  }

  @Override
  public Set<Animal> getAnimals() {
    return animals;
  }

  @Override
  public boolean addAnimal(Animal animal) {
    return canLiveHere(animal) && animals.add(animal);
  }

  @Override
  public boolean removeAnimal(Animal animal) {
    return animals.remove(animal);
  }

  @Override
  public boolean canLiveHere(Animal animal) {
    if (!(animal instanceof Swimmer)) {
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
    if (animal.getLandAreaRequired() > LAND_AREA) {
      return false;
    }
    if (!(animal.getPenTypes().contains(TYPE))) {
      return false;
    }
    Swimmer swimmer = (Swimmer) animal;
    if (swimmer.getWaterVolumeRequired() > calculateRemainingSpace()) {
      return false;
    }
    return swimmer.getWaterTypes().contains(waterType);
  }

  private int calculateRemainingSpace() {
    int volumeCache = waterVolume;
    for (Animal animal : animals) {
      Swimmer swimmer = (Swimmer) animal;
      volumeCache = volumeCache - swimmer.getWaterVolumeRequired();
    }
    return volumeCache;
  }
}