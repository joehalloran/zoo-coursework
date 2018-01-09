package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.FlyingAnimal;
import com.example.jhalloran.zoo.model.animal.SwimmingAnimal;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhalloran on 1/9/18.
 */

public class DryPen implements Enclosable {
  private static final PenType TYPE = PenType.DRY;
  private final int landArea;
  private final int temperature;
  private List<Animal> animals = new ArrayList<Animal>();

  public DryPen(int length, int width, int temperature) {
    this.landArea = length * width;
    this.temperature = temperature;
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
  public int getTemperature() {
    return temperature;
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
    if (animal.getLandAreaRequired() > calculateRemainingSpace()) {
      return false;
    }
    if (!(animal.getPenTypes().contains(TYPE))) {
      return false;
    }
    if (animal instanceof SwimmingAnimal) {
      return false;
    }
    if (animal instanceof FlyingAnimal) {
      return false;
    }
    return true;
  }

  private int calculateRemainingSpace() {
    int areaCache = landArea;
    for (Animal animal : animals) {
      areaCache = areaCache - animal.getLandAreaRequired();
    }
    return areaCache;
  }

  @Override
  public String toString(){
    return TYPE.toString();
  }
}
