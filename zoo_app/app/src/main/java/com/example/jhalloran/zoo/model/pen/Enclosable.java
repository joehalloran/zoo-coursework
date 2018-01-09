package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.util.List;

/**
 * Created by jhalloran on 1/9/18.
 */

public interface Enclosable {
  PenType getType();

  int getLandArea();

  int getTemperature();

  List<Animal> getAnimals();

  void addAnimal(Animal animal)
      throws Exception; // TODO: Create custom exception AND/OR return boolean

  // TODO: boolean removeAnimal(Animal animal);

  boolean canLiveHere(Animal animal);
}