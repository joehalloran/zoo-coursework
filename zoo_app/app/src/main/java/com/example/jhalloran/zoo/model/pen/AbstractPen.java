package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by jhalloran on 1/26/18.
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

  public String getName() {
    return name;
  }

  public int getTemperature() {
    return temperature;
  }

  public int getLandArea() {
    return landArea;
  }

  public PenType getType() {
    return type;
  }

  public abstract Set<Animal> getAnimals();

  public abstract boolean addAnimal(Animal animal);

  public abstract boolean removeAnimal(Animal animal);

  public abstract boolean canLiveHere(Animal animal);

  public boolean isAssigned() {
    return (zookeeperAssignedTo != null);
  }

  public Zookeeper getAssignedToZookeeper() {
    return zookeeperAssignedTo;
  }

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
  public String toString(){
    return name;
  }
}