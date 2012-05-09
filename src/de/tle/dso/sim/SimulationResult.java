package de.tle.dso.sim;

import de.tle.dso.resources.ResourceCost;
import de.tle.dso.sim.battle.BattleResult;
import de.tle.dso.units.Army;
import de.tle.dso.units.Unit;
import de.tle.dso.units.util.UnitPatternHelper;
import java.util.Map;

public class SimulationResult {

  protected Army minPlayerLosses = null;
  protected Army maxPlayerLosses = null;
  protected Army minComputerLosses = null;
  protected Army maxComputerLosses = null;
  protected ResourceCost maxResourceCosts = ResourceCost.buildEmpty();
  protected Army lossesThatCausedMaxResourceCosts = new Army();
  protected int numberOfSimulationRuns = 0;
  protected long simDuration = 0;
  protected boolean alwaysWin = true;
  protected ResourceCost totalResources = ResourceCost.buildEmpty();

  public SimulationResult() {
  }

  public void addBattleResult(BattleResult battleResult) {
    trackResourceCosts(battleResult);
    trackPlayerLosses(battleResult);
    trackComputerLosses(battleResult);

    if (battleResult.isBattleWon() == false) {
      alwaysWin = false;
    }

    numberOfSimulationRuns++;
  }

  public Army getMaxComputerLosses() {
    return maxComputerLosses;
  }

  public Army getMaxPlayerLosses() {
    return maxPlayerLosses;
  }

  public Army getMinComputerLosses() {
    return minComputerLosses;
  }

  public Army getMinPlayerLosses() {
    return minPlayerLosses;
  }

  public int getNumberOfSimulationRuns() {
    return numberOfSimulationRuns;
  }

  public long getSimDuration() {
    return simDuration;
  }

  public void setSimDuration(long simDuration) {
    this.simDuration = simDuration;
  }

  public double getBattlesPerSecond() {
    return ((double) numberOfSimulationRuns / ((double) simDuration / 1000d));
  }

  public ResourceCost getMaxResourceCosts() {
    return maxResourceCosts;
  }

  public int getMaxResourceCostWeightPoints() {
    return maxResourceCosts.totalWeightPoints();
  }

  public int getAvgResourceCostWeightPoints() {
    return totalResources.totalWeightPoints() / numberOfSimulationRuns;
  }

  public Army getArmyForMaxResourceCost() {
    return lossesThatCausedMaxResourceCosts;
  }

  public boolean isAlwaysWin() {
    return alwaysWin;
  }

  private void trackResourceCosts(BattleResult battleResult) {
    if (maxResourceCosts.lesserThan(battleResult.getResourceCosts())) {
      maxResourceCosts = battleResult.getResourceCosts();
      lossesThatCausedMaxResourceCosts = battleResult.getPlayerLosses();
    }

    totalResources.add(battleResult.getResourceCosts());
  }

  private void trackComputerLosses(BattleResult battleResult) {
    Army currentComputerLosses = battleResult.getComputerLosses();
    if (notSet(maxComputerLosses) || currentComputerLosses.size() > maxComputerLosses.size()) {
      maxComputerLosses = currentComputerLosses;
    }

    if (notSet(minComputerLosses) || currentComputerLosses.size() < minComputerLosses.size()) {
      minComputerLosses = currentComputerLosses;
    }
  }

  private void trackPlayerLosses(BattleResult battleResult) {
    Army currentPlayerLosses = battleResult.getPlayerLosses();

    if (notSet(maxPlayerLosses) || currentPlayerLosses.size() > maxPlayerLosses.size()) {
      maxPlayerLosses = currentPlayerLosses;
    }

    if (notSet(minPlayerLosses) || currentPlayerLosses.size() < minPlayerLosses.size()) {
      minPlayerLosses = currentPlayerLosses;
    }
  }

  private boolean notSet(Object o) {
    return o == null;
  }

  @Override
  public String toString() {
    return maxPlayerLosses.toString() + ", " + getAvgResourceCostWeightPoints() + "avg";
  }
}