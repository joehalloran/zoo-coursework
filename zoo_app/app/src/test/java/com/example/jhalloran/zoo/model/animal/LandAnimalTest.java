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
public final class LandAnimalTest {
  private static final PenType DEFAULT_PEN_TYPE = PenType.DRY;
  private static final String DEFAULT_NAME = "cat";
  private static final int DEFAULT_LAND_AREA_REQUIRED = 10;

  Animal animal = createDefaultLandAnimal();

  @Before
  public void setUp() {
    animal = createDefaultLandAnimal();
  }

  @Test
  public void createAnimal_withDefaultValues() {
    assertEquals(animal.getName(), DEFAULT_NAME);
    assertEquals(animal.getLandAreaRequired(), DEFAULT_LAND_AREA_REQUIRED);
    assertTrue(animal.getPenTypes().contains(DEFAULT_PEN_TYPE));
  }

  @Test
  public void createAnimal_returnsMagicValueForWaterAndAirMethods() {
    assertEquals(animal.getWaterVolumeRequired(), 0);
    assertTrue(animal.getWaterTypes().isEmpty());
    assertEquals(animal.getAirVolumeRequired(), 0);
  }

  private LandAnimal createDefaultLandAnimal() {
    return new LandAnimal(DEFAULT_NAME, DEFAULT_LAND_AREA_REQUIRED, EnumSet.of(DEFAULT_PEN_TYPE));
  }
}
