package com.example.jhalloran.zoo.model.animal;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import com.example.jhalloran.zoo.model.pen.DryPen;
import com.example.jhalloran.zoo.model.pen.Enclosable;
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
  private static final boolean DEFAULT_DANGEROUS = false;
  private static final int DEFAULT_LAND_AREA_REQUIRED = 10;
  private static final String DEFAULT_PEN_NAME = "pen";

  private Animal animal = createDefaultLandAnimal();

  @Before
  public void setUp() {
    animal = createDefaultLandAnimal();
  }

  @Test
  public void createAnimal_withDefaultValues() {
    assertEquals(animal.getName(), DEFAULT_NAME);
    assertEquals(animal.getLandAreaRequired(), DEFAULT_LAND_AREA_REQUIRED);
    assertFalse(animal.isDangerous());
    assertTrue(animal.getPenTypes().contains(DEFAULT_PEN_TYPE));
    assertFalse(animal.isAssigned());
  }

  @Test
  public void assignAnimalToPen() {
    Enclosable pen = new DryPen(DEFAULT_PEN_NAME, 10, 10, 20);

    assertTrue(animal.assignToPen(pen));
    assertTrue(animal.isAssigned());
    assertEquals(animal.getAssignedToPen(), pen);
  }

  @Test
  public void assignAnimalSamePenTwice_fails() {
    Enclosable pen = new DryPen(DEFAULT_PEN_NAME, 10, 10, 20);

    assertTrue(animal.assignToPen(pen));
    assertFalse(animal.assignToPen(pen));
    assertTrue(animal.isAssigned());
    assertEquals(animal.getAssignedToPen(), pen);
  }

  @Test
  public void assignAnimalToSecondPen_updates() {
    Enclosable pen = new DryPen(DEFAULT_PEN_NAME, 10, 10, 20);
    Enclosable secondPen = new DryPen(DEFAULT_PEN_NAME, 10, 10, 20);

    assertTrue(animal.assignToPen(pen));
    assertTrue(animal.assignToPen(secondPen));

    assertEquals(animal.getAssignedToPen(), secondPen);
    assertTrue(secondPen.getAnimals().contains(animal));
    assertFalse(pen.getAnimals().contains(animal));
  }

  @Test
  public void assignAssignedAnimalToSmallPen_fails_remainsAssigned() {
    Enclosable largePen = new DryPen(DEFAULT_PEN_NAME, 50, 50, 20);
    Enclosable smallPen = new DryPen(DEFAULT_PEN_NAME, 1, 1, 20);

    assertTrue(animal.assignToPen(largePen));

    assertFalse(animal.assignToPen(smallPen));

    assertTrue(animal.isAssigned());
    assertEquals(animal.getAssignedToPen(), largePen);
  }

  @Test
  public void toString_returnsName(){
    assertEquals(animal.toString(), DEFAULT_NAME);
  }

  private LandAnimal createDefaultLandAnimal() {
    return new LandAnimal(DEFAULT_NAME, DEFAULT_LAND_AREA_REQUIRED, EnumSet.of(DEFAULT_PEN_TYPE), DEFAULT_DANGEROUS);
  }
}
