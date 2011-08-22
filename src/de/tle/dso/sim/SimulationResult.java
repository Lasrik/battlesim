package de.tle.dso.sim;

import de.tle.dso.resources.ResourceCost;
import de.tle.dso.sim.battle.BattleResult;
import de.tle.dso.units.Army;
import de.tle.dso.units.Unit;
import java.util.HashMap;
import java.util.Map;

public class SimulationResult {

  protected Army minPlayerLosses;
  protected Army maxPlayerLosses;
  protected Army minComputerLosses;
  protected Army maxComputerLosses;
  protected ResourceCost maxResourceCosts = ResourceCost.buildEmpty();
  protected int numberOfSimulationRuns;
  protected long simDuration;
  protected boolean alwaysWin = true;

  public SimulationResult() {
    this.minComputerLosses = new Army();
    this.maxComputerLosses = new Army();

    this.minPlayerLosses = new Army();
    this.maxPlayerLosses = new Army();

    this.numberOfSimulationRuns = 0;
  }

  public void addBattleResult(BattleResult battleResult) {
    if (maxResourceCosts.lesserThan(battleResult.getResourceCosts())) {
      maxResourceCosts = battleResult.getResourceCosts();
    }

    Army currentPlayerLosses = battleResult.getPlayerLosses();
    Army currentComputerLosses = battleResult.getComputerLosses();

    if (currentPlayerLosses.size() > maxPlayerLosses.size()) {
      maxPlayerLosses = currentPlayerLosses;
    } else if (currentPlayerLosses.size() < minPlayerLosses.size()) {
      minPlayerLosses = currentPlayerLosses;
    }

    if (currentComputerLosses.size() > maxComputerLosses.size()) {
      maxComputerLosses = currentComputerLosses;
    } else if (currentComputerLosses.size() < minComputerLosses.size()) {
      minComputerLosses = currentComputerLosses;
    }

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
}