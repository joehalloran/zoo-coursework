package com.example.jhalloran.zoo.model;

import com.example.jhalloran.zoo.model.pen.Enclosure;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jhalloran on 1/9/18.
 */

public class Zookeeper implements Serializable {
  private final String name;
  private Set<PenType> penTypesCanManage;
  private Set<Enclosure> pens = new HashSet<>();

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

  public Set<Enclosure> getPens() {
    return pens;
  }

  public boolean addPen(Enclosure pen) {
    return  canManagerPen(pen) && pens.add(pen);
  }

  public boolean canManagerPen(Enclosure pen) {
    return penTypesCanManage.contains(pen.getType());
  }

  public boolean removePen(Enclosure pen) {
    return pens.remove(pen);
  }

  @Override
  public String toString(){
    return name;
  }
}
