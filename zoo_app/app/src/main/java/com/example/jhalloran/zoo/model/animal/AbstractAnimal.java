package com.example.jhalloran.zoo.model.animal;

import com.example.jhalloran.zoo.model.pen.Enclosable;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

/**
 * Superclass to be extended to create new types of Zoo Animal
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

  /**
   * @inheritDoc
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * @inheritDoc
   */
  @Override
  public Set<PenType> getPenTypes() {
    return penTypes;
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean isDangerous() {
    return dangerous;
  }

  /**
   * @inheritDoc
   */
  @Override
  public int getLandAreaRequired() {
    return landAreaRequired;
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean isAssigned() {
    return (penAssignedTo != null);
  }

  /**
   * @inheritDoc
   */
  @Override
  public Enclosable getAssignedToPen() {
    return penAssignedTo;
  }

  /**
   * @inheritDoc
   */
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
