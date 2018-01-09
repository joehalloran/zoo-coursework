package com.example.jhalloran.zoo.model;

import static junit.framework.Assert.assertEquals;

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
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jhalloran on 1/9/18.
 */

public class ZooTest {
  private static final String DEFAULT_ZOO_NAME = "Test zoo";

  private Zoo zoo = new Zoo(DEFAULT_ZOO_NAME);
  private List<Animal> defaultAnimals = new ArrayList<>();
  private List<Enclosable> defaultPens = new ArrayList<>();
  private List<Zookeeper> defaultZookeepers = new ArrayList<>();

  @Before
  public void setUp() {
    zoo = new Zoo(DEFAULT_ZOO_NAME);

    // Create default animals
    defaultAnimals = new ArrayList<>();
    defaultAnimals.add(new LandAnimal("Rhino", 20, EnumSet.of(PenType.DRY)));
    defaultAnimals.add(new LandAnimal("Zebra", 40, EnumSet.of(PenType.DRY, PenType.PETTING)));
    defaultAnimals.add(new FlyingAnimal("Eagle", 40, 400, EnumSet.of(PenType.AVIARY)));
    defaultAnimals.add(new SwimmingAnimal("Cod", 50, 500, EnumSet.of(WaterType.SALT), EnumSet.of(PenType.AQUARIUM)));
    defaultAnimals.add(new SwimmingAnimal("Shark", 100, 1000, EnumSet.of(WaterType.SALT), EnumSet.of(PenType.AQUARIUM)));

    // Create default pens
    defaultPens = new ArrayList<>();
    defaultPens.add(new DryPen(10, 10, 21));
    defaultPens.add(new AviaryPen(50, 100, 30, 21));
    defaultPens.add(new AquariumPen(WaterType.SALT, 20, 150, 100, 17));

    // Create default zookeepers
    defaultZookeepers = new ArrayList<>();
    defaultZookeepers.add(new Zookeeper("Joe", EnumSet.of(PenType.DRY, PenType.AVIARY, PenType.AQUARIUM)));
    defaultZookeepers.add(new Zookeeper("James", EnumSet.of(PenType.DRY)));
  }

  @Test
  public void getName_isDefault() {
    assertEquals(zoo.getName(), DEFAULT_ZOO_NAME);
  }
  @Test
  public void getAnimals_containsDefaults() {
    for (Animal animal : defaultAnimals) {
      zoo.addAnimal(animal);
    }
    assertEquals(zoo.getAnimals(), defaultAnimals);
  }

  @Test
  public void getPens_containsDefaults() {
    for (Enclosable pen: defaultPens) {
      zoo.addPen(pen);
    }
    assertEquals(zoo.getPens(), defaultPens);
  }

  @Test
  public void getZookeepers_containsDefaults() {
    for (Zookeeper zookeeper : defaultZookeepers) {
      zoo.addZookeeper(zookeeper);
    }
    assertEquals(zoo.getZookeepers(), defaultZookeepers);
  }

  public void toString_returnsName() {
    assertEquals(zoo.toString(), DEFAULT_ZOO_NAME);
  }
}
