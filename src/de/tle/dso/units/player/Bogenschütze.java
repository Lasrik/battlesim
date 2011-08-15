package de.tle.dso.units.player;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.Unit;

public class Bogenschütze extends PlayerUnit {

  protected static String name = "Bogenschütze";
  protected static String shortName = "B";

  public Bogenschütze() {
    this.maxHitPoints = 10;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 20;
    this.maxDamage = 40;
    this.hitChancePercent = 80;
    this.initiative = Initiative.MEDIUM;
    this.priority = 6;
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
