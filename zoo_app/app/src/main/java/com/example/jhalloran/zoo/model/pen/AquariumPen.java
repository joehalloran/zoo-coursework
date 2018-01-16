package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.SwimmingAnimal;
import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhalloran on 1/9/18.
 */

public class AquariumPen implements Enclosable, Swimmable, Serializable {
  private static final PenType TYPE = PenType.AQUARIUM;
  private static final int LAND_AREA = 0;
  private final int temperature;
  private final WaterType waterType;
  private final int waterVolume;
  private List<Animal> animals = new ArrayList<Animal>();

  public AquariumPen(WaterType waterType, int depth, int length, int width, int temperature) {
    this.temperature = temperature;
    this.waterType = waterType;
    waterVolume = depth * length * width;
  }

  @Override
  public PenType getType() {
    return TYPE;
  }

  @Override
  public int getLandArea() {
    return LAND_AREA;
  }

  @Override
  public int getTemperature() {
    return temperature;
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
  public boolean canLiveHere(Animal animal) {
    if (!(animal instanceof SwimmingAnimal)) {
      return false;
    }
    if (animal.getWaterVolumeRequired() > calculateRemainingSpace()) {
      return false;
    }
    if (!(animal.getPenTypes().contains(TYPE))) {
      return false;
    }
    return animal.getWaterTypes().contains(waterType);
  }

  private int calculateRemainingSpace() {
    int volumeCache = waterVolume;
    for (Animal animal : animals) {
      volumeCache = volumeCache - animal.getWaterVolumeRequired();
    }
    return volumeCache;
  }

  @Override
  public String toString(){
    return TYPE.toString();
  }
}