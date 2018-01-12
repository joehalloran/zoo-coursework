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
  private String name;
  private Set<PenType> penTypes;
  private final int landAreaRequired;

  public LandAnimal(String name, int landAreaRequired, Set<PenType> penTypes) {
    super();
    this.name = name;
    this.penTypes = EnumSet.copyOf(penTypes);
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

  @Override
  public String toString() {
    return name;
  }

}