package com.example.jhalloran.zoo.model.animal;

/**
 * Created by jhalloran on 1/8/18.
 */

import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.util.Collections;
import java.util.Set;

/** */
public class FlyingAnimal extends Animal {
  private final int landAreaRequired;
  private final int airVolumeRequired;

  private FlyingAnimal(String name, Set<PenType> penTypes) {
    super(name, penTypes);
    landAreaRequired = 0;
    airVolumeRequired = 0;
  }

  // TODO Builder
  public FlyingAnimal(
      String name, int landAreaRequired, int airVolumeRequired, Set<PenType> penTypes) {
    super(name, penTypes);
    this.landAreaRequired = landAreaRequired;
    this.airVolumeRequired = airVolumeRequired;
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
}

