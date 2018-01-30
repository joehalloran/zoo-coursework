package com.example.jhalloran.zoo.model;

import com.example.jhalloran.zoo.model.pen.Enclosable;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * A zookeeper, who can manage pens at Zoo.
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

  /**
   * @return a set of {@link PenType} that this Zookeeper can manage.
   */
  public Set<PenType> getPenTypesCanManage() {
    return penTypesCanManage;
  }

  /**
   * @return a Set of {@link Enclosable} that this zookeeeper currently manages
   */
  public Set<Enclosable> getPens() {
    return pens;
  }

  /**
   * Attempts to add another pen for the Zookeeper to manage
   *
   * @param pen {@link Enclosable} to add
   * @return {@code true} if pen successfully added, {@code false} otherwise
   */
  public boolean addPen(Enclosable pen) {
    return canManagerPen(pen) && pens.add(pen);
  }

  /**
   * Establishes if the Zookeeper is capable of managing a specific pen
   *
   * @param pen {@link Enclosable} to check
   * @return {@code true} if the zookeeper can manage this pen, {@code false} if not.
   */
  public boolean canManagerPen(Enclosable pen) {
    return penTypesCanManage.contains(pen.getType());
  }

  /**
   * Removes a pen from the collection of pens this zookeeper manages
   *
   * @param pen {@link Enclosable} to remove
   * @return {@code true} if pen removed successfully, {@code false} otherwise.
   */
  public boolean removePen(Enclosable pen) {
    return pens.remove(pen);
  }

  @Override
  public String toString() {
    return name;
  }
}
