package com.example.jhalloran.zoo.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jhalloran on 1/8/18.
 */

public class ZooManager {

  private static ZooManager instance = null;
  private Map<String, Zoo> namesToZoos;

  private ZooManager() {
    namesToZoos = new HashMap<>();
  }

  public static ZooManager getInstance() {
    if (instance == null) {
      instance  =new ZooManager();
    }
    return instance;
  }

  public void addZoo(String name){
    // TODO: Duplicate names
    namesToZoos.put(name, new Zoo(name));
  }

  public Zoo getZoo(String name) {
    // TODO: Null return values
    return namesToZoos.get(name);
  }


}
