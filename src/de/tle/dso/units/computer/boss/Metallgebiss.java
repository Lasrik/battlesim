package de.tle.dso.units.computer.boss;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.SpecialAttack;
import de.tle.dso.units.SpecialDefense;
import de.tle.dso.units.Unit;
import de.tle.dso.units.computer.ComputerUnit;

public class Metallgebiss extends ComputerUnit {

  protected static String name = "Metallgebiss";
  protected static String shortName = "MG";

  public Metallgebiss() {
    this.maxHitPoints = 11000;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 250;
    this.maxDamage = 500;
    this.hitChancePercent = 50;
    this.initiative = Initiative.LOW;
    this.specialDefense = SpecialDefense.ALYWAYS_FIRST;
    this.specialAttacks = SpecialAttack.SPLASH;
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
