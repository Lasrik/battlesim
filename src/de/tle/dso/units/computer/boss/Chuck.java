package de.tle.dso.units.computer.boss;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.SpecialAttack;
import de.tle.dso.units.SpecialDefense;
import de.tle.dso.units.Unit;
import de.tle.dso.units.computer.ComputerUnit;

public class Chuck extends ComputerUnit {

  protected static String name = "Chuck";
  protected static String shortName = "CK";

  public Chuck() {
    this.maxHitPoints = 9000;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 2000;
    this.maxDamage = 2500;
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
