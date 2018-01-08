package com.example.jhalloran.zoo.model.animal;

/**
 * Created by jhalloran on 1/8/18.
 */

import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.util.Collections;
import java.util.Set;

/** */
public class LandAnimal extends Animal {

  private final int landAreaRequired;

  private LandAnimal(String name, Set<PenType> penTypes) {
    super(name, penTypes);
    landAreaRequired = 0;
  }

  public LandAnimal(String name, int landAreaRequired, Set<PenType> penTypes) {
    super(name, penTypes);
    this.landAreaRequired = landAreaRequired;
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
    return 0;
  }

  @Override
  public Set<WaterType> getWaterTypes() {
    return Collections.emptySet();
  }
}