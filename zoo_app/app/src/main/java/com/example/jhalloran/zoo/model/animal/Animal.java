package com.example.jhalloran.zoo.model.animal;

import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by jhalloran on 1/8/18.
 */

// TODO Make this an interface
public abstract class Animal {

  public abstract String getName();

  public abstract Set<PenType> getPenTypes();

  public abstract int getWaterVolumeRequired();

  public abstract int getLandAreaRequired();

  public abstract int getAirVolumeRequired();

  public abstract Set<WaterType> getWaterTypes();
}
