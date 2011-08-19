package de.tle.dso.units.player;

import static de.tle.dso.resources.Resource.*;
import de.tle.dso.resources.ResourceCost;
import de.tle.dso.units.Initiative;

public class Kanonier extends PlayerUnit {

  protected static String name = "Kanonier";
  protected static String shortName = "K";

  public Kanonier() {
    this.maxHitPoints = 60;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 60;
    this.maxDamage = 120;
    this.hitChancePercent = 90;
    this.initiative = Initiative.LOW;
    this.priority = 9;
    this.resourceCost = ResourceCost.build(SETTLER, 1, BEER, 50, CANON, 10);
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
