package de.tle.dso.units.player;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.Unit;

public class Miliz extends PlayerUnit {

  protected static String name = "Miliz";
  protected static String shortName = "M";

  public Miliz() {
    this.maxHitPoints = 60;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 20;
    this.maxDamage = 40;
    this.hitChancePercent = 80;
    this.initiative = Initiative.MEDIUM;
    this.priority = 2;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getShortName() {
    return this.shortName;
  }
}
