package com.example.jhalloran.zoo.model.animal;

import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

/**
 * An animal that can swim.
 */
public class SwimmingAnimal extends AbstractAnimal implements Swimmer, Serializable {

  private final int waterVolumeRequire;
  private final Set<WaterType> waterTypes;

  public SwimmingAnimal(
      String name,
      boolean dangerous,
      int landAreaRequired,
      int waterVolumeRequired,
      Set<WaterType> waterTypes,
      Set<PenType> penTypes) {
    super(name, penTypes, dangerous, landAreaRequired);
    this.waterTypes = EnumSet.copyOf(waterTypes);
    this.waterVolumeRequire = waterVolumeRequired;
  }

  /**
   * @inheritDoc
   */
  @Override
  public Set<WaterType> getWaterTypes() {
    return waterTypes;
  }

  /**
   * @inheritDoc
   */
  @Override
  public int getWaterVolumeRequired() {
    return waterVolumeRequire;
  }

  public static class Builder {

    private String name;
    private boolean dangerous;
    private Set<PenType> penTypes;
    private int landAreaRequired;
    private int waterVolumeRequired;
    private Set<WaterType> waterTypes;

    public Builder() {
    }

    public SwimmingAnimal.Builder setName(String name) {
      this.name = name;
      return this;
    }

    public SwimmingAnimal.Builder setDangerous(boolean dangerous) {
      this.dangerous = dangerous;
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
          this.dangerous,
          this.landAreaRequired,
          this.waterVolumeRequired,
          this.waterTypes,
          this.penTypes);
    }
  }
}
