package com.example.jhalloran.zoo.model.animal;

/**
 * Defines mixin API for {@link Animal} than can fly
 */
public interface Flyer {

  /**
   * @return the air volume required for a animal, metre cubed.
   */
  int getAirVolumeRequired();
}
