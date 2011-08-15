package de.tle.dso.units.computer.boss;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.SpecialAttack;
import de.tle.dso.units.SpecialDefense;
import de.tle.dso.units.Unit;
import de.tle.dso.units.computer.ComputerUnit;

public class Waltraud extends ComputerUnit {

  protected static String name = "Die wilde Waltraud";
  protected static String shortName = "DWW";

  public Waltraud() {
    this.maxHitPoints = 60000;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 740;
    this.maxDamage = 800;
    this.hitChancePercent = 50;
    this.initiative = Initiative.HIGH;
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
