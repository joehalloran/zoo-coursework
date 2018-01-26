package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.LandAnimal;
import com.example.jhalloran.zoo.model.animal.SwimmingAnimal;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jhalloran on 1/9/18.
 */

public class AviaryPen extends Enclosure implements Flyable, Serializable {
  private static final PenType TYPE = PenType.AVIARY;
  private final int airVolume;
  private final int landArea;
  private Set<Animal> animals = new HashSet<>();

  public AviaryPen(String name, int length, int width, int height, int temperature) {
    super(name, temperature);
    this.landArea = length * width;
    this.airVolume = length * width * height;
  }

  @Override
  public PenType getType() {
    return TYPE;
  }

  @Override
  public int getLandArea() {
    return landArea;
  }

  @Override
  public int getAirVolume() {
    return airVolume;
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
    if (animal.getAirVolumeRequired() > calculateRemainingSpace()) {
      return false;
    }
    if (animal instanceof SwimmingAnimal) {
      return false;
    }
    return !(animal instanceof LandAnimal);
  }

  private int calculateRemainingSpace() {
    int airVolumeCache = airVolume;
    for (Animal animal : animals) {
      airVolumeCache = airVolumeCache - animal.getAirVolumeRequired();
    }
    return airVolumeCache;
  }
}

