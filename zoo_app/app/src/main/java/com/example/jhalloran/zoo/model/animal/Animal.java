package com.example.jhalloran.zoo.model.animal;

import com.example.jhalloran.zoo.model.pen.Enclosable;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.util.Set;

/**
 * Defines Animal API
 */
public interface Animal {

  /**
   * @return Animal's name
   */
  String getName();

  /**
   * @return {@link PenType} where this animal can live
   */
  Set<PenType> getPenTypes();

  /**
   * return {@code true} if the animal is considered too dangerous to share a {@link Enclosable}.
   */
  boolean isDangerous();

  /**
   * @return the land required by this animal (metre squared)
   */
  int getLandAreaRequired();

  /**
   * @return {@code true} if this animal is assigned to a pen, {@code false} otherwise
   */
  boolean isAssigned();

  /**
   * @return The {@link Enclosable} to which this animal is assigned
   */
  Enclosable getAssignedToPen();

  /**
   * Assigns this animal to a new {@link Enclosable}
   *
   * @param pen {@link Enclosable} to which the animal will be assigned to
   * @return {@code true} if assignment is successful, {@code false} otherwise
   */
  boolean assignToPen(Enclosable pen);
}
