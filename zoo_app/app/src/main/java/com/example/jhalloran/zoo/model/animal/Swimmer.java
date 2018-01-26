package com.example.jhalloran.zoo.model.animal;

import com.example.jhalloran.zoo.model.shared.WaterType;
import java.util.Set;

/**
 * Created by jhalloran on 1/26/18.
 */

public interface Swimmer {
  Set<WaterType> getWaterTypes();

  int getWaterVolumeRequired();

}
