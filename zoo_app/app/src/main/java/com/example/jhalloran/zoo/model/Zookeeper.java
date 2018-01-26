package com.example.jhalloran.zoo.model;

import com.example.jhalloran.zoo.model.pen.Enclosable;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jhalloran on 1/9/18.
 */

public class Zookeeper implements Serializable {
  private final String name;
  private Set<PenType> penTypesCanManage;
  private Set<Enclosable> pens = new HashSet<>();

  public Zookeeper(String name, Set<PenType> penTypesCanManage) {
    this.name = name;
    this.penTypesCanManage = EnumSet.copyOf(penTypesCanManage);
  }

  public String getName() {
    return name;
  }

  public Set<PenType> getPenTypesCanManage() {
    return penTypesCanManage;
  }

  public Set<Enclosable> getPens() {
    return pens;
  }

  public boolean addPen(Enclosable pen) {
    return  canManagerPen(pen) && pens.add(pen);
  }

  public boolean canManagerPen(Enclosable pen) {
    return penTypesCanManage.contains(pen.getType());
  }

  public boolean removePen(Enclosable pen) {
    return pens.remove(pen);
  }

  @Override
  public String toString(){
    return name;
  }
}
