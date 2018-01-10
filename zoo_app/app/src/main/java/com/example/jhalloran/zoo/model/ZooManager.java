package com.example.jhalloran.zoo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jhalloran on 1/8/18.
 */

public class ZooManager {

  private static ZooManager instance = null;
  private Map<String, Zoo> namesToZoos;
  private Object ArrayList;
  private Zoo activeZoo = null;

  private ZooManager() {
    namesToZoos = new HashMap<>();
  }

  public static ZooManager getInstance() {
    if (instance == null) {
      instance  = new ZooManager();
    }
    return instance;
  }

  public void addZoo(Zoo zoo){
    // TODO: Duplicate names
    namesToZoos.put(zoo.getName(), zoo);
  }

  public Zoo getZoo(String name) {
    // TODO: Null return values
    return namesToZoos.get(name);
  }

  public List<Zoo> getZoos() {
    return new ArrayList<Zoo>(namesToZoos.values());
  }

  public void setActiveZoo(Zoo zoo) {
    activeZoo = zoo;
  }

  public Zoo getActiveZoo() {
    // TODO: zoo should be in Zoo List.
    return activeZoo;
  }

}
