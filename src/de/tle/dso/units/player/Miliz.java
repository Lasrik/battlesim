package de.tle.dso.units.player;

import static de.tle.dso.resources.Resource.*;
import de.tle.dso.resources.ResourceCost;
import de.tle.dso.units.Initiative;

public class Miliz extends PlayerUnit {

  protected static String name = "Miliz";
  protected static String shortName = "M";

  public Miliz() {
    this.maxHitPoints = 60;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 20;
    this.maxDamage = 40;
    this.hitChancePercent = 80;
    this.initiative = Initiative.MEDIUM;
    this.priority = 2;
    this.resourceCost = ResourceCost.build(SETTLER, 1, BEER, 10, IRON_SWORD, 10);
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getShortName() {
    return this.shortName;
  }
}
