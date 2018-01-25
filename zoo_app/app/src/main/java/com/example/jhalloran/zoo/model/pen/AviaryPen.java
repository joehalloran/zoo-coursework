package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.LandAnimal;
import com.example.jhalloran.zoo.model.animal.SwimmingAnimal;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhalloran on 1/9/18.
 */

public class AviaryPen extends Enclosure implements Flyable, Serializable {
  private static final PenType TYPE = PenType.AVIARY;
  private final int airVolume;
  private final int landArea;
  private List<Animal> animals = new ArrayList<Animal>();

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
  public List<Animal> getAnimals() {
    return animals;
  }

  @Override
  public void addAnimal(Animal animal) throws Exception {
    if (canLiveHere(animal)) {
      animals.add(animal);
    } else {
      throw new Exception("Animal not suitable for this pen");
    }
  }

  @Override
  public void removeAnimal(Animal animal) {
    animals.remove(animal);
  }

  @Override
  public boolean canLiveHere(Animal animal) {
    if (!(animal.getPenTypes().contains(TYPE))) {
      return false;
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

