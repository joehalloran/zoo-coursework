
```java
package com.example.jhalloran.zoo.model;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.FlyingAnimal;
import com.example.jhalloran.zoo.model.animal.LandAnimal;
import com.example.jhalloran.zoo.model.animal.SwimmingAnimal;
import com.example.jhalloran.zoo.model.pen.AquariumPen;
import com.example.jhalloran.zoo.model.pen.AviaryPen;
import com.example.jhalloran.zoo.model.pen.DryPen;
import com.example.jhalloran.zoo.model.pen.Enclosable;
import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * A singleton that represents the current state of the Zoo. A god class.
 */
public class Zoo implements Serializable {

  private static final String ZOO_NAME = "Tottenham Hale retail park zoo";
  private static Zoo instance = null;

  private Map<UUID, Object> allItems = new HashMap<>();
  private Map<UUID, Animal> idsToAnimals = new HashMap<>();
  private Map<UUID, Zookeeper> idsToZookeepers = new HashMap<>();
  private Map<UUID, Enclosable> idsToPens = new HashMap<>();

  // Private constructor to suppress multiple instantiation. Singleton.
  private Zoo() {
  }

  /**
   * @return Current Zoo instance
   */
  public static Zoo getInstance() {
    if (instance == null) {
      instance = new Zoo();
      instance.setUpZoo();
    }
    return instance;
  }

  /**
   * Initializes Zoo from a pre-existing instance, e.g. A zoo saved from file.
   *
   * @param zoo The pre-existing zoo to initialize.
   */
  public static void initializeZoo(Zoo zoo) {
    instance = zoo;
  }

  /**
   * @return The name of this Zoo.
   */
  public String getName() {
    return ZOO_NAME;
  }

  /**
   * @return All {@link Animal} in the Zoo.
   */
  public List<Animal> getAnimals() {
    return new ArrayList<>(idsToAnimals.values());
  }

  /**
   * @return All {@link Zookeeper} in the Zoo.
   */
  public List<Zookeeper> getZookeepers() {
    return new ArrayList<>(idsToZookeepers.values());
  }

  /**
   * @return All {@link Enclosable} in the Zoo.
   */
  public List<Enclosable> getPens() {
    return new ArrayList<>(idsToPens.values());
  }

  /**
   * @return {@link UUID} for all {@link Animal} in the Zoo.
   */
  public Set<UUID> getAnimalIds() {
    return idsToAnimals.keySet();
  }

  /**
   * @return {@link UUID} for all {@link Zookeeper} in the Zoo.
   */
  public Set<UUID> getZookeeperIds() {
    return idsToZookeepers.keySet();
  }

  /**
   * @return {@link UUID} for all {@link Enclosable} in the Zoo.
   */
  public Set<UUID> getPenIds() {
    return idsToPens.keySet();
  }

  /**
   * @param uuid {@link UUID}
   * @return The {@link Animal} associated with a specific {@link UUID}.
   */
  public Animal getAnimalById(UUID uuid) {
    return idsToAnimals.get(uuid);
  }

  /**
   * @param uuid {@link UUID}
   * @return The {@link Enclosable} associated with a specific {@link UUID}.
   */
  public Enclosable getPenById(UUID uuid) {
    return idsToPens.get(uuid);
  }

  /**
   * @param uuid {@link UUID}
   * @return The {@link Zookeeper} associated with a specific {@link UUID}.
   */
  public Zookeeper getZookeeperById(UUID uuid) {
    return idsToZookeepers.get(uuid);
  }

  /**
   * @param uuid {@link UUID}
   * @return The Zoo object associated with a specific {@link UUID}.
   */
  public Object getAnyItemById(UUID uuid) {
    return allItems.get(uuid);
  }

  /**
   * @param animal {@link Animal} to add to the Zoo.
   */
  public void addAnimal(Animal animal) {
    if (!getAnimals().contains(animal)) {
      UUID newUuid = UUID.randomUUID();
      idsToAnimals.put(newUuid, animal);
      allItems.put(newUuid, animal);
    }
  }

  /**
   * @param zookeeper {@link Zookeeper} to add to the Zoo.
   */
  public void addZookeeper(Zookeeper zookeeper) {
    if (!getZookeepers().contains(zookeeper)) {
      UUID newUuid = UUID.randomUUID();
      idsToZookeepers.put(newUuid, zookeeper);
      allItems.put(newUuid, zookeeper);
    }
  }

  /**
   * @param pen {@link Enclosable} to add to the Zoo.
   */
  public void addPen(Enclosable pen) {
    if (!getPens().contains(pen)) {
      UUID newUuid = UUID.randomUUID();
      idsToPens.put(newUuid, pen);
      allItems.put(newUuid, pen);
    }
  }

  @Override
  public String toString() {
    return ZOO_NAME;
  }

  private void setUpZoo() {
    addAnimal(new LandAnimal("Sloth", 2, EnumSet.of(PenType.DRY), false));
    addAnimal(new LandAnimal("Goat", 3, EnumSet.of(PenType.DRY, PenType.PETTING), false));
    addAnimal(new LandAnimal("Dog", 4, EnumSet.of(PenType.DRY, PenType.PETTING), false));
    addAnimal(new FlyingAnimal("Owl", false, 0, 20, EnumSet.of(PenType.AVIARY)));
    addAnimal(new SwimmingAnimal("Dolphin", false, 0, 40, EnumSet.of(WaterType.SALT),
        EnumSet.of(PenType.AQUARIUM)));
    addAnimal(new SwimmingAnimal("Penguin", false, 2, 4, EnumSet.of(WaterType.SALT),
        EnumSet.of(PenType.PART_WATER_PART_DRY)));
    addAnimal(new SwimmingAnimal("Hippo", false, 10, 20, EnumSet.of(WaterType.SALT),
        EnumSet.of(PenType.PART_WATER_PART_DRY)));

    addPen(new DryPen("Dry Pen", 10, 10, 21));
    addPen(new AviaryPen("Aviary", 50, 100, 30, 21));
    addPen(new AquariumPen("Aquarium", WaterType.SALT, 10, 10, 10, 17));
    addPen(new AquariumPen("Big Aquarium", WaterType.SALT, 30, 10, 10, 17));

    addZookeeper(new Zookeeper("Alex", EnumSet.of(PenType.PART_WATER_PART_DRY, PenType.AQUARIUM)));
    addZookeeper(new Zookeeper("Hardip", EnumSet.of(PenType.DRY)));
    addZookeeper(new Zookeeper("Farhad", EnumSet.of(PenType.AVIARY)));
  }
}
```


```java
package com.example.jhalloran.zoo.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.LandAnimal;
import com.example.jhalloran.zoo.model.pen.DryPen;
import com.example.jhalloran.zoo.model.pen.Enclosable;
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
  private Animal defaultAnimal = new LandAnimal("Rhino", 20, EnumSet.of(PenType.DRY), false);
  private Enclosable defaultPen = new DryPen("Dry pen", 10, 10, 21);
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
```
