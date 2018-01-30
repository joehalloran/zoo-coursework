package com.example.jhalloran.zoo.model.animal;

import com.example.jhalloran.zoo.model.shared.WaterType;
import java.util.Set;

/**
 *Defines mixin API for {@link Animal} than can swim.
 */
public interface Swimmer {

  /**
   * @return the {@link WaterType} in which these animals can live. Note, this is a set to
   * accommodate euryhaline animals.
   */
  Set<WaterType> getWaterTypes();

  /**
   * @return the water volume required for a animal, metre cubed.
   */
  int getWaterVolumeRequired();

}
