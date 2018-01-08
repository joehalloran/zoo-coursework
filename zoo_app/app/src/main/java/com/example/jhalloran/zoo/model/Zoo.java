package com.example.jhalloran.zoo.model;

import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.animal.LandAnimal;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jhalloran on 1/8/18.
 */

public class Zoo {
  private String name;
  private List<Animal> animals = new ArrayList<>();

  public Zoo(String name) {
    this.name = name;
    setUpZoo();
  }

  public String getName() {
    return name;
  }

  public List<Animal> getAnimals() {
    return animals;
  }

  public void addAnimal(Animal animal) {
    animals.add(animal);
  }

  private void setUpZoo() {
    Set<PenType> defaultPens = new HashSet<>();
    defaultPens.add(PenType.DRY);
    animals.add(new LandAnimal("Rhino", 20, defaultPens));
    animals.add(new LandAnimal("Zebra", 40, defaultPens));
  }
}
