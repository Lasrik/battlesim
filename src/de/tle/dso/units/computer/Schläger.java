package de.tle.dso.units.computer;

import de.tle.dso.units.Unit;
import static de.tle.dso.units.Initiative.*;

public class Schläger extends ComputerUnit {

  protected static String name = "Schläger";
  protected static String shortName = "SL";

  public Schläger() {
    this.maxHitPoints = 60;
    this.currentHitPoints = maxHitPoints;
    this.minDamage = 20;
    this.maxDamage = 40;
    this.hitChancePercent = 60;
    this.initiative = MEDIUM;
    this.priority = 102;
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
