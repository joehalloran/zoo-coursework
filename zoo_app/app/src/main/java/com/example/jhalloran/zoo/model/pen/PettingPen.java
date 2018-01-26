package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.Flyer;
import com.example.jhalloran.zoo.model.animal.Swimmer;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jhalloran on 1/26/18.
 */

public class PettingPen extends DryPen implements Serializable {
  private static final PenType TYPE = PenType.PETTING;

  public PettingPen(String name, int length, int width, int temperature) {
    super(name, length, width, temperature);
  }

  @Override
  public PenType getType() {
    return TYPE;
  }

  @Override
  public boolean canLiveHere(Animal animal) {
    if (animal.getLandAreaRequired() > calculateRemainingSpace()) {
      return false;
    }
    Set<Animal> animals = super.getAnimals();
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
    if (animal instanceof Swimmer) {
      return false;
    }
    return !(animal instanceof Flyer);
  }

  private int calculateRemainingSpace() {
    int areaCache = super.getLandArea();
    for (Animal animal : super.getAnimals()) {
      areaCache = areaCache - animal.getLandAreaRequired();
    }
    return areaCache;
  }
}
