package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.List;

/**
 * Created by jhalloran on 1/9/18.
 */

public abstract class Enclosure implements Serializable {
  private final String name;
  private final int temperature;
  private Zookeeper zookeeperAssignedTo = null;

  public Enclosure(String name, int temperature) {
    this.name = name;
    this.temperature = temperature;
  }

  public String getName() {
    return name;
  }

  public int getTemperature() {
    return temperature;
  }

  public abstract PenType getType();

  public abstract int getLandArea();

  public abstract List<Animal> getAnimals();

  public abstract void addAnimal(Animal animal)
      throws Exception; // TODO: Create custom exception AND/OR return boolean

  public abstract void removeAnimal(Animal animal); // TODO: Create custom exception AND/OR return boolean


  public abstract boolean canLiveHere(Animal animal);

  public boolean isAssigned() {
    return (zookeeperAssignedTo != null);
  }

  public Zookeeper getAssignedToZookeeper() {
    return zookeeperAssignedTo;
  }

  public void assignToZookeeper(Zookeeper zookeeper) throws Exception{
    if (zookeeper.canManagerPen(this)) {
      zookeeper.addPen(this);
      if (isAssigned()) {
        zookeeperAssignedTo.removePen(this);
      }
      zookeeperAssignedTo = zookeeper;
    }
  }

  @Override
  public String toString(){
    return name;
  }
}