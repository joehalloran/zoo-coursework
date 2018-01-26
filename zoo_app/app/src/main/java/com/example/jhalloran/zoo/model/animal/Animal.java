package com.example.jhalloran.zoo.model.animal;

import com.example.jhalloran.zoo.model.pen.Enclosable;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.util.Set;

/**
 * Created by jhalloran on 1/8/18.
 */
public interface Animal {
  String getName();

  Set<PenType> getPenTypes();

  boolean isDangerous();

  int getLandAreaRequired();

  boolean isAssigned();

  Enclosable getAssignedToPen();

  boolean assignToPen(Enclosable pen);
}
