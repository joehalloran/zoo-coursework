package com.example.jhalloran.zoo.model.animal;

/**
 * Created by jhalloran on 1/8/18.
 */

import android.net.Uri.Builder;
import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/** */
public class FlyingAnimal extends Animal implements Serializable {
  private final String name;
  private final Set<PenType> penTypes;
  private final int landAreaRequired;
  private final int airVolumeRequired;

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

  public static class Builder {
    private String name;
    private Set<PenType> penTypes;
    private int landAreaRequired;
    private int airVolumeRequired;

    public Builder() {}

    public FlyingAnimal.Builder setName(String name) {
      this.name = name;
      return this;
    }

    public FlyingAnimal.Builder setPenTypes(Set<PenType> penTypes) {
      this.penTypes = EnumSet.copyOf(penTypes);
      return this;
    }

    public FlyingAnimal.Builder setLandAreaRequired(int landAreaRequired) {
      this.landAreaRequired = landAreaRequired;
      return this;
    }

    public FlyingAnimal.Builder setAirVolumeRequired(int airVolumeRequired) {
      this.airVolumeRequired = airVolumeRequired;
      return this;
    }

    public FlyingAnimal build() {
      return new FlyingAnimal(
          this.name,
          this.landAreaRequired,
          this.airVolumeRequired,
          this.penTypes);
    }
  }
}

