package com.example.jhalloran.zoo.model.animal;

/**
 * Created by jhalloran on 1/8/18.
 */

import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/** */
public class LandAnimal extends Animal implements Serializable {
  private final int landAreaRequired;

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