package com.example.jhalloran.zoo.model.animal;

/**
 * Created by jhalloran on 1/8/18.
 */

import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;
import java.util.Set;

/**
 * An animal that lives on land only (i.e. cannot fly or swim)
 */
public class LandAnimal extends AbstractAnimal implements Serializable {

  public LandAnimal(String name, int landAreaRequired, Set<PenType> penTypes, boolean dangerous) {
    super(name, penTypes, dangerous, landAreaRequired);
  }
}