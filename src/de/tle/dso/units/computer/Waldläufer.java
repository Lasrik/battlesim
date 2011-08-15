package de.tle.dso.units.computer;

import de.tle.dso.units.Unit;
import static de.tle.dso.units.Initiative.*;

public class Waldläufer extends ComputerUnit {

  protected static String name = "Waldläufer";
  protected static String shortName = "WL";

  public Waldläufer() {
    this.maxHitPoints = 10;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 30;
    this.maxDamage = 60;
    this.hitChancePercent = 60;
    this.initiative = MEDIUM;
    this.priority = 106;
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
