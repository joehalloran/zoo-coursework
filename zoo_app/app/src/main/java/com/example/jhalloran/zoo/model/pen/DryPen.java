package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.Flyer;
import com.example.jhalloran.zoo.model.animal.Swimmer;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A pen that can house land animals who cannot swim or fly
 */
public class DryPen extends AbstractPen implements Serializable {

  private Set<Animal> animals = new HashSet<>();

  public DryPen(String name, int length, int width, int temperature) {
    this(name, PenType.DRY, length, width, temperature);
  }

  // Allows subclasses to override type
  DryPen(String name, PenType type, int length, int width, int temperature) {
    super(name, type, temperature, (length * width));
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
  public boolean addAnimal(Animal animal) {
    return canLiveHere(animal) && animals.add(animal);
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
    if (!(animal.getPenTypes().contains(super.getType()))) {
      return false;
    }
    if (animal instanceof Swimmer) {
      return false;
    }
    return !(animal instanceof Flyer);
  }

  private int calculateRemainingSpace() {
    int areaCache = super.getLandArea();
    for (Animal animal : animals) {
      areaCache = areaCache - animal.getLandAreaRequired();
    }
    return areaCache;
  }
}
