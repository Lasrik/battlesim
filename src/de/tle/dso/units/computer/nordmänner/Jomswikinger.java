package de.tle.dso.units.computer.nordmänner;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.SpecialAttack;
import de.tle.dso.units.computer.ComputerUnit;

public class Jomswikinger extends ComputerUnit {
  protected static String name = "Jomswikinger";
  protected static String shortName = "JW";

  public Jomswikinger() {
    this.maxHitPoints = 140;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 60;
    this.maxDamage = 80;
    this.hitChancePercent = 95;
    this.initiative = Initiative.LOW;
    this.priority = 200;
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
