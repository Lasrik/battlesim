package de.tle.dso.units.player;

import static de.tle.dso.resources.Resource.*;
import de.tle.dso.resources.ResourceCost;
import de.tle.dso.units.Initiative;

public class Langbogenschütze extends PlayerUnit {

  protected static String name = "Langbogenschütze";
  protected static String shortName = "LB";

  public Langbogenschütze() {
    this.maxHitPoints = 10;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 30;
    this.maxDamage = 60;
    this.hitChancePercent = 80;
    this.initiative = Initiative.MEDIUM;
    this.priority = 7;
    this.resourceCost = ResourceCost.build(SETTLER, 1, BEER, 15, LONG_BOW, 10);
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
