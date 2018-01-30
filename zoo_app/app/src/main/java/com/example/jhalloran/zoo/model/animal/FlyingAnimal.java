package com.example.jhalloran.zoo.model.animal;

import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

/**
 * An animal that can fly.
 **/
public class FlyingAnimal extends AbstractAnimal implements Flyer, Serializable {

  private final int airVolumeRequired;

  public FlyingAnimal(
      String name, boolean dangerous, int landAreaRequired, int airVolumeRequired,
      Set<PenType> penTypes) {
    super(name, penTypes, dangerous, landAreaRequired);
    this.airVolumeRequired = airVolumeRequired;
  }

  /**
   * @inheritDoc
   */
  @Override
  public int getAirVolumeRequired() {
    return airVolumeRequired;
  }

  public static class Builder {

    private String name;
    private boolean dangerous;
    private Set<PenType> penTypes;
    private int landAreaRequired;
    private int airVolumeRequired;

    public Builder() {
    }

    public FlyingAnimal.Builder setName(String name) {
      this.name = name;
      return this;
    }

    public FlyingAnimal.Builder setDangerous(boolean dangerous) {
      this.dangerous = dangerous;
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
          this.dangerous,
          this.landAreaRequired,
          this.airVolumeRequired,
          this.penTypes);
    }
  }
}

