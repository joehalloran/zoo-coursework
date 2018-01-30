package com.example.jhalloran.zoo.model.pen;

import com.example.jhalloran.zoo.model.shared.PenType;
import java.io.Serializable;

/**
 * A pen that can house petting animals
 */
public class PettingPen extends DryPen implements Serializable {

  public PettingPen(String name, int length, int width, int temperature) {
    super(name, PenType.PETTING, length, width, temperature);
  }
}
