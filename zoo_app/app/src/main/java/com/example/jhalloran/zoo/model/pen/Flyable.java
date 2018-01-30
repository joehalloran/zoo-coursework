package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.animal.Flyer;

/**
 * Defines mixin API for {@link Enclosable} than can host {@link Flyer} animals
 */
public interface Flyable {

  /**
   * @return Air volume of this pen, metre cubed
   */
  int getAirVolume();
}

