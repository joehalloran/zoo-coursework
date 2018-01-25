package com.example.jhalloran.zoo.allocator;

import android.util.Log;
import com.example.jhalloran.zoo.model.Zoo;
import com.example.jhalloran.zoo.model.Zookeeper;
import com.example.jhalloran.zoo.model.animal.Animal;
import com.example.jhalloran.zoo.model.pen.Enclosure;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by jhalloran on 1/25/18.
 */
public class AutoAllocatorHelper {
  private static final String TAG = "AutoAllocatorHelper";
  private final Zoo zoo;

  public AutoAllocatorHelper() {
    zoo = Zoo.getInstance();
  }

  public String autoAllocateAnimalsAndGetReport() {
    StringBuilder stringBuilder = new StringBuilder();
    List<Animal> animals = zoo.getAnimals();
    List<Enclosure> pens = zoo.getPens();
    for (Animal animal : animals) {
      if (!animal.isAssigned()) {
        for (Enclosure pen : pens) {
          if (pen.canLiveHere(animal)) {
            try {
              animal.assignToPen(pen);
              stringBuilder.append(String.format("%s assigned to %s \n", animal.getName(), pen.getName()));
              Log.i(TAG ,String.format("Allocating %s into %s", animal.getName(), pen.getName()));
              break; // TODO does this break both for loops??
            } catch (Exception e){
              Log.e(TAG, String.format("Unable to assign %s animal to %s", animal, pen));
            }
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

  public String autoAllocatePensAndGetReport() {
    StringBuilder stringBuilder = new StringBuilder();
    List<Enclosure> pens = zoo.getPens();
    List<Zookeeper> zookeepers = zoo.getZookeepers();
    for (Enclosure pen : pens) {
      if (!pen.isAssigned()) {
        for (Zookeeper zookeeper : zookeepers) {
          if (zookeeper.canManagerPen(pen)) {
            try {
              pen.assignToZookeeper(zookeeper);
              stringBuilder.append(String.format("%s assigned to %s\n", pen.getName(), zookeeper.getName()));
              Log.i(TAG ,String.format("Allocating %s into %s", pen.getName(), zookeeper.getName()));
              break; // TODO does this break both for loops??
            } catch (Exception e) {
              Log.e(TAG, String.format("Unable to assign %s pen to %s", pen, zookeeper));
            }
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
