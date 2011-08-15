package de.tle.dso.units.player;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.Unit;

public class Rekrut extends PlayerUnit {

  protected static String name = "Rekrut";
  protected static String shortName = "R";

  public Rekrut() {
    this.maxHitPoints = 40;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 15;
    this.maxDamage = 30;
    this.hitChancePercent = 80;
    this.initiative = Initiative.MEDIUM;
    this.priority = 1;
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
