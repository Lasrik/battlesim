package de.tle.dso.resources;

public enum Resource {
  SETTLER(1),
  BEER(1),
  BRONCE_SWORD(1),
  BOW(2),
  HORSE(6),
  IRON_SWORD(5),
  STEEL_SWORD(8),
  LONG_BOW(4),
  TITAN_SWORD(80),
  CROSSBOW(50),
  CANON(100);
  final int weight;

  Resource(int weight) {
    this.weight = weight;
  }

  public int weight() {
    return weight;
  }

}
