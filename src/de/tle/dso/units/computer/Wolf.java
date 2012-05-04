package de.tle.dso.units.computer;

import static de.tle.dso.units.Initiative.HIGH;

public class Wolf extends ComputerUnit {

  protected static String name = "Wolf";
  protected static String shortName = "W";

  public Wolf() {
    this.maxHitPoints = 10;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 2;
    this.maxDamage = 3;
    this.hitChancePercent = 80;
    this.initiative = HIGH;
    this.priority = 500;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getShortName() {
    return shortName;
  }
}
