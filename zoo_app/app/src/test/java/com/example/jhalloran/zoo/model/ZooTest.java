package com.example.jhalloran.zoo.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.LandAnimal;
import com.example.jhalloran.zoo.model.pen.DryPen;
import com.example.jhalloran.zoo.model.pen.Enclosure;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;
import org.junit.Before;
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

  @Before
  public void setUp() {
    Zoo.initializeZoo(new Zoo());
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
  public void addAndGetAnimalById() {
    zoo.addAnimal(defaultAnimal);
    assertTrue(zoo.getAnimals().contains(defaultAnimal));
    for (UUID uuid : zoo.getAnimalIds()) {
      assertEquals(zoo.getAnimalById(uuid), defaultAnimal);
      assertEquals(zoo.getAnyItemById(uuid), defaultAnimal);
    }
  }

  @Test
  public void addSameAnimalTwice_notAdded() {
    zoo.addAnimal(defaultAnimal);
    Set<UUID> animalIds = zoo.getAnimalIds();
    assertEquals(animalIds.size(), 1);
    UUID idCache = animalIds.iterator().next();
    for (UUID uuid : animalIds) {
      assertEquals(zoo.getAnimalById(uuid), defaultAnimal);
    }

    // Adding again
    zoo.addAnimal(defaultAnimal);
    assertTrue(zoo.getAnimals().contains(defaultAnimal));
    assertEquals(zoo.getAnimals().size(), 1);
    assertEquals(zoo.getAnimalIds().iterator().next(), idCache);
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
  public void addSamePenTwice_notAdded() {
    zoo.addPen(defaultPen);
    Set<UUID> penIds = zoo.getPenIds();
    assertEquals(penIds.size(), 1);
    UUID idCache = penIds.iterator().next();
    for (UUID uuid : penIds) {
      assertEquals(zoo.getPenById(uuid), defaultPen);
    }

    // Adding again
    zoo.addPen(defaultPen);
    assertTrue(zoo.getPens().contains(defaultPen));
    assertEquals(zoo.getPens().size(), 1);
    assertEquals(zoo.getPenIds().iterator().next(), idCache);
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
  public void addSameZookeeperTwice_notAdded() {
    zoo.addZookeeper(defaultZookeeper);
    Set<UUID> zookeeperIds = zoo.getZookeeperIds();
    assertEquals(zookeeperIds.size(), 1);
    UUID idCache = zookeeperIds.iterator().next();
    for (UUID uuid : zookeeperIds) {
      assertEquals(zoo.getZookeeperById(uuid), defaultZookeeper);
    }

    // Adding again
    zoo.addZookeeper(defaultZookeeper);
    assertTrue(zoo.getZookeepers().contains(defaultZookeeper));
    assertEquals(zoo.getZookeepers().size(), 1);
    assertEquals(zoo.getZookeeperIds().iterator().next(), idCache);
  }

  @Test
  public void toString_returnsName() {
    assertEquals(zoo.toString(), DEFAULT_ZOO_NAME);
  }
}
