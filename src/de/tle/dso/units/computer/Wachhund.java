package de.tle.dso.units.computer;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.SpecialAttack;
import de.tle.dso.units.Unit;

public class Wachhund extends ComputerUnit {

  protected static String name = "Wachhund";
  protected static String shortName = "WH";

  public Wachhund() {
    this.maxHitPoints = 5;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 5;
    this.maxDamage = 10;
    this.hitChancePercent = 60;
    this.initiative = Initiative.HIGH;
    this.priority = 103;
    this.specialAttacks = SpecialAttack.ATTACK_WEAKEST;
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
