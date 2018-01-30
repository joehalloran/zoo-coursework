package com.example.jhalloran.zoo.allocator;

import android.util.Log;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.pen.Enclosable;
import java.util.List;

/**
 * Auto allocates animal to pens and pens to zookeepers
 */
public class AutoAllocatorHelper {

  private static final String TAG = "AutoAllocatorHelper";
  private final Zoo zoo;

  public AutoAllocatorHelper() {
    zoo = Zoo.getInstance();
  }

  /**
   * Auto allocates animals to pens and generates a report for display to user.
   *
   * @return allocation report, includes newline escape chars.
   */
  public String autoAllocateAnimalsAndGetReport() {
    StringBuilder stringBuilder = new StringBuilder();
    List<Animal> animals = zoo.getAnimals();
    List<Enclosable> pens = zoo.getPens();
    for (Animal animal : animals) {
      if (!animal.isAssigned()) {
        for (Enclosable pen : pens) {
          if (pen.canLiveHere(animal)) {
            animal.assignToPen(pen);
            stringBuilder
                .append(String.format("%s assigned to %s \n", animal.getName(), pen.getName()));
            Log.i(TAG, String.format("Allocating %s into %s", animal.getName(), pen.getName()));
            break;
          }
        }
        if (!animal.isAssigned()) {
          // Only record failure if no suitable pen was found (i.e. still unassigned)
          Log.i(TAG, String.format("Unable to assign %s", animal.getName()));
          stringBuilder.append(String.format("No suitable pens for %s\n", animal.getName()));
        }
      }
    }
    return stringBuilder.toString();
  }

  /**
   *
   * @return
   */
  public String autoAllocatePensAndGetReport() {
    StringBuilder stringBuilder = new StringBuilder();
    List<Enclosable> pens = zoo.getPens();
    List<Zookeeper> zookeepers = zoo.getZookeepers();
    for (Enclosable pen : pens) {
      if (!pen.isAssigned()) {
        for (Zookeeper zookeeper : zookeepers) {
          if (zookeeper.canManagerPen(pen)) {
            pen.assignToZookeeper(zookeeper);
            stringBuilder
                .append(String.format("%s assigned to %s\n", pen.getName(), zookeeper.getName()));
            Log.i(TAG, String.format("Allocating %s into %s", pen.getName(), zookeeper.getName()));
            break;
          }
        }
        if (!pen.isAssigned()) {
          // Only record failure if no suitable zookeeper was found (i.e. still unassigned)
          Log.i(TAG, String.format("Unable to assign %s", pen.getName()));
          stringBuilder.append(String.format("No suitable zookeepers for %s \n", pen.getName()));
        }
      }
    }
    return stringBuilder.toString();
  }
}
