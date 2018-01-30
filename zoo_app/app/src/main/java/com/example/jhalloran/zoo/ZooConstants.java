package com.example.jhalloran.zoo;

/**
 * Holds string values to be used throughout Zoo app. E.g. in Android Intents.
 */
public enum ZooConstants {
  ITEM_ID("com.example.jhalloran.zoo.item.id"),
  ARG_PAGE_NUMBER("com.example.jhalloran.zoo.zoocontentfragment.pagenum");

  private final String value;

  ZooConstants(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
