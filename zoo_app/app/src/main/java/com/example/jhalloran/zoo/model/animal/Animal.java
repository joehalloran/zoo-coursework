package com.example.jhalloran.zoo.model.animal;

import com.example.jhalloran.zoo.model.pen.Enclosure;
import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by jhalloran on 1/8/18.
 */
public abstract class Animal implements Serializable {
  private final String name;
  private final Set<PenType> penTypes;
  private Enclosure penAssignedTo = null;

  public Animal(String name, Set<PenType> penTypes) {
    this.name = name;
    this.penTypes = EnumSet.copyOf(penTypes);
  }

  public String getName() {
    return name;
  }

  public Set<PenType> getPenTypes() {
    return penTypes;
  }

  public abstract int getWaterVolumeRequired();

  public abstract int getLandAreaRequired();

  public abstract int getAirVolumeRequired();

  public abstract Set<WaterType> getWaterTypes();

  public boolean isAssigned() {
    return (penAssignedTo != null);
  }

  public Enclosure getAssignedToPen() {
    return penAssignedTo;
  }

  public boolean assignToPen(Enclosure pen) {
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
