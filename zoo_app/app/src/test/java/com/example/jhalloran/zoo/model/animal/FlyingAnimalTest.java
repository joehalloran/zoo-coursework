package com.example.jhalloran.zoo.model.animal;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import com.example.jhalloran.zoo.model.shared.PenType;
import java.util.EnumSet;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jhalloran on 1/8/18.
 */

public final class FlyingAnimalTest {
  private static final PenType DEFAULT_PEN_TYPE = PenType.AVIARY;
  private static final String DEFAULT_NAME = "parrot";
  private static final int DEFAULT_LAND_AREA_REQUIRED = 10;
  private static final int DEFAULT_AIR_VOLUME_REQUIRED = 10;

  Animal animal = createDefaultLandAnimal();

  @Before
  public void setUp() {
    animal = createDefaultLandAnimal();
  }

  @Test
  public void createAnimal_withDefaultValues() {
    assertEquals(animal.getName(),DEFAULT_NAME);
    assertEquals(animal.getLandAreaRequired(), DEFAULT_LAND_AREA_REQUIRED);
    assertEquals(animal.getAirVolumeRequired(), DEFAULT_AIR_VOLUME_REQUIRED);
    assertTrue(animal.getPenTypes().contains(DEFAULT_PEN_TYPE));
  }

  @Test
  public void createAnimal_returnsMagicValueForWaterMethods() {
    assertEquals(animal.getWaterVolumeRequired(), 0);
    assertTrue(animal.getWaterTypes().isEmpty());
  }

  private FlyingAnimal createDefaultLandAnimal() {
    return new FlyingAnimal(
        DEFAULT_NAME,
        DEFAULT_LAND_AREA_REQUIRED,
        DEFAULT_AIR_VOLUME_REQUIRED,
        EnumSet.of(DEFAULT_PEN_TYPE));
  }
}