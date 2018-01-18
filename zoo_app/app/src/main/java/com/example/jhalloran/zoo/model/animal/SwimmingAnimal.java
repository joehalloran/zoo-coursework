package com.example.jhalloran.zoo.model.animal;

import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by jhalloran on 1/8/18.
 */

public class SwimmingAnimal extends Animal implements Serializable{
  private final int waterVolumeRequire;
  private final int landAreaRequired;
  private final Set<WaterType> waterTypes;

  public SwimmingAnimal(
      String name,
      int landAreaRequired,
      int waterVolumeRequired,
      Set<WaterType> waterTypes,
      Set<PenType> penTypes) {
    super(name, penTypes);
    this.waterTypes = EnumSet.copyOf(waterTypes);
    this.waterVolumeRequire = waterVolumeRequired;
    this.landAreaRequired = landAreaRequired;
  }

  @Override
  public Set<WaterType> getWaterTypes() {
    return waterTypes;
  }

  @Override
  public int getWaterVolumeRequired() {
    return waterVolumeRequire;
  }

  @Override
  public int getLandAreaRequired() {
    return landAreaRequired;
  }

  @Override
  public int getAirVolumeRequired() {
    return 0;
  }

  public static class Builder {
    private String name;
    private Set<PenType> penTypes;
    private int landAreaRequired;
    private int waterVolumeRequired;
    private Set<WaterType> waterTypes;

    public Builder() {}

    public SwimmingAnimal.Builder setName(String name) {
      this.name = name;
      return this;
    }

    public SwimmingAnimal.Builder setPenTypes(Set<PenType> penTypes) {
      this.penTypes = EnumSet.copyOf(penTypes);
      return this;
    }

    public SwimmingAnimal.Builder setLandAreaRequired(int landAreaRequired) {
      this.landAreaRequired = landAreaRequired;
      return this;
    }

    public SwimmingAnimal.Builder setWaterVolumeRequired(int waterVolumeRequired) {
      this.waterVolumeRequired = waterVolumeRequired;
      return this;
    }

    public SwimmingAnimal.Builder setWaterTypes(Set<WaterType> waterTypes) {
      this.waterTypes = EnumSet.copyOf(waterTypes);
      return this;
    }

    public SwimmingAnimal build() {
      return new SwimmingAnimal(
          this.name,
          this.landAreaRequired,
          this.waterVolumeRequired,
          this.waterTypes,
          this.penTypes);
    }
  }
}
