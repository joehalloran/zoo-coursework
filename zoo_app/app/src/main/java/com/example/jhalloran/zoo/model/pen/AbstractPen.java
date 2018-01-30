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