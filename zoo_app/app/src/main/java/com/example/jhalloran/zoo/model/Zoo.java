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
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jhalloran on 1/8/18.
 */

public class Zoo {
  private String name;
  private List<Animal> animals = new ArrayList<>();
  private List<Zookeeper> zookeepers = new ArrayList<>();
  private List<Enclosable> pens = new ArrayList<>();

  public Zoo(String name) {
    this.name = name;
  }

  public Zoo(String name, boolean useDefaults) {
    this.name = name;
    if (useDefaults) {
      setUpZoo();
    }
  }

  public String getName() {
    return name;
  }

  public List<Animal> getAnimals() {
    return animals;
  }

  public List<Zookeeper> getZookeepers() {
    return zookeepers;
  }

  public List<Enclosable> getPens() {
    return pens;
  }

  public void addAnimal(Animal animal) {
    animals.add(animal);
  }

  public void addZookeeper(Zookeeper zookeeper) {
    zookeepers.add(zookeeper);
  }

  public void addPen(Enclosable pen) {
    pens.add(pen);
  }

  @Override
  public String toString() {
    return name;
  }

  private void setUpZoo() {
    addAnimal(new LandAnimal("Rhino", 20, EnumSet.of(PenType.DRY)));
    addAnimal(new LandAnimal("Zebra", 40, EnumSet.of(PenType.DRY, PenType.PETTING)));
    addAnimal(new FlyingAnimal("Eagle", 40, 400, EnumSet.of(PenType.AVIARY)));
    addAnimal(new SwimmingAnimal("Cod", 50, 500, EnumSet.of(WaterType.SALT), EnumSet.of(PenType.AQUARIUM)));
    addAnimal(new SwimmingAnimal("Shark", 100, 1000, EnumSet.of(WaterType.SALT), EnumSet.of(PenType.AQUARIUM)));

    addPen(new DryPen(10, 10, 21));
    addPen(new AviaryPen(50, 100, 30, 21));
    addPen(new AquariumPen(WaterType.SALT, 20, 150, 100, 17));

    addZookeeper(new Zookeeper("Joe", EnumSet.of(PenType.DRY, PenType.AVIARY, PenType.AQUARIUM)));
    addZookeeper(new Zookeeper("James", EnumSet.of(PenType.DRY)));
  }
}
