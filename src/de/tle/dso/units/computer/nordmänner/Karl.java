package de.tle.dso.units.computer.nordmänner;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.SpecialAttack;
import de.tle.dso.units.computer.ComputerUnit;

public class Karl extends ComputerUnit {
  protected static String name = "Karl";
  protected static String shortName = "KAR";

  public Karl() {
    this.maxHitPoints = 80;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 40;
    this.maxDamage = 50;
    this.hitChancePercent = 90;
    this.initiative = Initiative.LOW;
    this.priority = 202;
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
