package de.tle.dso.units.player;

import static de.tle.dso.resources.Resource.*;
import de.tle.dso.resources.ResourceCost;
import de.tle.dso.units.Initiative;
import de.tle.dso.units.SpecialAttack;

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
    this.resourceCost = ResourceCost.build(SETTLER, 1, BEER, 30, HORSE, 40);
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
