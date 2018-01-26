package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;

/**
 * Created by jhalloran on 1/26/18.
 */
public class PettingPen extends DryPen implements Serializable {
  public PettingPen(String name, int length, int width, int temperature) {
    super(name, PenType.PETTING, length, width, temperature);
  }
}
