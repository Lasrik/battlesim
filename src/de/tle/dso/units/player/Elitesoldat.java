package de.tle.dso.units.player;

import static de.tle.dso.resources.Resource.*;
import de.tle.dso.resources.ResourceCost;
import de.tle.dso.units.Initiative;

public class Elitesoldat extends PlayerUnit {

  protected static String name = "Elitesoldat";
  protected static String shortName = "E";

  public Elitesoldat() {
    this.maxHitPoints = 120;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 20;
    this.maxDamage = 40;
    this.hitChancePercent = 90;
    this.initiative = Initiative.MEDIUM;
    this.priority = 5;
    this.resourceCost = ResourceCost.build(SETTLER, 1, BEER, 50, TITAN_SWORD, 10);
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
