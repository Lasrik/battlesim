package de.tle.dso.units.computer.nordmänner;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.SpecialAttack;
import de.tle.dso.units.computer.ComputerUnit;

public class Walküre extends ComputerUnit {
  protected static String name = "Walküre";
  protected static String shortName = "WK";

  public Walküre() {
    this.maxHitPoints = 10;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 20;
    this.maxDamage = 40;
    this.hitChancePercent = 60;
    this.initiative = Initiative.MEDIUM;
    this.priority = 204;
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
