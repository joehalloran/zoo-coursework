package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.animal.Swimmer;
import com.example.jhalloran.zoo.model.shared.WaterType;

/**
 * Defines mixin API for {@link Enclosable} than can host {@link Swimmer} animals
 */
public interface Swimmable {

  /**
   * @return Water volume of this pen, metre cubed
   */
  int getWaterVolume();

  /**
   * @return {@link WaterType} of this pen
   */
  WaterType getWaterType();
}

