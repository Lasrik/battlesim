package de.tle.dso.units.player;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.SpecialDefense;
import de.tle.dso.units.Unit;

public class General extends PlayerUnit {

  protected static String name = "General";

  protected static String shortName = "G";

  public General() {
    this.maxHitPoints = 1;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 120;
    this.maxDamage = 120;
    this.hitChancePercent = 100;
    this.initiative = Initiative.MEDIUM;
    this.specialDefense = SpecialDefense.ALWAYS_LAST;
    this.priority = 10000;
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
