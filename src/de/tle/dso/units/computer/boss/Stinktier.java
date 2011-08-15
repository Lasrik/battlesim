package de.tle.dso.units.computer.boss;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.SpecialAttack;
import de.tle.dso.units.SpecialDefense;
import de.tle.dso.units.Unit;
import de.tle.dso.units.computer.ComputerUnit;

public class Stinktier extends ComputerUnit {

  protected static String name = "Stinktier";
  protected static String shortName = "ST";

  public Stinktier() {
    this.maxHitPoints = 5000;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 1;
    this.maxDamage = 100;
    this.hitChancePercent = 50;
    this.initiative = Initiative.LOW;
    this.specialAttacks = SpecialAttack.SPLASH;
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
