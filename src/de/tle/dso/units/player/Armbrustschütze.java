package de.tle.dso.units.player;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.Unit;

public class Armbrustschütze extends PlayerUnit {

  protected static String name = "Armbrustschütze";
  protected static String shortName = "A";

  public Armbrustschütze() {
    this.maxHitPoints = 10;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 45;
    this.maxDamage = 90;
    this.hitChancePercent = 80;
    this.initiative = Initiative.MEDIUM;
    this.priority = 8;
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
