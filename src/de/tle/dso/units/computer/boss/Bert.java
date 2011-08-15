package de.tle.dso.units.computer.boss;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.SpecialAttack;
import de.tle.dso.units.SpecialDefense;
import de.tle.dso.units.Unit;
import de.tle.dso.units.computer.ComputerUnit;

public class Bert extends ComputerUnit {

  protected static String name = "Einäugiger Bert";
  protected static String shortName = "EB";

  public Bert() {
    this.maxHitPoints = 6000;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 300;
    this.maxDamage = 500;
    this.hitChancePercent = 50;
    this.initiative = Initiative.LOW;
    this.specialDefense = SpecialDefense.ALWAYS_LAST;
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
