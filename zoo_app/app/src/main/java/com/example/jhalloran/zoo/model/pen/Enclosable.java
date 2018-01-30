package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.util.Set;

/**
 * Defines an API for Zoo enclosures.
 */
public interface Enclosable {

  /**
   * @return pen's name
   */
  String getName();

  /**
   * @return pen temperature celcius
   */
  int getTemperature();

  /**
   * @return {@link PenType} of this pen
   */
  PenType getType();

  /**
   * @return Land area of this pen, metre squared
   */
  int getLandArea();

  /**
   * @return {@link Animal} that live in this pen
   */
  Set<Animal> getAnimals();

  /**
   * @param animal {@link Animal} to add to this pen
   * @return {@code true} if add is successful, {@code false} otherwise
   */
  boolean addAnimal(Animal animal);

  /**
   * @param animal {@link Animal} to remove to this pen
   * @return {@code true} if removal is successful, {@code false} otherwise
   */
  boolean removeAnimal(Animal animal);

  /**
   * Ascertains if an animal can live in this pen. Useful to call before {@link #addAnimal(Animal)}
   * in client code.
   *
   * @param animal {@link Animal} to check
   * @return {@code true} if animal can live here, {@code false} otherwise
   */
  boolean canLiveHere(Animal animal);

  /**
   * @return {@code true} if assigned to a {@link Zookeeper}, {@code false} otherwise
   */
  boolean isAssigned();

  /**
   * @return The {@link Zookeeper} to which this pen is assigned
   */
  Zookeeper getAssignedToZookeeper();

  /**
   * Assigns this pen to a new {@link Zookeeper}
   *
   * @param zookeeper {@link Zookeeper} to which the pen will be assigned to
   * @return {@code true} if assignment is successful, {@code false} otherwise
   */
  boolean assignToZookeeper(Zookeeper zookeeper);
}