package com.example.jhalloran.zoo.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.LandAnimal;
import com.example.jhalloran.zoo.model.pen.DryPen;
import com.example.jhalloran.zoo.model.pen.Enclosable;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.util.EnumSet;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jhalloran on 1/9/18.
 */

public class ZooTest {
  private static final String DEFAULT_ZOO_NAME = "Tottenham Hale retail park zoo";

  private Zoo zoo = Zoo.getInstance();
  private Animal defaultAnimal = new LandAnimal("Rhino", 20, EnumSet.of(PenType.DRY));
  private Enclosable defaultPen = new DryPen(10, 10, 21);
  private Zookeeper defaultZookeeper = new Zookeeper("James", EnumSet.of(PenType.DRY));

  @Before
  public void setUp() {
    zoo = Zoo.getInstance();
  }

  @Test
  public void zooManager_isSingleton() {
    Zoo zooTwo = Zoo.getInstance();
    assertEquals(zoo, zooTwo);
  }


  @Test
  public void getName_isDefault() {
    assertEquals(zoo.getName(), DEFAULT_ZOO_NAME);
  }
  @Test
  public void addAnimal() {
    zoo.addAnimal(defaultAnimal);
    assertTrue(zoo.getAnimals().contains(defaultAnimal));
  }

  @Test
  public void addPen() {
    zoo.addPen(defaultPen);
    assertTrue(zoo.getPens().contains(defaultPen));
  }

  @Test
  public void addZookeeper() {
    zoo.addZookeeper(defaultZookeeper);
    assertTrue(zoo.getZookeepers().contains(defaultZookeeper));
  }

  @Test
  public void toString_returnsName() {
    assertEquals(zoo.toString(), DEFAULT_ZOO_NAME);
  }
}
