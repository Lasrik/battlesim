package de.tle.dso.units.player;

import de.tle.dso.units.Initiative;
import de.tle.dso.units.SpecialAttack;
import de.tle.dso.units.Unit;

public class Reiterei extends PlayerUnit {

  protected static String name = "Reiterei";
  protected static String shortName = "C";

  public Reiterei() {
    this.maxHitPoints = 5;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 5;
    this.maxDamage = 10;
    this.hitChancePercent = 80;
    this.initiative = Initiative.HIGH;
    this.priority = 3;
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
