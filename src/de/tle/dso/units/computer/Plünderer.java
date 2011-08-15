package de.tle.dso.units.computer;

import de.tle.dso.units.Unit;
import static de.tle.dso.units.Initiative.*;

public class Plünderer extends ComputerUnit {

  protected static String name = "Plünderer";
  protected static String shortName = "PL";

  public Plünderer() {
    this.maxHitPoints = 40;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 15;
    this.maxDamage = 30;
    this.hitChancePercent = 60;
    this.initiative = MEDIUM;
    this.priority = 101;
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
