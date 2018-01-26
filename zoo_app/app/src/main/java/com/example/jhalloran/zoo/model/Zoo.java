package com.example.jhalloran.zoo.model;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.FlyingAnimal;
import com.example.jhalloran.zoo.model.animal.LandAnimal;
import com.example.jhalloran.zoo.model.animal.SwimmingAnimal;
import com.example.jhalloran.zoo.model.pen.AquariumPen;
import com.example.jhalloran.zoo.model.pen.AviaryPen;
import com.example.jhalloran.zoo.model.pen.DryPen;
import com.example.jhalloran.zoo.model.pen.Enclosure;
import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import com.google.common.annotations.VisibleForTesting;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jhalloran on 1/8/18.
 */

public class Zoo implements Serializable {

  public static final String ZOO_NAME = "Tottenham Hale retail park zoo";
  private static Zoo instance = null;

  private HashMap<UUID, Object> allItems = new HashMap<>();
  private HashMap<UUID, Animal> idsToAnimals = new HashMap<>();
  private HashMap<UUID, Zookeeper> idsToZookeepers = new HashMap<>();
  private HashMap<UUID, Enclosure> idsToPens = new HashMap<>();

  Zoo() {}

  public static Zoo getInstance() {
    if (instance == null) {
      instance = new Zoo();
      instance.setUpZoo();
    }
    return instance;
  }

  public static void initializeZoo(Zoo zoo) {
    instance = zoo;
  }

  public String getName() {
    return ZOO_NAME;
  }

  public List<Animal> getAnimals() {
    return new ArrayList<>(idsToAnimals.values());
  }

  public List<Zookeeper> getZookeepers() {
    return new ArrayList<>(idsToZookeepers.values());
  }

  public List<Enclosure> getPens() {
    return new ArrayList<>(idsToPens.values());
  }

  public Set<UUID> getAnimalIds() {
    return idsToAnimals.keySet();
  }

  public Set<UUID> getZookeeperIds() {
    return idsToZookeepers.keySet();
  }

  public Set<UUID> getPenIds() {
    return idsToPens.keySet();
  }

  public Animal getAnimalById(UUID uuid) {
    return idsToAnimals.get(uuid);
  }

  public Enclosure getPenById(UUID uuid) {
    return idsToPens.get(uuid);
  }

  public Zookeeper getZookeeperById(UUID uuid) {
    return idsToZookeepers.get(uuid);
  }

  public Object getAnyItemById(UUID uuid) {
    return allItems.get(uuid);
  }

  public void addAnimal(Animal animal) {
    if (!getAnimals().contains(animal)) {
      UUID newUuid = UUID.randomUUID();
      idsToAnimals.put(newUuid, animal);
      allItems.put(newUuid, animal);
    }
  }

  public void addZookeeper(Zookeeper zookeeper) {
    if (!getZookeepers().contains(zookeeper)) {
      UUID newUuid = UUID.randomUUID();
      idsToZookeepers.put(newUuid, zookeeper);
      allItems.put(newUuid, zookeeper);
    }
  }

  public void addPen(Enclosure pen) {
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
    addAnimal(new LandAnimal("Rhino", 20, EnumSet.of(PenType.DRY), false));
    addAnimal(new LandAnimal("Zebra", 40, EnumSet.of(PenType.DRY, PenType.PETTING), false));
    addAnimal(new FlyingAnimal("Eagle", false, 40, 400, EnumSet.of(PenType.AVIARY)));
    addAnimal(new SwimmingAnimal("Cod", false, 0, 500, EnumSet.of(WaterType.SALT),
        EnumSet.of(PenType.AQUARIUM)));
    addAnimal(new SwimmingAnimal("Shark", true, 0, 1000, EnumSet.of(WaterType.SALT),
        EnumSet.of(PenType.AQUARIUM)));

    addAnimal(new LandAnimal("Cheetah", 20, EnumSet.of(PenType.DRY), true));
    addAnimal(new LandAnimal("Springbok", 40, EnumSet.of(PenType.DRY, PenType.PETTING), false));
    addAnimal(new FlyingAnimal("Hawk", false, 40, 400, EnumSet.of(PenType.AVIARY)));
    addAnimal(new SwimmingAnimal("Haddock", false, 0, 500, EnumSet.of(WaterType.SALT),
        EnumSet.of(PenType.AQUARIUM)));
    addAnimal(new SwimmingAnimal("Tuna", false, 0, 1000, EnumSet.of(WaterType.SALT),
        EnumSet.of(PenType.AQUARIUM)));

    addAnimal(new LandAnimal("Lion", 20, EnumSet.of(PenType.DRY), true));
    addAnimal(new LandAnimal("Moose", 40, EnumSet.of(PenType.DRY, PenType.PETTING), false));
    addAnimal(new FlyingAnimal("Sparrow", false, 40, 400, EnumSet.of(PenType.AVIARY)));
    addAnimal(new SwimmingAnimal("Hake", false, 0, 500, EnumSet.of(WaterType.SALT),
        EnumSet.of(PenType.AQUARIUM)));
    addAnimal(new SwimmingAnimal("Dolphin", false, 0, 1000, EnumSet.of(WaterType.SALT),
        EnumSet.of(PenType.AQUARIUM)));

    addPen(new DryPen("Dry Pen", 10, 10, 21));
    addPen(new AviaryPen("Aviary", 50, 100, 30, 21));
    addPen(new AquariumPen("Aquarium", WaterType.SALT, 10, 10, 10, 17));
    addPen(new AquariumPen("Big Aquarium", WaterType.SALT, 30, 10, 10, 17));

    addZookeeper(new Zookeeper("Joe", EnumSet.of(PenType.DRY, PenType.AQUARIUM)));
    addZookeeper(new Zookeeper("James", EnumSet.of(PenType.DRY)));
  }
}
