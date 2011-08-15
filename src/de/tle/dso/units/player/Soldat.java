package de.tle.dso.units.player;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.Unit;

public class Soldat extends PlayerUnit {

  protected static String name = "Soldat";
  protected static String shortName = "S";

  public Soldat() {
    this.maxHitPoints = 90;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 20;
    this.maxDamage = 40;
    this.hitChancePercent = 85;
    this.initiative = Initiative.MEDIUM;
    this.priority = 4;
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
