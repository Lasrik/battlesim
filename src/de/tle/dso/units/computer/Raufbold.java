package de.tle.dso.units.computer;

import de.tle.dso.units.Unit;
import static de.tle.dso.units.Initiative.*;

public class Raufbold extends ComputerUnit {

  protected static String name = "Raufbold";
  protected static String shortName = "RB";

  public Raufbold() {
    this.maxHitPoints = 90;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 20;
    this.maxDamage = 40;
    this.hitChancePercent = 60;
    this.initiative = MEDIUM;
    this.priority = 104;
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
