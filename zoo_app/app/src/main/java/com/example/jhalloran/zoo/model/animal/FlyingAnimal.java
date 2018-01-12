package com.example.jhalloran.zoo.model.animal;

/**
 * Created by jhalloran on 1/8/18.
 */

import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/** */
public class FlyingAnimal extends Animal implements Serializable {
  private String name;
  private Set<PenType> penTypes;
  private final int landAreaRequired;
  private final int airVolumeRequired;

  // TODO Builder
  public FlyingAnimal(
      String name, int landAreaRequired, int airVolumeRequired, Set<PenType> penTypes) {
    super();
    this.name = name;
    this.penTypes = EnumSet.copyOf(penTypes);
    this.landAreaRequired = landAreaRequired;
    this.airVolumeRequired = airVolumeRequired;
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
  public int getLandAreaRequired() {
    return landAreaRequired;
  }

  @Override
  public int getWaterVolumeRequired() {
    return 0;
  }

  @Override
  public int getAirVolumeRequired() {
    return airVolumeRequired;
  }

  @Override
  public Set<WaterType> getWaterTypes() {
    return Collections.emptySet();
  }

  @Override
  public String toString() {
    return name;
  }
}

