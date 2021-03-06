package de.tle.dso.units;

import de.tle.dso.resources.ResourceCost;
import java.util.Random;

public abstract class Unit {

  protected int maxHitPoints;
  protected int currentHitPoints;
  protected int minDamage;
  protected int maxDamage;
  protected int hitChancePercent;
  protected int priority;
  protected SpecialAttack specialAttacks = SpecialAttack.NONE;
  protected SpecialDefense specialDefense = SpecialDefense.NONE;
  protected ResourceCost resourceCost = ResourceCost.NONE;
  protected Initiative initiative = Initiative.MEDIUM;
  private static Random rand = new Random();

  public abstract String getName();

  public abstract String getShortName();

  public int doDamage() {
    int damageDone = getMinDamage();
    if ((getNextRand() + 1) <= getHitChancePercent()) {
      damageDone = getMaxDamage();
    }
    return damageDone;
  }

  public int getHitChancePercent() {
    return hitChancePercent;
  }

  public int getMaxHitPoints() {
    return maxHitPoints;
  }

  public int getCurrentHitPoints() {
    return currentHitPoints;
  }

  public Initiative getInitiative() {
    return initiative;
  }

  public int getMaxDamage() {
    return maxDamage;
  }

  public int getMinDamage() {
    return minDamage;
  }

  public ResourceCost getResourceCost() {
    return resourceCost;
  }

  public SpecialAttack getSpecialAttacks() {
    return specialAttacks;
  }

  public int getPriority() {
    return priority;
  }

  public int reduceHitpoints(int damageDone) {
    int actuallyReducedHitpoints = currentHitPoints;
    this.currentHitPoints -= damageDone;
    return actuallyReducedHitpoints;
  }

  public boolean isAlive() {
    return this.currentHitPoints > 0;
  }

  public boolean isDead() {
    return !this.isAlive();
  }

  public SpecialDefense getSpecialDefense() {
    return specialDefense;
  }

  protected int getNextRand() {
    return rand.nextInt(100);
  }
}