package com.example.jhalloran.zoo.model.animal;

import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by jhalloran on 1/8/18.
 */

public abstract class Animal {
  private final String name;
  private final Set<PenType> penTypes;

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

  @Override
  public String toString() {
    return name;
  }
}
