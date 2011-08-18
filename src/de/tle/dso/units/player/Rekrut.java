package de.tle.dso.units.player;

import de.tle.dso.resources.ResourceCost;
import de.tle.dso.units.Initiative;
import static de.tle.dso.resources.Resource.*;

public class Rekrut extends PlayerUnit {

  protected static String name = "Rekrut";
  protected static String shortName = "R";

  public Rekrut() {
    this.maxHitPoints = 40;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 15;
    this.maxDamage = 30;
    this.hitChancePercent = 80;
    this.initiative = Initiative.MEDIUM;
    this.priority = 1;
    this.resourceCost = ResourceCost.build(SETTLER, 1, BEER, 5, BRONCE_SWORD, 10);
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
