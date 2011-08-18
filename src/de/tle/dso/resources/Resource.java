package de.tle.dso.resources;

public enum Resource {
  SETTLER(1),
  BEER(1),
  BRONCE_SWORD(1),
  BOW(2),
  HORSE(4),
  IRON_SWORD(5),
  STEEL_SWORD(8),
  LONG_BOW(4),
  TITAN_SWORD(15),
  CROSSBOW(10),
  CANON(20);
  final int weight;

  Resource(int weight) {
    this.weight = weight;
  }

  public int weight() {
    return weight;
  }

}
