package com.example.jhalloran.zoo.model;

import com.example.jhalloran.zoo.model.pen.Enclosable;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jhalloran on 1/9/18.
 */

public class Zookeeper implements Serializable {
  private final String name;
  private Set<PenType> penTypesCanManage;
  private List<Enclosable> pens = new ArrayList<Enclosable>();

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

  public boolean addResponsibilty(PenType type) {
    return penTypesCanManage.add(type);
  }

  public boolean removeResponsibilty(PenType type) {
    return penTypesCanManage.remove(type);
  }

  public List<Enclosable> getPens() {
    return pens;
  }

  public Boolean addPen(Enclosable pen) {
    return pens.add(pen);
  }

  public Boolean removePen(Enclosable pen) {
    return pens.remove(pen);
  }

  @Override
  public String toString(){
    return name;
  }
}
