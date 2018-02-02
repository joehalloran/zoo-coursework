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