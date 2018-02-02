
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

```java
package com.example.jhalloran.zoo.model;

import com.example.jhalloran.zoo.model.pen.Enclosable;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * A zookeeper, who can manage pens at Zoo.
 */
public class Zookeeper implements Serializable {

  private final String name;
  private Set<PenType> penTypesCanManage;
  private Set<Enclosable> pens = new HashSet<>();

  public Zookeeper(String name, Set<PenType> penTypesCanManage) {
    this.name = name;
    this.penTypesCanManage = EnumSet.copyOf(penTypesCanManage);
  }

  public String getName() {
    return name;
  }

  /**
   * @return a set of {@link PenType} that this Zookeeper can manage.
   */
  public Set<PenType> getPenTypesCanManage() {
    return penTypesCanManage;
  }

  /**
   * @return a Set of {@link Enclosable} that this zookeeeper currently manages
   */
  public Set<Enclosable> getPens() {
    return pens;
  }

  /**
   * Attempts to add another pen for the Zookeeper to manage
   *
   * @param pen {@link Enclosable} to add
   * @return {@code true} if pen successfully added, {@code false} otherwise
   */
  public boolean addPen(Enclosable pen) {
    return canManagerPen(pen) && pens.add(pen);
  }

  /**
   * Establishes if the Zookeeper is capable of managing a specific pen
   *
   * @param pen {@link Enclosable} to check
   * @return {@code true} if the zookeeper can manage this pen, {@code false} if not.
   */
  public boolean canManagerPen(Enclosable pen) {
    return penTypesCanManage.contains(pen.getType());
  }

  /**
   * Removes a pen from the collection of pens this zookeeper manages
   *
   * @param pen {@link Enclosable} to remove
   * @return {@code true} if pen removed successfully, {@code false} otherwise.
   */
  public boolean removePen(Enclosable pen) {
    return pens.remove(pen);
  }

  @Override
  public String toString() {
    return name;
  }
}
```

```java
package com.example.jhalloran.zoo.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import com.example.jhalloran.zoo.model.pen.DryPen;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jhalloran on 1/9/18.
 */

public final class ZookeeperTest {
  private static final PenType DEFAULT_TYPE = PenType.DRY;
  private static final String DEFAULT_NAME = "Hardip";

  private Zookeeper zookeeper = createDefaultZookeeper();

  @Before
  public void setUp() {
    zookeeper = createDefaultZookeeper();
  }

  @Test
  public void createZooKeeper_withNameAndPen() {
    assertEquals(zookeeper.getName(), DEFAULT_NAME);
    assertEquals(zookeeper.getPenTypesCanManage(), EnumSet.of(DEFAULT_TYPE));
  }

  @Test
  public void zookeeper_addPen() {
    DryPen pen = createDefaultDryPen();
    assertTrue(zookeeper.addPen(pen));
    assertEquals(zookeeper.getPens(), Collections.singleton(pen));
  }

  @Test
  public void zookeeper_cannotAddSamePenTwice() {
    DryPen pen = createDefaultDryPen();
    assertTrue(zookeeper.addPen(pen));
    assertFalse(zookeeper.addPen(pen));
    assertEquals(zookeeper.getPens(), Collections.singleton(pen));
  }

  @Test
  public void zookeeper_removePen() {
    DryPen pen = createDefaultDryPen();
    zookeeper.addPen(pen);
    zookeeper.removePen(pen);
    assertTrue(zookeeper.getPens().isEmpty());
  }

  @Test
  public void zookeeper_removePenNotPresent_returnsFalse() {
    Boolean successful = zookeeper.removePen(createDefaultDryPen());
    assertFalse(successful);
  }

  @Test
  public void toString_returnType(){
    assertEquals(zookeeper.toString(), DEFAULT_NAME);
  }

  private Zookeeper createDefaultZookeeper() {
    return new Zookeeper(DEFAULT_NAME, EnumSet.of(DEFAULT_TYPE));
  }

  private DryPen createDefaultDryPen() {
    return new DryPen("Dry pen", 10, 10, 21);
  }
}
```

```java
package com.example.jhalloran.zoo.model.animal;

import com.example.jhalloran.zoo.model.pen.Enclosable;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.util.Set;

/**
 * Defines Animal API
 */
public interface Animal {

  /**
   * @return Animal's name
   */
  String getName();

  /**
   * @return {@link PenType} where this animal can live
   */
  Set<PenType> getPenTypes();

  /**
   * return {@code true} if the animal is considered too dangerous to share a {@link Enclosable}.
   */
  boolean isDangerous();

  /**
   * @return the land required by this animal (metre squared)
   */
  int getLandAreaRequired();

  /**
   * @return {@code true} if this animal is assigned to a pen, {@code false} otherwise
   */
  boolean isAssigned();

  /**
   * @return The {@link Enclosable} to which this animal is assigned
   */
  Enclosable getAssignedToPen();

  /**
   * Assigns this animal to a new {@link Enclosable}
   *
   * @param pen {@link Enclosable} to which the animal will be assigned to
   * @return {@code true} if assignment is successful, {@code false} otherwise
   */
  boolean assignToPen(Enclosable pen);
}
```

```java
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
```

```java
package com.example.jhalloran.zoo.model.animal;

import com.example.jhalloran.zoo.model.shared.WaterType;
import java.util.Set;

/**
 *Defines mixin API for {@link Animal} than can swim.
 */
public interface Swimmer {

  /**
   * @return the {@link WaterType} in which these animals can live. Note, this is a set to
   * accommodate euryhaline animals.
   */
  Set<WaterType> getWaterTypes();

  /**
   * @return the water volume required for a animal, metre cubed.
   */
  int getWaterVolumeRequired();

}
```


```java
package com.example.jhalloran.zoo.model.animal;

/**
 * Created by jhalloran on 1/8/18.
 */

import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.Set;

/**
 * An animal that lives on land only (i.e. cannot fly or swim)
 */
public class LandAnimal extends AbstractAnimal implements Serializable {

  public LandAnimal(String name, int landAreaRequired, Set<PenType> penTypes, boolean dangerous) {
    super(name, penTypes, dangerous, landAreaRequired);
  }
}
```

```java
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
```

```java
package com.example.jhalloran.zoo.model.animal;

import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

/**
 * An animal that can fly.
 **/
public class FlyingAnimal extends AbstractAnimal implements Flyer, Serializable {

  private final int airVolumeRequired;

  public FlyingAnimal(
      String name, boolean dangerous, int landAreaRequired, int airVolumeRequired,
      Set<PenType> penTypes) {
    super(name, penTypes, dangerous, landAreaRequired);
    this.airVolumeRequired = airVolumeRequired;
  }

  /**
   * @inheritDoc
   */
  @Override
  public int getAirVolumeRequired() {
    return airVolumeRequired;
  }

  public static class Builder {

    private String name;
    private boolean dangerous;
    private Set<PenType> penTypes;
    private int landAreaRequired;
    private int airVolumeRequired;

    public Builder() {
    }

    public FlyingAnimal.Builder setName(String name) {
      this.name = name;
      return this;
    }

    public FlyingAnimal.Builder setDangerous(boolean dangerous) {
      this.dangerous = dangerous;
      return this;
    }

    public FlyingAnimal.Builder setPenTypes(Set<PenType> penTypes) {
      this.penTypes = EnumSet.copyOf(penTypes);
      return this;
    }

    public FlyingAnimal.Builder setLandAreaRequired(int landAreaRequired) {
      this.landAreaRequired = landAreaRequired;
      return this;
    }

    public FlyingAnimal.Builder setAirVolumeRequired(int airVolumeRequired) {
      this.airVolumeRequired = airVolumeRequired;
      return this;
    }

    public FlyingAnimal build() {
      return new FlyingAnimal(
          this.name,
          this.dangerous,
          this.landAreaRequired,
          this.airVolumeRequired,
          this.penTypes);
    }
  }
}
```

```java
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

  private FlyingAnimal animal = createDefaultLandAnimal();

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
  public void toString_returnsName(){
    assertEquals(animal.toString(), DEFAULT_NAME);
  }

  private FlyingAnimal createDefaultLandAnimal() {
    return new FlyingAnimal.Builder()
          .setName(DEFAULT_NAME)
          .setLandAreaRequired(DEFAULT_LAND_AREA_REQUIRED)
          .setAirVolumeRequired(DEFAULT_AIR_VOLUME_REQUIRED)
          .setPenTypes(EnumSet.of(DEFAULT_PEN_TYPE))
          .build();
  }
}
```

```java
package com.example.jhalloran.zoo.model.animal;

import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

/**
 * An animal that can swim.
 */
public class SwimmingAnimal extends AbstractAnimal implements Swimmer, Serializable {

  private final int waterVolumeRequire;
  private final Set<WaterType> waterTypes;

  public SwimmingAnimal(
      String name,
      boolean dangerous,
      int landAreaRequired,
      int waterVolumeRequired,
      Set<WaterType> waterTypes,
      Set<PenType> penTypes) {
    super(name, penTypes, dangerous, landAreaRequired);
    this.waterTypes = EnumSet.copyOf(waterTypes);
    this.waterVolumeRequire = waterVolumeRequired;
  }

  /**
   * @inheritDoc
   */
  @Override
  public Set<WaterType> getWaterTypes() {
    return waterTypes;
  }

  /**
   * @inheritDoc
   */
  @Override
  public int getWaterVolumeRequired() {
    return waterVolumeRequire;
  }

  public static class Builder {

    private String name;
    private boolean dangerous;
    private Set<PenType> penTypes;
    private int landAreaRequired;
    private int waterVolumeRequired;
    private Set<WaterType> waterTypes;

    public Builder() {
    }

    public SwimmingAnimal.Builder setName(String name) {
      this.name = name;
      return this;
    }

    public SwimmingAnimal.Builder setDangerous(boolean dangerous) {
      this.dangerous = dangerous;
      return this;
    }

    public SwimmingAnimal.Builder setPenTypes(Set<PenType> penTypes) {
      this.penTypes = EnumSet.copyOf(penTypes);
      return this;
    }

    public SwimmingAnimal.Builder setLandAreaRequired(int landAreaRequired) {
      this.landAreaRequired = landAreaRequired;
      return this;
    }

    public SwimmingAnimal.Builder setWaterVolumeRequired(int waterVolumeRequired) {
      this.waterVolumeRequired = waterVolumeRequired;
      return this;
    }

    public SwimmingAnimal.Builder setWaterTypes(Set<WaterType> waterTypes) {
      this.waterTypes = EnumSet.copyOf(waterTypes);
      return this;
    }

    public SwimmingAnimal build() {
      return new SwimmingAnimal(
          this.name,
          this.dangerous,
          this.landAreaRequired,
          this.waterVolumeRequired,
          this.waterTypes,
          this.penTypes);
    }
  }
}
```

```java
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

  private SwimmingAnimal animal = createDefaultSwimmingAnimal();

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
  public void toString_returnsName(){
    assertEquals(animal.toString(), DEFAULT_NAME);
  }

  private SwimmingAnimal createDefaultSwimmingAnimal() {
    return new SwimmingAnimal.Builder()
        .setName(DEFAULT_NAME)
        .setLandAreaRequired(DEFAULT_LAND_AREA_REQUIRED)
        .setWaterVolumeRequired(DEFAULT_WATER_VOLUME_REQUIRED)
        .setWaterTypes(EnumSet.of(DEFAULT_WATER_TYPE))
        .setPenTypes(EnumSet.of(DEFAULT_PEN_TYPE))
        .build();
  }
}
```

```java
package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.util.Set;

/**
 * Defines an API for Zoo enclosures.
 */
public interface Enclosable {

  /**
   * @return pen's name
   */
  String getName();

  /**
   * @return pen temperature celcius
   */
  int getTemperature();

  /**
   * @return {@link PenType} of this pen
   */
  PenType getType();

  /**
   * @return Land area of this pen, metre squared
   */
  int getLandArea();

  /**
   * @return {@link Animal} that live in this pen
   */
  Set<Animal> getAnimals();

  /**
   * @param animal {@link Animal} to add to this pen
   * @return {@code true} if add is successful, {@code false} otherwise
   */
  boolean addAnimal(Animal animal);

  /**
   * @param animal {@link Animal} to remove to this pen
   * @return {@code true} if removal is successful, {@code false} otherwise
   */
  boolean removeAnimal(Animal animal);

  /**
   * Ascertains if an animal can live in this pen. Useful to call before {@link #addAnimal(Animal)}
   * in client code.
   *
   * @param animal {@link Animal} to check
   * @return {@code true} if animal can live here, {@code false} otherwise
   */
  boolean canLiveHere(Animal animal);

  /**
   * @return {@code true} if assigned to a {@link Zookeeper}, {@code false} otherwise
   */
  boolean isAssigned();

  /**
   * @return The {@link Zookeeper} to which this pen is assigned
   */
  Zookeeper getAssignedToZookeeper();

  /**
   * Assigns this pen to a new {@link Zookeeper}
   *
   * @param zookeeper {@link Zookeeper} to which the pen will be assigned to
   * @return {@code true} if assignment is successful, {@code false} otherwise
   */
  boolean assignToZookeeper(Zookeeper zookeeper);
}
```

```java
package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.animal.Flyer;

/**
 * Defines mixin API for {@link Enclosable} than can host {@link Flyer} animals
 */
public interface Flyable {

  /**
   * @return Air volume of this pen, metre cubed
   */
  int getAirVolume();
}
```

```java
package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.animal.Swimmer;
import com.example.jhalloran.zoo.model.shared.WaterType;

/**
 * Defines mixin API for {@link Enclosable} than can host {@link Swimmer} animals
 */
public interface Swimmable {

  /**
   * @return Water volume of this pen, metre cubed
   */
  int getWaterVolume();

  /**
   * @return {@link WaterType} of this pen
   */
  WaterType getWaterType();
}
```

```java
package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.Set;

/**
 * Superclass to be extended to create new types of pens
 */
public abstract class AbstractPen implements Enclosable, Serializable {

  private final PenType type;
  private final String name;
  private final int temperature;
  private final int landArea;
  private Zookeeper zookeeperAssignedTo = null;

  AbstractPen(String name, PenType type, int temperature, int landArea) {
    this.name = name;
    this.type = type;
    this.temperature = temperature;
    this.landArea = landArea;
  }

  /**
   * @inheritDoc
   */
  public String getName() {
    return name;
  }

  /**
   * @inheritDoc
   */
  public int getTemperature() {
    return temperature;
  }

  /**
   * @inheritDoc
   */
  public int getLandArea() {
    return landArea;
  }

  /**
   * @inheritDoc
   */
  public PenType getType() {
    return type;
  }

  /**
   * @inheritDoc
   */
  public abstract Set<Animal> getAnimals();

  /**
   * @inheritDoc
   */
  public abstract boolean addAnimal(Animal animal);

  /**
   * @inheritDoc
   */
  public abstract boolean removeAnimal(Animal animal);

  /**
   * @inheritDoc
   */
  public abstract boolean canLiveHere(Animal animal);

  /**
   * @inheritDoc
   */
  public boolean isAssigned() {
    return (zookeeperAssignedTo != null);
  }

  /**
   * @inheritDoc
   */
  public Zookeeper getAssignedToZookeeper() {
    return zookeeperAssignedTo;
  }

  /**
   * @inheritDoc
   */
  public boolean assignToZookeeper(Zookeeper zookeeper) {
    if (zookeeper.addPen(this)) {
      if (isAssigned()) {
        zookeeperAssignedTo.removePen(this);
      }
      zookeeperAssignedTo = zookeeper;
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return name;
  }
}
```

```java
package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.Swimmer;
import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A pen that can house swimming animals who live exclusively in water
 */
public class AquariumPen extends AbstractPen implements Swimmable, Serializable {

  private static final int LAND_AREA = 0;
  private final WaterType waterType;
  private final int waterVolume;
  private Set<Animal> animals = new HashSet<>();

  public AquariumPen(String name, WaterType waterType, int depth, int length, int width,
      int temperature) {
    super(name, PenType.AQUARIUM, temperature, LAND_AREA);
    this.waterType = waterType;
    waterVolume = depth * length * width;
  }

  /**
   * @inheritDoc
   */
  @Override
  public WaterType getWaterType() {
    return waterType;
  }

  /**
   * @inheritDoc
   */
  @Override
  public int getWaterVolume() {
    return waterVolume;
  }

  /**
   * @inheritDoc
   */
  @Override
  public Set<Animal> getAnimals() {
    return animals;
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean addAnimal(Animal animal) {
    return canLiveHere(animal) && animals.add(animal);
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean removeAnimal(Animal animal) {
    return animals.remove(animal);
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean canLiveHere(Animal animal) {
    if (!(animal instanceof Swimmer)) {
      return false;
    }
    if (animals.size() > 0) {
      if (animal.isDangerous()) {
        // Dangerous animals can only go into an empty pen
        return false;
      }
      if (animals.iterator().next().isDangerous()) {
        // If first animal is dangerous, no more animals can be added.
        return false;
      }
    }
    if (animal.getLandAreaRequired() > LAND_AREA) {
      return false;
    }
    if (!(animal.getPenTypes().contains(super.getType()))) {
      return false;
    }
    Swimmer swimmer = (Swimmer) animal;
    if (swimmer.getWaterVolumeRequired() > calculateRemainingSpace()) {
      return false;
    }
    return swimmer.getWaterTypes().contains(waterType);
  }

  private int calculateRemainingSpace() {
    int volumeCache = waterVolume;
    for (Animal animal : animals) {
      Swimmer swimmer = (Swimmer) animal;
      volumeCache = volumeCache - swimmer.getWaterVolumeRequired();
    }
    return volumeCache;
  }
}
```

```java
package com.example.jhalloran.zoo.model.pen;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.FlyingAnimal;
import com.example.jhalloran.zoo.model.animal.LandAnimal;
import com.example.jhalloran.zoo.model.animal.SwimmingAnimal;
import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.util.Collections;
import java.util.EnumSet;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jhalloran on 1/9/18.
 */

public final class AquariumPenTest {
  private static final PenType DEFAULT_PEN_TYPE = PenType.AQUARIUM;
  private static final String DEFAULT_NAME = "Aquarium";
  private static final int DEFAULT_LENGTH = 5;
  private static final int DEFAULT_WIDTH = 10;
  private static final int DEFAULT_TEMPERATURE = 22;
  private static final int DEFAULT_DEPTH = 8;
  private static final WaterType DEFAULT_WATER_TYPE = WaterType.SALT;

  private AquariumPen pen = createDefaultAquarium();

  @Before
  public void setUp() {
    pen = createDefaultAquarium();
  }

  @Test
  public void createAquariumWithDefaults() {
    assertEquals(pen.getType(), DEFAULT_PEN_TYPE);
    assertEquals(pen.getWaterType(), DEFAULT_WATER_TYPE);
    assertEquals(pen.getWaterVolume(), 400);
  }

  @Test
  public void addAnimal() {
    Animal fish =
        new SwimmingAnimal(
            "fish", false, 0, 10, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_PEN_TYPE));
    assertTrue(pen.addAnimal(fish));
    assertEquals(pen.getAnimals(), Collections.singleton(fish));
  }

  @Test
  public void addSameAnimalTwice_fails() {
    Animal fish =
        new SwimmingAnimal(
            "fish", false, 0, 10, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_PEN_TYPE));
    assertTrue(pen.addAnimal(fish));
    assertFalse(pen.addAnimal(fish));
    assertEquals(pen.getAnimals().size(), 1);
  }

  @Test
  public void addAnimalThatIsTooLargeForPen_fails() {
    Animal whale =
        new SwimmingAnimal(
            "whale", false, 0, 500, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_PEN_TYPE));
    assertFalse(pen.addAnimal(whale));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void addAnimalThatNeedsLand_fails() {
    Animal seal =
        new SwimmingAnimal(
            "seal", false, 10, 100, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_PEN_TYPE));
    assertFalse(pen.addAnimal(seal));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void addAnimalWithWrongPenType_fails() {
    Animal dolphin =
        new SwimmingAnimal(
            "dolphin",
            false,
            0,
            100,
            EnumSet.of(DEFAULT_WATER_TYPE),
            EnumSet.of(PenType.PART_WATER_PART_DRY));
    assertFalse(pen.addAnimal(dolphin));
    assertEquals(pen.getAnimals().size(), 0);  }

  @Test
  public void addLandAnimal_fails() {
    Animal dog = new LandAnimal("dog", 10, EnumSet.of(DEFAULT_PEN_TYPE), false);
    assertFalse(pen.addAnimal(dog));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void addFlyingAnimal_fails() {
    FlyingAnimal bird = new FlyingAnimal("bird", false, 10, 200, EnumSet.of(DEFAULT_PEN_TYPE));
    assertFalse(pen.addAnimal(bird));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void addAnimalOfWrongWaterType_fails() {
    Animal cod =
        new SwimmingAnimal("cod", false, 0, 10, EnumSet.of(WaterType.FRESH), EnumSet.of(DEFAULT_PEN_TYPE));
    assertFalse(pen.addAnimal(cod));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void removeAnimal() {
    Animal cod =
        new SwimmingAnimal(
            "fish", false, 0, 10, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_PEN_TYPE));

    Animal snapper =
        new SwimmingAnimal(
            "fish", false, 0, 10, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_PEN_TYPE));

    pen.addAnimal(cod);
    pen.addAnimal(snapper);

    assertTrue(pen.getAnimals().contains(cod));
    assertTrue(pen.getAnimals().contains(snapper));

    pen.removeAnimal(snapper);

    assertTrue(pen.getAnimals().contains(cod));
    assertEquals(pen.getAnimals().size(), 1);
  }

  @Test
  public void addDangerousAnimalToPen() {
    Animal piranha =
        new SwimmingAnimal(
            "fish", true, 0, 10, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_PEN_TYPE));

    assertTrue(pen.addAnimal(piranha));
    assertEquals(pen.getAnimals().size(), 1);
  }

  @Test
  public void addDangerousAnimalToPenOccupiedBySafeAnimal_fails() {
    Animal cod =
        new SwimmingAnimal(
            "fish", false, 0, 10, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_PEN_TYPE));
    Animal piranha =
        new SwimmingAnimal(
            "fish", true, 0, 10, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_PEN_TYPE));

    assertTrue(pen.addAnimal(cod));
    assertFalse(pen.addAnimal(piranha));
    assertEquals(pen.getAnimals(), Collections.singleton(cod));
  }

  @Test
  public void addDangerousAnimalToPenOccupiedByDangerousAnimal_fails() {
    Animal shark =
        new SwimmingAnimal(
            "fish", true, 0, 10, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_PEN_TYPE));
    Animal piranha =
        new SwimmingAnimal(
            "fish", true, 0, 10, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_PEN_TYPE));
    assertTrue(pen.addAnimal(shark));
    assertFalse(pen.addAnimal(piranha));
    assertEquals(pen.getAnimals(), Collections.singleton(shark));
  }

  @Test
  public void addSafeAnimalToPenOccupiedByDangerousAnimal_fails() {
    Animal cod =
        new SwimmingAnimal(
            "fish", false, 0, 10, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_PEN_TYPE));
    Animal piranha =
        new SwimmingAnimal(
            "fish", true, 0, 10, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_PEN_TYPE));
    assertTrue(pen.addAnimal(piranha));
    assertFalse(pen.addAnimal(cod));
    assertEquals(pen.getAnimals(), Collections.singleton(piranha));
  }

  @Test
  public void toString_returnType(){
    assertEquals(pen.toString(), DEFAULT_NAME);
  }

  private AquariumPen createDefaultAquarium() {
    return new AquariumPen(
        DEFAULT_NAME, DEFAULT_WATER_TYPE, DEFAULT_LENGTH, DEFAULT_WIDTH, DEFAULT_DEPTH, DEFAULT_TEMPERATURE);
  }
}
```

```java
package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.Flyer;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A pen that can house flying animals
 */
public class AviaryPen extends AbstractPen implements Flyable, Serializable {

  private final int airVolume;
  private Set<Animal> animals = new HashSet<>();

  public AviaryPen(String name, int length, int width, int height, int temperature) {
    super(name, PenType.AVIARY, temperature, (length * width));
    this.airVolume = length * width * height;
  }

  /**
   * @inheritDoc
   */
  @Override
  public int getAirVolume() {
    return airVolume;
  }

  /**
   * @inheritDoc
   */
  @Override
  public Set<Animal> getAnimals() {
    return animals;
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean addAnimal(Animal animal) {
    return canLiveHere(animal) && animals.add(animal);
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean removeAnimal(Animal animal) {
    return animals.remove(animal);
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean canLiveHere(Animal animal) {
    if (!(animal instanceof Flyer)) {
      return false;
    }
    if (!(animal.getPenTypes().contains(super.getType()))) {
      return false;
    }
    if (animals.size() > 0) {
      if (animal.isDangerous()) {
        // Dangerous animals can only go into an empty pen
        return false;
      }
      if (animals.iterator().next().isDangerous()) {
        // If first animal is dangerous, no more animals can be added.
        return false;
      }
    }
    Flyer flyer = (Flyer) animal;
    if (flyer.getAirVolumeRequired() > calculateRemainingSpace()) {
      return false;
    }
    return true;
  }

  private int calculateRemainingSpace() {
    int airVolumeCache = airVolume;
    for (Animal animal : animals) {
      Flyer flyer = (Flyer) animal;
      airVolumeCache = airVolumeCache - flyer.getAirVolumeRequired();
    }
    return airVolumeCache;
  }
}
```

```java
package com.example.jhalloran.zoo.model.pen;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.FlyingAnimal;
import com.example.jhalloran.zoo.model.animal.LandAnimal;
import com.example.jhalloran.zoo.model.animal.SwimmingAnimal;
import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.util.Collections;
import java.util.EnumSet;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jhalloran on 1/9/18.
 */

public final class AviaryPenTest {
  private static final PenType DEFAULT_TYPE = PenType.AVIARY;
  private static final String DEFAULT_NAME = "Aviary";
  private static final int DEFAULT_LENGTH = 5;
  private static final int DEFAULT_WIDTH = 10;
  private static final int DEFAULT_HEIGHT = 7;
  private static final int DEFAULT_TEMPERATURE = 22;

  private AviaryPen pen = createDefaultAviary();
  @Before
  public void setUp() {
    pen = createDefaultAviary();
  }

  @Test
  public void createDryPenWithDefaults() {
    assertEquals(pen.getName(), DEFAULT_NAME);
    assertEquals(pen.getType(), DEFAULT_TYPE);
    assertEquals(pen.getLandArea(), 50); // 5 * 10
    assertEquals(pen.getAirVolume(), 350); // 5 * 10 * 7
    assertEquals(pen.getTemperature(), DEFAULT_TEMPERATURE);
  }

  @Test
  public void addAnimalToPen() {
    FlyingAnimal bird = new FlyingAnimal("bird", false, 10, 200, EnumSet.of(DEFAULT_TYPE));
    assertTrue(pen.addAnimal(bird));
    assertEquals(pen.getAnimals(), Collections.singleton(bird));
  }

  @Test
  public void addSameAnimalTwice_fails() {
    FlyingAnimal bird = new FlyingAnimal("bird", false, 10, 200, EnumSet.of(DEFAULT_TYPE));
    assertTrue(pen.addAnimal(bird));
    assertFalse(pen.addAnimal(bird));
    assertEquals(pen.getAnimals().size(), 1);
  }

  @Test
  public void addAnimalWithWrongPenType_fails() {
    FlyingAnimal owl = new FlyingAnimal("owl", false, 10, 200, EnumSet.of(PenType.PETTING));
    assertFalse(pen.addAnimal(owl));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void addAnimalThatIsTooLargeForPen_fails() {
    FlyingAnimal eagle = new FlyingAnimal("eagle", false, 80, 400, EnumSet.of(DEFAULT_TYPE));
    assertFalse(pen.addAnimal(eagle));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void addLandAnimal_fails() {
    Animal dog = new LandAnimal("dog", 10, EnumSet.of(DEFAULT_TYPE),false);
    assertFalse(pen.addAnimal(dog));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void addSwimmingAnimal_fails() {
    Animal seal =
        new SwimmingAnimal("seal", false, 10,  100, EnumSet.of(WaterType.FRESH), EnumSet.of(DEFAULT_TYPE));
    assertFalse(pen.addAnimal(seal));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void removeAnimal() {
    FlyingAnimal eagle = new FlyingAnimal("bird", false, 10, 100, EnumSet.of(DEFAULT_TYPE));

    FlyingAnimal hawk = new FlyingAnimal("bird", false, 10, 100, EnumSet.of(DEFAULT_TYPE));

    pen.addAnimal(eagle);
    pen.addAnimal(hawk);

    assertTrue(pen.getAnimals().contains(eagle));
    assertTrue(pen.getAnimals().contains(hawk));

    pen.removeAnimal(hawk);

    assertTrue(pen.getAnimals().contains(eagle));
    assertEquals(pen.getAnimals().size(), 1);
  }

  @Test
  public void addDangerousAnimalToPen() {
    FlyingAnimal evilBird = new FlyingAnimal("bird", true, 10, 40, EnumSet.of(DEFAULT_TYPE));
    assertTrue(pen.addAnimal(evilBird));
    assertEquals(pen.getAnimals().size(), 1);
  }

  @Test
  public void addDangerousAnimalToPenOccupiedBySafeAnimal_fails() {
    FlyingAnimal bird = new FlyingAnimal("bird", false, 10, 40, EnumSet.of(DEFAULT_TYPE));
    FlyingAnimal evilBird = new FlyingAnimal("bird", true, 10, 40, EnumSet.of(DEFAULT_TYPE));
    assertTrue(pen.addAnimal(bird));
    assertFalse(pen.addAnimal(evilBird));
    assertEquals(pen.getAnimals(), Collections.singleton(bird));
  }

  @Test
  public void addDangerousAnimalToPenOccupiedByDangerousAnimal_fails() {
    FlyingAnimal dangerBird = new FlyingAnimal("bird", true, 10, 40, EnumSet.of(DEFAULT_TYPE));
    FlyingAnimal evilBird = new FlyingAnimal("bird", true, 10, 40, EnumSet.of(DEFAULT_TYPE));
    assertTrue(pen.addAnimal(dangerBird));
    assertFalse(pen.addAnimal(evilBird));
    assertEquals(pen.getAnimals(), Collections.singleton(dangerBird));
  }

  @Test
  public void addSafeAnimalToPenOccupiedByDangerousAnimal_fails() {
    FlyingAnimal bird = new FlyingAnimal("bird", false, 10, 40, EnumSet.of(DEFAULT_TYPE));
    FlyingAnimal evilBird = new FlyingAnimal("bird", true, 10, 40, EnumSet.of(DEFAULT_TYPE));
    assertTrue(pen.addAnimal(evilBird));
    assertFalse(pen.addAnimal(bird));
    assertEquals(pen.getAnimals(), Collections.singleton(evilBird));
  }

  @Test
  public void toString_returnType(){
    assertEquals(pen.toString(), DEFAULT_NAME);
  }

  private AviaryPen createDefaultAviary() {
    return new AviaryPen(DEFAULT_NAME, DEFAULT_LENGTH, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_TEMPERATURE);
  }
}
```

```java
package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.Flyer;
import com.example.jhalloran.zoo.model.animal.Swimmer;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A pen that can house land animals who cannot swim or fly
 */
public class DryPen extends AbstractPen implements Serializable {

  private Set<Animal> animals = new HashSet<>();

  public DryPen(String name, int length, int width, int temperature) {
    this(name, PenType.DRY, length, width, temperature);
  }

  // Allows subclasses to override type
  DryPen(String name, PenType type, int length, int width, int temperature) {
    super(name, type, temperature, (length * width));
  }

  /**
   * @inheritDoc
   */
  @Override
  public Set<Animal> getAnimals() {
    return animals;
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean addAnimal(Animal animal) {
    return canLiveHere(animal) && animals.add(animal);
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean removeAnimal(Animal animal) {
    return animals.remove(animal);
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean canLiveHere(Animal animal) {
    if (animal.getLandAreaRequired() > calculateRemainingSpace()) {
      return false;
    }
    if (animals.size() > 0) {
      if (animal.isDangerous()) {
        // Dangerous animals can only go into an empty pen
        return false;
      }
      if (animals.iterator().next().isDangerous()) {
        // If first animal is dangerous, no more animals can be added.
        return false;
      }
    }
    if (!(animal.getPenTypes().contains(super.getType()))) {
      return false;
    }
    if (animal instanceof Swimmer) {
      return false;
    }
    return !(animal instanceof Flyer);
  }

  private int calculateRemainingSpace() {
    int areaCache = super.getLandArea();
    for (Animal animal : animals) {
      areaCache = areaCache - animal.getLandAreaRequired();
    }
    return areaCache;
  }
}
```

```java
package com.example.jhalloran.zoo.model.pen;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.FlyingAnimal;
import com.example.jhalloran.zoo.model.animal.LandAnimal;
import com.example.jhalloran.zoo.model.animal.SwimmingAnimal;
import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.util.Collections;
import java.util.EnumSet;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jhalloran on 1/9/18.
 */

public final class DryPenTest {
  private static final PenType DEFAULT_TYPE = PenType.DRY;
  private static final String DEFAULT_NAME = "Dry pen";
  private static final int DEFAULT_LENGTH = 5;
  private static final int DEFAULT_WIDTH = 10;
  private static final int DEFAULT_TEMPERATURE = 22;

  private DryPen pen = createDefaultDryPen();

  @Before
  public void setUp() {
    pen = createDefaultDryPen();
  }

  @Test
  public void createDryPenWithDefaults() {
    assertEquals(pen.getName(), DEFAULT_NAME);
    assertEquals(pen.getType(), DEFAULT_TYPE);
    assertEquals(pen.getLandArea(), 50);
    assertEquals(pen.getTemperature(), DEFAULT_TEMPERATURE);
  }

  @Test
  public void addAnimalToPen() {
    Animal cat = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), false);
    assertTrue(pen.addAnimal(cat));
    assertEquals(pen.getAnimals(), Collections.singleton(cat));
  }

  @Test
  public void addSameAnimalTwice_fails() {
    Animal cat = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), false);
    assertTrue(pen.addAnimal(cat));
    assertFalse(pen.addAnimal(cat));
    assertEquals(pen.getAnimals().size(), 1);
  }

  @Test
  public void addAnimalThatIsTooLargeForPen_fails() {
    DryPen smallPen = new DryPen(DEFAULT_NAME, 2, 2, DEFAULT_TEMPERATURE);
    Animal elephant = new LandAnimal("elephant", 50, EnumSet.of(DEFAULT_TYPE), false);
    assertFalse(smallPen.addAnimal(elephant));
    assertEquals(smallPen.getAnimals().size(), 0);
  }

  @Test
  public void addAnimalWithWrongPenType_fails() {
    Animal cat = new LandAnimal("cat", 50, EnumSet.of(PenType.PETTING), false);
    assertFalse(pen.addAnimal(cat));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void addFlyingAnimal_fails() {
    FlyingAnimal bird = new FlyingAnimal("bird", false, 10, 200, EnumSet.of(DEFAULT_TYPE));
    assertFalse(pen.addAnimal(bird));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void addSwimmingAnimal_fails() {
    Animal seal =
        new SwimmingAnimal("seal", false, 10, 100, EnumSet.of(WaterType.FRESH), EnumSet.of(DEFAULT_TYPE));
    assertFalse(pen.addAnimal(seal));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void removeAnimal() {
    Animal cat = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), false);
    Animal dog = new LandAnimal("dog", 15, EnumSet.of(DEFAULT_TYPE), false);

    assertTrue(pen.addAnimal(cat));
    assertTrue(pen.addAnimal(dog));

    assertTrue(pen.getAnimals().contains(cat));
    assertTrue(pen.getAnimals().contains(dog));

    pen.removeAnimal(cat);

    assertTrue(pen.getAnimals().contains(dog));
    assertEquals(pen.getAnimals().size(), 1);
  }

  @Test
  public void assignPenToZookeeper() {
    Zookeeper zookeeper = new Zookeeper("Jane", EnumSet.of(DEFAULT_TYPE));
    pen.assignToZookeeper(zookeeper);

    assertTrue(pen.isAssigned());
    assertEquals(pen.getAssignedToZookeeper(), zookeeper);
  }

  @Test
  public void assignPenToSecondZookeeper_updates() {
    Zookeeper zookeeperOne = new Zookeeper("Jane", EnumSet.of(DEFAULT_TYPE));
    Zookeeper zookeeperTwo = new Zookeeper("Fred", EnumSet.of(DEFAULT_TYPE));

    assertTrue(pen.assignToZookeeper(zookeeperOne));
    assertTrue(pen.assignToZookeeper(zookeeperTwo));

    assertEquals(pen.getAssignedToZookeeper(), zookeeperTwo);
    assertTrue(zookeeperTwo.getPens().contains(pen));
    assertFalse(zookeeperOne.getPens().contains(pen));
  }
  
  @Test
  public void addDangerousAnimalToPen() {
    Animal cat = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), true);
    assertTrue(pen.addAnimal(cat));
    assertEquals(pen.getAnimals().size(), 1);
  }
  
  @Test
  public void addDangerousAnimalToPenOccupiedBySafeAnimal_fails() {
    Animal cat = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), false);
    Animal dangerCat = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), true);
    assertTrue(pen.addAnimal(cat));
    assertFalse(pen.addAnimal(dangerCat));
    assertEquals(pen.getAnimals(), Collections.singleton(cat));
  }
  
  @Test
  public void addDangerousAnimalToPenOccupiedByDangerousAnimal_fails() {
    Animal lion = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), true);
    Animal dangerCat = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), true);
    assertTrue(pen.addAnimal(lion));
    assertFalse(pen.addAnimal(dangerCat));
    assertEquals(pen.getAnimals(), Collections.singleton(lion));
  }

  @Test
  public void addSafeAnimalToPenOccupiedByDangerousAnimal_fails() {
    Animal lion = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), true);
    Animal cat = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), false);
    assertTrue(pen.addAnimal(lion));
    assertFalse(pen.addAnimal(cat));
    assertEquals(pen.getAnimals(), Collections.singleton(lion));
  }
    
  @Test
  public void toString_returnType(){
    assertEquals(pen.toString(), DEFAULT_NAME);
  }

  private DryPen createDefaultDryPen() {
    return new DryPen(DEFAULT_NAME, DEFAULT_LENGTH, DEFAULT_WIDTH, DEFAULT_TEMPERATURE);
  }
}
```

```java
package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;

/**
 * A pen that can house petting animals
 */
public class PettingPen extends DryPen implements Serializable {

  public PettingPen(String name, int length, int width, int temperature) {
    super(name, PenType.PETTING, length, width, temperature);
  }
}
```

```
package com.example.jhalloran.zoo.model.pen;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.FlyingAnimal;
import com.example.jhalloran.zoo.model.animal.LandAnimal;
import com.example.jhalloran.zoo.model.animal.SwimmingAnimal;
import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.util.Collections;
import java.util.EnumSet;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jhalloran on 1/26/18.
 */
public class PettingPenTest {
  private static final PenType DEFAULT_TYPE = PenType.PETTING;
  private static final String DEFAULT_NAME = "Petting pen";
  private static final int DEFAULT_LENGTH = 5;
  private static final int DEFAULT_WIDTH = 10;
  private static final int DEFAULT_TEMPERATURE = 22;

  private PettingPen pen = createDefaultPettingPen();

  @Before
  public void setUp() {
    pen = createDefaultPettingPen();
  }

  @Test
  public void createPettingPenWithDefaults() {
    assertEquals(pen.getName(), DEFAULT_NAME);
    assertEquals(pen.getType(), DEFAULT_TYPE);
    assertEquals(pen.getLandArea(), 50); // 5 * 10
    assertEquals(pen.getTemperature(), DEFAULT_TEMPERATURE);
  }

  @Test
  public void addAnimalToPen() {
    Animal cat = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), false);
    assertTrue(pen.addAnimal(cat));
    assertEquals(pen.getAnimals(), Collections.singleton(cat));
  }

  @Test
  public void addSameAnimalTwice_fails() {
    Animal cat = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), false);
    assertTrue(pen.addAnimal(cat));
    assertFalse(pen.addAnimal(cat));
    assertEquals(pen.getAnimals().size(), 1);
  }

  @Test
  public void addAnimalThatIsTooLargeForPen_fails() {
    DryPen smallPen = new DryPen(DEFAULT_NAME, 2, 2, DEFAULT_TEMPERATURE);
    Animal elephant = new LandAnimal("elephant", 50, EnumSet.of(DEFAULT_TYPE), false);
    assertFalse(smallPen.addAnimal(elephant));
    assertEquals(smallPen.getAnimals().size(), 0);
  }

  @Test
  public void addAnimalWithWrongPenType_fails() {
    Animal cat = new LandAnimal("cat", 50, EnumSet.of(PenType.DRY), false);
    assertFalse(pen.addAnimal(cat));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void addFlyingAnimal_fails() {
    FlyingAnimal bird = new FlyingAnimal("bird", false, 10, 200, EnumSet.of(DEFAULT_TYPE));
    assertFalse(pen.addAnimal(bird));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void addSwimmingAnimal_fails() {
    Animal seal =
        new SwimmingAnimal("seal", false, 10, 100, EnumSet.of(WaterType.FRESH), EnumSet.of(DEFAULT_TYPE));
    assertFalse(pen.addAnimal(seal));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void removeAnimal() {
    Animal cat = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), false);
    Animal dog = new LandAnimal("dog", 15, EnumSet.of(DEFAULT_TYPE), false);

    assertTrue(pen.addAnimal(cat));
    assertTrue(pen.addAnimal(dog));

    assertTrue(pen.getAnimals().contains(cat));
    assertTrue(pen.getAnimals().contains(dog));

    pen.removeAnimal(cat);

    assertTrue(pen.getAnimals().contains(dog));
    assertEquals(pen.getAnimals().size(), 1);
  }

  @Test
  public void assignPenToZookeeper() {
    Zookeeper zookeeper = new Zookeeper("Jane", EnumSet.of(DEFAULT_TYPE));
    pen.assignToZookeeper(zookeeper);

    assertTrue(pen.isAssigned());
    assertEquals(pen.getAssignedToZookeeper(), zookeeper);
  }

  @Test
  public void assignPenToSecondZookeeper_updates() {
    Zookeeper zookeeperOne = new Zookeeper("Jane", EnumSet.of(DEFAULT_TYPE));
    Zookeeper zookeeperTwo = new Zookeeper("Fred", EnumSet.of(DEFAULT_TYPE));

    assertTrue(pen.assignToZookeeper(zookeeperOne));
    assertTrue(pen.assignToZookeeper(zookeeperTwo));

    assertEquals(pen.getAssignedToZookeeper(), zookeeperTwo);
    assertTrue(zookeeperTwo.getPens().contains(pen));
    assertFalse(zookeeperOne.getPens().contains(pen));
  }

  @Test
  public void addDangerousAnimalToPen() {
    Animal cat = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), true);
    assertTrue(pen.addAnimal(cat));
    assertEquals(pen.getAnimals().size(), 1);
  }

  @Test
  public void addDangerousAnimalToPenOccupiedBySafeAnimal_fails() {
    Animal cat = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), false);
    Animal dangerCat = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), true);
    assertTrue(pen.addAnimal(cat));
    assertFalse(pen.addAnimal(dangerCat));
    assertEquals(pen.getAnimals(), Collections.singleton(cat));
  }

  @Test
  public void addDangerousAnimalToPenOccupiedByDangerousAnimal_fails() {
    Animal lion = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), true);
    Animal dangerCat = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), true);
    assertTrue(pen.addAnimal(lion));
    assertFalse(pen.addAnimal(dangerCat));
    assertEquals(pen.getAnimals(), Collections.singleton(lion));
  }

  @Test
  public void addSafeAnimalToPenOccupiedByDangerousAnimal_fails() {
    Animal lion = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), true);
    Animal cat = new LandAnimal("cat", 10, EnumSet.of(DEFAULT_TYPE), false);
    assertTrue(pen.addAnimal(lion));
    assertFalse(pen.addAnimal(cat));
    assertEquals(pen.getAnimals(), Collections.singleton(lion));
  }

  @Test
  public void toString_returnType(){
    assertEquals(pen.toString(), DEFAULT_NAME);
  }

  private PettingPen createDefaultPettingPen() {
    return new PettingPen(DEFAULT_NAME, DEFAULT_LENGTH, DEFAULT_WIDTH, DEFAULT_TEMPERATURE);
  }
}
```

```java
package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.Swimmer;
import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A pen that can house amphibious and semiaquatic animals
 */
public class PartWaterPartDryPen implements Enclosable, Swimmable, Serializable {

  private static final PenType TYPE = PenType.PART_WATER_PART_DRY;
  private final DryPen landSection;
  private final AquariumPen waterSection;
  private Set<Animal> animals = new HashSet<>();
  private Zookeeper zookeeperAssignedTo = null;

  public PartWaterPartDryPen(DryPen landSection, AquariumPen waterSection) {
    this.landSection = landSection;
    this.waterSection = waterSection;
  }

  /**
   * @inheritDoc
   */
  @Override
  public String getName() {
    return landSection.getName();
  }

  /**
   * @inheritDoc
   */
  @Override
  public PenType getType() {
    return TYPE;
  }

  /**
   * @inheritDoc
   */
  @Override
  public int getLandArea() {
    return landSection.getLandArea();
  }

  /**
   * @inheritDoc
   */
  @Override
  public int getTemperature() {
    // Mean temperature of two sections
    return (landSection.getTemperature() + waterSection.getTemperature()) / 2;
  }

  /**
   * @inheritDoc
   */
  @Override
  public WaterType getWaterType() {
    return waterSection.getWaterType();
  }

  /**
   * @inheritDoc
   */
  @Override
  public int getWaterVolume() {
    return waterSection.getWaterVolume();
  }

  /**
   * @inheritDoc
   */
  @Override
  public Set<Animal> getAnimals() {
    return animals;
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean removeAnimal(Animal animal) {
    return animals.remove(animal);
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean addAnimal(Animal animal) {
    return canLiveHere(animal) && animals.add(animal);
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean canLiveHere(Animal animal) {
    if (!(animal instanceof Swimmer)) {
      return false;
    }
    if (!(animal.getPenTypes().contains(TYPE))) {
      return false;
    }
    if (animals.size() > 0) {
      if (animal.isDangerous()) {
        // Dangerous animals can only go into an empty pen
        return false;
      }
      if (animals.iterator().next().isDangerous()) {
        // If first animal is dangerous, no more animals can be added.
        return false;
      }
    }
    if (animal.getLandAreaRequired() > calculateRemainingLandArea()) {
      return false;
    }
    Swimmer swimmer = (Swimmer) animal;
    if (swimmer.getWaterVolumeRequired() > calculateRemainingWaterVolume()) {
      return false;
    }
    if (!(swimmer.getWaterTypes().contains(waterSection.getWaterType()))) {
      return false;
    }
    return true;
  }

  private int calculateRemainingWaterVolume() {
    int volumeCache = waterSection.getWaterVolume();
    for (Animal animal : animals) {
      Swimmer swimmer = (Swimmer) animal;
      volumeCache = volumeCache - swimmer.getWaterVolumeRequired();
    }
    return volumeCache;
  }

  private int calculateRemainingLandArea() {
    int areaCache = landSection.getLandArea();
    for (Animal animal : animals) {
      areaCache = areaCache - animal.getLandAreaRequired();
    }
    return areaCache;
  }

  /**
   * @inheritDoc
   */
  public boolean isAssigned() {
    return (zookeeperAssignedTo != null);
  }

  /**
   * @inheritDoc
   */
  public Zookeeper getAssignedToZookeeper() {
    return zookeeperAssignedTo;
  }

  /**
   * @inheritDoc
   */
  public boolean assignToZookeeper(Zookeeper zookeeper) {
    if (zookeeper.addPen(this)) {
      if (isAssigned()) {
        zookeeperAssignedTo.removePen(this);
      }
      zookeeperAssignedTo = zookeeper;
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return getName();
  }
}
```

```java
package com.example.jhalloran.zoo.model.pen;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.FlyingAnimal;
import com.example.jhalloran.zoo.model.animal.LandAnimal;
import com.example.jhalloran.zoo.model.animal.SwimmingAnimal;
import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.util.Collections;
import java.util.EnumSet;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jhalloran on 1/26/18.
 */
public final class PartWaterPartDryPenTest {
  private static final PenType DEFAULT_TYPE = PenType.PART_WATER_PART_DRY;
  private static final String DEFAULT_NAME = "Part Wet Part Dry";
  private static final int DEFAULT_LENGTH = 5;
  private static final int DEFAULT_WIDTH = 10;
  private static final int DEFAULT_TEMPERATURE = 22;
  private static final int DEFAULT_DEPTH = 8;
  private static final WaterType DEFAULT_WATER_TYPE = WaterType.SALT;

  private PartWaterPartDryPen pen = createDefaultPartWaterParDryPen();

  @Before
  public void setUp() {
    pen = createDefaultPartWaterParDryPen();
  }

  @Test
  public void createDryPenWithDefaults() {
    assertEquals(pen.getName(), DEFAULT_NAME);
    assertEquals(pen.getType(), DEFAULT_TYPE);
    assertEquals(pen.getLandArea(), 50);
    assertEquals(pen.getTemperature(), DEFAULT_TEMPERATURE + 5); // (22 + (22 + 10)) / 2 = 22 + 5
    assertEquals(pen.getWaterType(), DEFAULT_WATER_TYPE);
    assertEquals(pen.getWaterVolume(), 400);
  }

  @Test
  public void addAnimalToPen() {
    Animal seal =
        new SwimmingAnimal(
            "seal", false,10, 100, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_TYPE));
    assertTrue(pen.addAnimal(seal));
    assertEquals(pen.getAnimals().size(), 1);
    assertTrue(pen.getAnimals().contains(seal));
  }

  @Test
  public void addAnimalThatIsTooLargeForWaterSection_throws() {
    Animal seaOtter =
        new SwimmingAnimal(
            "Sea Otter", false, 40, 500, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_TYPE));

    assertFalse(pen.addAnimal(seaOtter));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void addAnimalThatIsTooLargeForLandSection_throws() {
    Animal bullElephantSeal =
        new SwimmingAnimal(
            "Bull Elephant Seal",
            false,
            100,
            300,
            EnumSet.of(DEFAULT_WATER_TYPE),
            EnumSet.of(DEFAULT_TYPE));

    assertFalse(pen.addAnimal(bullElephantSeal));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void addAnimalThatIsWrongTypeForPen_throws() {
    Animal seal =
        new SwimmingAnimal(
            "seal", false, 10, 100, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(PenType.AQUARIUM));

    assertFalse(pen.addAnimal(seal));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void addLandAnimal_throws() {
    Animal dog = new LandAnimal("dog", 10, EnumSet.of(DEFAULT_TYPE), false);

    assertFalse(pen.addAnimal(dog));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void addFlyingAnimal_throws() {
    FlyingAnimal bird = new FlyingAnimal("bird", false, 10, 200, EnumSet.of(DEFAULT_TYPE));

    assertFalse(pen.addAnimal(bird));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void addAnimalofWrongWaterType_throws() {
    Animal cod =
        new SwimmingAnimal("cod", false, 0, 10, EnumSet.of(WaterType.FRESH), EnumSet.of(
            DEFAULT_TYPE));
    assertFalse(pen.addAnimal(cod));
    assertEquals(pen.getAnimals().size(), 0);
  }

  @Test
  public void removeAnimal() {
    Animal seal =
        new SwimmingAnimal(
            "seal", false,10, 100, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_TYPE));
    Animal penguin =
        new SwimmingAnimal(
            "seal", false,10, 100, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_TYPE));

    assertTrue(pen.addAnimal(seal));
    assertTrue(pen.addAnimal(penguin));

    assertTrue(pen.getAnimals().contains(seal));
    assertTrue(pen.getAnimals().contains(penguin));

    pen.removeAnimal(penguin);

    assertTrue(pen.getAnimals().contains(seal));
    assertEquals(pen.getAnimals().size(), 1);
  }

  @Test
  public void assignPenToZookeeper() {
    Zookeeper zookeeper = new Zookeeper("Jane", EnumSet.of(DEFAULT_TYPE));
    pen.assignToZookeeper(zookeeper);

    assertTrue(pen.isAssigned());
    assertEquals(pen.getAssignedToZookeeper(), zookeeper);
  }

  @Test
  public void assignPenToSecondZookeeper_updates() {
    Zookeeper zookeeperOne = new Zookeeper("Jane", EnumSet.of(DEFAULT_TYPE));
    Zookeeper zookeeperTwo = new Zookeeper("Fred", EnumSet.of(DEFAULT_TYPE));

    assertTrue(pen.assignToZookeeper(zookeeperOne));
    assertTrue(pen.assignToZookeeper(zookeeperTwo));

    assertEquals(pen.getAssignedToZookeeper(), zookeeperTwo);
    assertTrue(zookeeperTwo.getPens().contains(pen));
    assertFalse(zookeeperOne.getPens().contains(pen));
  }

  @Test
  public void addDangerousAnimalToPen() {
    Animal killerSeal =
        new SwimmingAnimal(
            "seal", true,10, 100, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_TYPE));

    assertTrue(pen.addAnimal(killerSeal));
    assertEquals(pen.getAnimals().size(), 1);
  }

  @Test
  public void addDangerousAnimalToPenOccupiedBySafeAnimal_fails() {
    Animal killerSeal =
        new SwimmingAnimal(
            "seal", true, 10, 100, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_TYPE));
    Animal penguin =
        new SwimmingAnimal(
            "seal", false,10, 100, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_TYPE));


    assertTrue(pen.addAnimal(penguin));
    assertFalse(pen.addAnimal(killerSeal));
    assertEquals(pen.getAnimals(), Collections.singleton(penguin));
  }

  @Test
  public void addDangerousAnimalToPenOccupiedByDangerousAnimal_fails() {
    Animal killerSeal =
        new SwimmingAnimal(
            "seal", true, 10, 100, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_TYPE));
    Animal polarBear =
        new SwimmingAnimal(
            "polar bear", true,10, 100, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_TYPE));
    assertTrue(pen.addAnimal(killerSeal));
    assertFalse(pen.addAnimal(polarBear));
    assertEquals(pen.getAnimals(), Collections.singleton(killerSeal));
  }

  @Test
  public void addSafeAnimalToPenOccupiedByDangerousAnimal_fails() {
    Animal killerSeal =
        new SwimmingAnimal(
            "seal", true, 10, 100, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_TYPE));
    Animal penguin =
        new SwimmingAnimal(
            "seal", false,10, 100, EnumSet.of(DEFAULT_WATER_TYPE), EnumSet.of(DEFAULT_TYPE));
    assertTrue(pen.addAnimal(killerSeal));
    assertFalse(pen.addAnimal(penguin));
    assertEquals(pen.getAnimals(), Collections.singleton(killerSeal));
  }

  private PartWaterPartDryPen createDefaultPartWaterParDryPen() {
    DryPen dryPen = new DryPen(DEFAULT_NAME, DEFAULT_LENGTH, DEFAULT_WIDTH, DEFAULT_TEMPERATURE);
    AquariumPen aquariumPen =
        new AquariumPen(
            DEFAULT_NAME,
            DEFAULT_WATER_TYPE,
            DEFAULT_DEPTH,
            DEFAULT_LENGTH,
            DEFAULT_WIDTH,
            DEFAULT_TEMPERATURE + 10);
    return new PartWaterPartDryPen(dryPen, aquariumPen);
  }
}
```
