package com.example.jhalloran.zoo.model.animal;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.util.EnumSet;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jhalloran on 1/8/18.
 */

public final class SwimmingAnimalTest {
  private static final PenType DEFAULT_PEN_TYPE = PenType.AQUARIUM;
  private static final String DEFAULT_NAME = "dolphin";
  private static final int DEFAULT_LAND_AREA_REQUIRED = 10;
  private static final int DEFAULT_WATER_VOLUME_REQUIRED = 100;
  private static final WaterType DEFAULT_WATER_TYPE = WaterType.SALT;

  Animal animal = createDefaultSwimmingAnimal();

  @Before
  public void setUp() {
    animal = createDefaultSwimmingAnimal();
  }

  @Test
  public void createAnimal_withDefaultValues() {
    assertEquals(animal.getName(), DEFAULT_NAME);
    assertEquals(animal.getLandAreaRequired(), DEFAULT_LAND_AREA_REQUIRED);
    assertEquals(animal.getWaterVolumeRequired(), DEFAULT_WATER_VOLUME_REQUIRED);
    assertTrue(animal.getPenTypes().contains(DEFAULT_PEN_TYPE));
    assertTrue(animal.getWaterTypes().contains(DEFAULT_WATER_TYPE));
  }

  @Test
  public void createAnimal_returnsMagicValueForAirMethods() {
    assertEquals(animal.getAirVolumeRequired(), 0);
  }

  @Test
  public void toString_returnsName(){
    assertEquals(animal.toString(), DEFAULT_NAME);
  }

  private SwimmingAnimal createDefaultSwimmingAnimal() {
    return new SwimmingAnimal(
        DEFAULT_NAME,
        DEFAULT_LAND_AREA_REQUIRED,
        DEFAULT_WATER_VOLUME_REQUIRED,
        EnumSet.of(DEFAULT_WATER_TYPE),
        EnumSet.of(DEFAULT_PEN_TYPE));
  }
}
