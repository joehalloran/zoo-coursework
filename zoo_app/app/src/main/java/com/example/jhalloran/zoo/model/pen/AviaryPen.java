package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.Flyer;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jhalloran on 1/9/18.
 */
public class AviaryPen extends AbstractPen implements Flyable, Serializable {
  private final int airVolume;
  private Set<Animal> animals = new HashSet<>();

  public AviaryPen(String name, int length, int width, int height, int temperature) {
    super(name, PenType.AVIARY, temperature, (length * width));
    this.airVolume = length * width * height;
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
    if (!(animal instanceof Flyer)) {
      return false;
    }
    if (!(animal.getPenTypes().contains(super.getType()))) {
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
    Flyer flyer = (Flyer) animal;
    if (flyer.getAirVolumeRequired() > calculateRemainingSpace()) {
      return false;
    }
    return true;
  }

  private int calculateRemainingSpace() {
    int airVolumeCache = airVolume;
    for (Animal animal : animals) {
      Flyer flyer = (Flyer) animal;
      airVolumeCache = airVolumeCache - flyer.getAirVolumeRequired();
    }
    return airVolumeCache;
  }
}

