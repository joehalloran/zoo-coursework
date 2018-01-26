package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.FlyingAnimal;
import com.example.jhalloran.zoo.model.animal.SwimmingAnimal;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jhalloran on 1/9/18.
 */
public class DryPen extends Enclosure implements Serializable {
  private static final PenType TYPE = PenType.DRY;
  private final int landArea;
  private final int temperature;
  private Set<Animal> animals = new HashSet<>();

  public DryPen(String name, int length, int width, int temperature) {
    super(name, temperature);
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
    if (animal.getLandAreaRequired() > calculateRemainingSpace()) {
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
    if (!(animal.getPenTypes().contains(TYPE))) {
      return false;
    }
    if (animal instanceof SwimmingAnimal) {
      return false;
    }
    return !(animal instanceof FlyingAnimal);
  }

  private int calculateRemainingSpace() {
    int areaCache = landArea;
    for (Animal animal : animals) {
      areaCache = areaCache - animal.getLandAreaRequired();
    }
    return areaCache;
  }
}
