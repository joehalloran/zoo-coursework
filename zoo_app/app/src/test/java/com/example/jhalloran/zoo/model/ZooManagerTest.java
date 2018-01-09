package com.example.jhalloran.zoo.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jhalloran on 1/8/18.
 */

public class ZooManagerTest {
  private ZooManager zooManager = null;
  private Zoo zooOne = new Zoo("Zoo one");
  private Zoo zooTwo = new Zoo("Zoo two");

  @Before
  public void setUp() {
    zooManager = null;
  }

  @Test
  public void zooManager_createNewInstance() {
    zooManager = ZooManager.getInstance();
    assertTrue(zooManager != null);
  }

  @Test
  public void zooManager_isSingelton() {
    zooManager = ZooManager.getInstance();
    ZooManager zooManagerTwo = ZooManager.getInstance();
    assertEquals(zooManager, zooManagerTwo);
  }

  @Test
  public void addZoos() {
    zooManager = ZooManager.getInstance();

    zooManager.addZoo(zooOne);
    zooManager.addZoo(zooTwo);

    assertEquals(zooManager.getZoo(zooOne.getName()), zooOne);
    assertEquals(zooManager.getZoo(zooTwo.getName()), zooTwo);
  }

  @Test
  public void getZoos_returnsAll() {
    zooManager = ZooManager.getInstance();

    zooManager.addZoo(zooOne);
    zooManager.addZoo(zooTwo);

    List<Zoo> allZoos = zooManager.getZoos();

    assertTrue(allZoos.contains(zooOne));
    assertTrue(allZoos.contains(zooTwo));
  }

}
