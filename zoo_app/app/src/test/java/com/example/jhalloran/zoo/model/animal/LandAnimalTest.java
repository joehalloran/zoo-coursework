package com.example.jhalloran.zoo.model.animal;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import com.example.jhalloran.zoo.model.pen.DryPen;
import com.example.jhalloran.zoo.model.pen.Enclosure;
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
    assertFalse(animal.isAssigned());
  }

  @Test
  public void createAnimal_returnsMagicValueForWaterAndAirMethods() {
    assertEquals(animal.getWaterVolumeRequired(), 0);
    assertTrue(animal.getWaterTypes().isEmpty());
    assertEquals(animal.getAirVolumeRequired(), 0);
  }

  @Test
  public void assignAnimalToPen() {
    Enclosure pen = new DryPen(10, 10, 20);
    try {
      animal.assignToPen(pen);
    } catch (Exception e) {
      // Do nothing... test
    }
    assertTrue(animal.isAssigned());
    assertEquals(animal.getAssignedToPen(), pen);
  }

  @Test
  public void assignAssignedAnimalToSmallPen_throws_remainsAssigned() {
    Enclosure largePen = new DryPen(50, 50, 20);
    try {
      animal.assignToPen(largePen);
    } catch (Exception e) {
      // Do nothing... test
    }
    Enclosure smallPen = new DryPen(1, 1, 20);
    try {
      animal.assignToPen(smallPen);
      fail("Expected exception: Animal too large");
    } catch (Exception e) {
      // Do nothing... test
    }
    assertTrue(animal.isAssigned());
    assertEquals(animal.getAssignedToPen(), largePen);
  }

  @Test
  public void toString_returnsName(){
    assertEquals(animal.toString(), DEFAULT_NAME);
  }

  private LandAnimal createDefaultLandAnimal() {
    return new LandAnimal(DEFAULT_NAME, DEFAULT_LAND_AREA_REQUIRED, EnumSet.of(DEFAULT_PEN_TYPE));
  }

}
