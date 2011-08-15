package de.tle.dso.units.player;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.Unit;

public class Kanonier extends PlayerUnit {

  protected static String name = "Kanonier";
  protected static String shortName = "K";

  public Kanonier() {
    this.maxHitPoints = 60;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 60;
    this.maxDamage = 120;
    this.hitChancePercent = 90;
    this.initiative = Initiative.LOW;
    this.priority = 9;
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
