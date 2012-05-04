package de.tle.dso.units.computer.nordmänner;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.SpecialAttack;
import de.tle.dso.units.computer.ComputerUnit;

public class Berserker extends ComputerUnit {
  protected static String name = "Berserker";
  protected static String shortName = "BR";

  public Berserker() {
    this.maxHitPoints = 90;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 20;
    this.maxDamage = 40;
    this.hitChancePercent = 70;
    this.initiative = Initiative.LOW;
    this.priority = 205;
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
