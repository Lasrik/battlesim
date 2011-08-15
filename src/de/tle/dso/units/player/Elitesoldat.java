package de.tle.dso.units.player;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.Unit;

public class Elitesoldat extends PlayerUnit {

  protected static String name = "Elitesoldat";
  protected static String shortName = "E";

  public Elitesoldat() {
    this.maxHitPoints = 120;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 20;
    this.maxDamage = 40;
    this.hitChancePercent = 90;
    this.initiative = Initiative.MEDIUM;
    this.priority = 5;
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
