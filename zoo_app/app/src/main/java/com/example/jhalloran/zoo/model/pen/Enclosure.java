package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.util.List;

/**
 * Created by jhalloran on 1/9/18.
 */

public abstract class Enclosure {

  private Zookeeper zookeeperAssignedTo = null;

  public abstract PenType getType();

  public abstract int getLandArea();

  public abstract int getTemperature();

  public abstract List<Animal> getAnimals();

  public abstract void addAnimal(Animal animal)
      throws Exception; // TODO: Create custom exception AND/OR return boolean

  // TODO: boolean removeAnimal(Animal animal);

  public abstract boolean canLiveHere(Animal animal);

  public boolean isAssigned() {
    return (zookeeperAssignedTo != null);
  }

  public Zookeeper getAssignedToZookeeper() {
    return zookeeperAssignedTo;
  }

  public void assignToZookeeper(Zookeeper zookeeper) throws Exception{
    try {
      zookeeper.addPen(this);
      zookeeperAssignedTo = zookeeper;
    } catch (Exception e) {
      throw e;
    }
  }
}