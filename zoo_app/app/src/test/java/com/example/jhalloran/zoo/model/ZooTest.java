package com.example.jhalloran.zoo.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.LandAnimal;
import com.example.jhalloran.zoo.model.pen.DryPen;
import com.example.jhalloran.zoo.model.pen.Enclosure;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.util.EnumSet;
import java.util.UUID;
import org.junit.Test;

/**
 * Created by jhalloran on 1/9/18.
 */

public class ZooTest {
  private static final String DEFAULT_ZOO_NAME = "Tottenham Hale retail park zoo";

  private Zoo zoo = Zoo.getInstance();
  private Animal defaultAnimal = new LandAnimal("Rhino", 20, EnumSet.of(PenType.DRY));
  private Enclosure defaultPen = new DryPen("Dry pen", 10, 10, 21);
  private Zookeeper defaultZookeeper = new Zookeeper("James", EnumSet.of(PenType.DRY));

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
  public void addAndGetAnimalById() {
    zoo.addAnimal(defaultAnimal);
    assertTrue(zoo.getAnimals().contains(defaultAnimal));
    for (UUID uuid : zoo.getAnimalIds()) {
      assertEquals(zoo.getAnimalById(uuid), defaultAnimal);
      assertEquals(zoo.getAnyItemById(uuid), defaultAnimal);
    }
  }

  @Test
  public void addAndGetPenById() {
    zoo.addPen(defaultPen);
    assertTrue(zoo.getPens().contains(defaultPen));
    for (UUID uuid : zoo.getPenIds()) {
      assertEquals(zoo.getPenById(uuid), defaultPen);
      assertEquals(zoo.getAnyItemById(uuid), defaultPen);
    }
  }

  @Test
  public void addAndGetZookeeperById() {
    zoo.addZookeeper(defaultZookeeper);
    assertTrue(zoo.getZookeepers().contains(defaultZookeeper));
    for (UUID uuid : zoo.getZookeeperIds()) {
      assertEquals(zoo.getZookeeperById(uuid), defaultZookeeper);
      assertEquals(zoo.getAnyItemById(uuid), defaultZookeeper);
    }
  }

  @Test
  public void toString_returnsName() {
    assertEquals(zoo.toString(), DEFAULT_ZOO_NAME);
  }
}
