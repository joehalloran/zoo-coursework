package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by jhalloran on 1/9/18.
 */
public interface Enclosable {
  String getName();

  int getTemperature();

  PenType getType();

  int getLandArea();

  Set<Animal> getAnimals();

  boolean addAnimal(Animal animal);

  boolean removeAnimal(Animal animal);

  boolean canLiveHere(Animal animal);

  boolean isAssigned();

  Zookeeper getAssignedToZookeeper();

  boolean assignToZookeeper(Zookeeper zookeeper);
}