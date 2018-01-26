package com.example.jhalloran.zoo.model.animal;

/**
 * Created by jhalloran on 1/8/18.
 */

import com.example.jhalloran.zoo.model.shared.PenType;
import com.example.jhalloran.zoo.model.shared.WaterType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/** */
public class LandAnimal extends AbstractAnimal implements Serializable {
  public LandAnimal(String name, int landAreaRequired, Set<PenType> penTypes, boolean dangerous) {
    super(name, penTypes, dangerous, landAreaRequired);
  }
}