package com.example.jhalloran.zoo.model.animal;

import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by jhalloran on 1/8/18.
 */

public class SwimmingAnimal extends Animal {
  private final int waterVolumeRequire;
  private final int landAreaRequired;
  private Set<WaterType> waterTypes;

  private SwimmingAnimal(String name, Set<PenType> penTypes) {
    super(name, penTypes);
    this.waterVolumeRequire = 0;
    this.landAreaRequired = 0;
  }

  //TODO: Builder
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
}
