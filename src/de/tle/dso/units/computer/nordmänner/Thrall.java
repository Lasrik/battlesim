package de.tle.dso.units.computer.nordmänner;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.SpecialAttack;
import de.tle.dso.units.computer.ComputerUnit;

public class Thrall extends ComputerUnit {
  protected static String name = "Thrall";
  protected static String shortName = "TH";

  public Thrall() {
    this.maxHitPoints = 60;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 20;
    this.maxDamage = 25;
    this.hitChancePercent = 85;
    this.initiative = Initiative.LOW;
    this.priority = 203;
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
