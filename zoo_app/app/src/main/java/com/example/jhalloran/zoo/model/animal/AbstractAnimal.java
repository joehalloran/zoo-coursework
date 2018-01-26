package com.example.jhalloran.zoo.model.animal;

import com.example.jhalloran.zoo.model.pen.Enclosable;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by jhalloran on 1/26/18.
 */

public abstract class AbstractAnimal implements Animal, Serializable {
  private final String name;
  private final Set<PenType> penTypes;
  private final boolean dangerous;
  private final int landAreaRequired;
  private Enclosable penAssignedTo = null;

  AbstractAnimal(String name, Set<PenType> penTypes, boolean dangerous, int landAreaRequired) {
    this.name = name;
    this.penTypes = EnumSet.copyOf(penTypes);
    this.dangerous = dangerous;
    this.landAreaRequired = landAreaRequired;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Set<PenType> getPenTypes() {
    return penTypes;
  }

  @Override
  public boolean isDangerous() {
    return dangerous;
  }

  @Override
  public int getLandAreaRequired() {
    return landAreaRequired;
  }

  @Override
  public boolean isAssigned() {
    return (penAssignedTo != null);
  }

  @Override
  public Enclosable getAssignedToPen() {
    return penAssignedTo;
  }

  @Override
  public boolean assignToPen(Enclosable pen) {
    if (pen.addAnimal(this)) {
      if (isAssigned()) {
        penAssignedTo.removeAnimal(this);
      }
      penAssignedTo = pen;
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return name;
  }
}
