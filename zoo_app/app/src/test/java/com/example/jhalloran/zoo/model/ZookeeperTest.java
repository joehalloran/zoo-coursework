package com.example.jhalloran.zoo.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import com.example.jhalloran.zoo.model.pen.DryPen;
import com.example.jhalloran.zoo.model.shared.PenType;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jhalloran on 1/9/18.
 */

public final class ZookeeperTest {
  private static final PenType DEFAULT_TYPE = PenType.DRY;
  private static final String DEFAULT_NAME = "Hardip";

  private Zookeeper zookeeper = createDefaultZookeeper();

  @Before
  public void setUp() {
    zookeeper = createDefaultZookeeper();
  }

  @Test
  public void createZooKeeper_withNameAndPen() {
    assertEquals(zookeeper.getName(), DEFAULT_NAME);
    assertEquals(zookeeper.getPenTypesCanManage(), EnumSet.of(DEFAULT_TYPE));
  }

  @Test
  public void zookeeper_addResponsibility() {
    zookeeper.addResponsibilty(PenType.AVIARY);
    assertEquals(zookeeper.getPenTypesCanManage(), EnumSet.of(DEFAULT_TYPE, PenType.AVIARY));
  }

  @Test
  public void zookeeper_removeResponsibility() {
    zookeeper.addResponsibilty(PenType.AVIARY);
    zookeeper.removeResponsibilty(DEFAULT_TYPE);
    assertEquals(zookeeper.getPenTypesCanManage(), EnumSet.of(PenType.AVIARY));
  }

  @Test
  public void zookeeper_removeResponsibilityNotPresent_returnsFalse() {
    boolean successful = zookeeper.removeResponsibilty(PenType.AVIARY);
    assertEquals(successful, false);
  }

  @Test
  public void zookeeper_addPen() {
    DryPen pen = createDefaultDryPen();
    zookeeper.addPen(pen);
    assertEquals(zookeeper.getPens(), Collections.singletonList(pen));
  }

  @Test
  public void zookeeper_removePen() {
    DryPen pen = createDefaultDryPen();
    zookeeper.addPen(pen);
    zookeeper.removePen(pen);
    assertTrue(zookeeper.getPens().isEmpty());
  }

  @Test
  public void zookeeper_removePenNotPresent_returnsFalse() {
    Boolean successful = zookeeper.removePen(createDefaultDryPen());
    assertFalse(successful);
  }

  @Test
  public void toString_returnType(){
    assertEquals(zookeeper.toString(), DEFAULT_NAME);
  }

  private Zookeeper createDefaultZookeeper() {
    return new Zookeeper(DEFAULT_NAME, EnumSet.of(DEFAULT_TYPE));
  }

  private DryPen createDefaultDryPen() {
    return new DryPen(10, 10, 21);
  }
}
