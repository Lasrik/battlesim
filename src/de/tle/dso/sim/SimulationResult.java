package de.tle.dso.sim;

import de.tle.dso.sim.battle.BattleResult;
import de.tle.dso.units.Army;
import de.tle.dso.units.Unit;
import java.util.HashMap;
import java.util.Map;

public class SimulationResult {

  private Map<Class<? extends Unit>, Integer> minPlayerLosses;
  private Map<Class<? extends Unit>, Integer> maxPlayerLosses;
  private Map<Class<? extends Unit>, Integer> minComputerLosses;
  private Map<Class<? extends Unit>, Integer> maxComputerLosses;
  private int numberOfSimulationRuns;
  private long simDuration;

  public SimulationResult() {
    this.minComputerLosses = new HashMap<Class<? extends Unit>, Integer>();
    this.maxComputerLosses = new HashMap<Class<? extends Unit>, Integer>();

    this.minPlayerLosses = new HashMap<Class<? extends Unit>, Integer>();
    this.maxPlayerLosses = new HashMap<Class<? extends Unit>, Integer>();

    this.numberOfSimulationRuns = 0;
  }

  public void addBattleResult(BattleResult battleResult) {
    updatePlayerLosses(battleResult);
    updateComputerLosses(battleResult);

    numberOfSimulationRuns++;
  }

  public Map<Class<? extends Unit>, Integer> getMaxComputerLosses() {
    return maxComputerLosses;
  }

  public Map<Class<? extends Unit>, Integer> getMaxPlayerLosses() {
    return maxPlayerLosses;
  }

  public Map<Class<? extends Unit>, Integer> getMinComputerLosses() {
    return minComputerLosses;
  }

  public Map<Class<? extends Unit>, Integer> getMinPlayerLosses() {
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

  private int getMinimum(Class<? extends Unit> unitClass, Map<Class<? extends Unit>, Integer> map1, Map<Class<? extends Unit>, Integer> map2) {
    int count1 = Integer.MAX_VALUE;
    int count2 = Integer.MAX_VALUE;

    if (map1.containsKey(unitClass)) {
      count1 = map1.get(unitClass);
    }

    if (map2.containsKey(unitClass)) {
      count2 = map2.get(unitClass);
    }

    return Math.min(count1, count2);
  }

  private int getMaximum(Class<? extends Unit> unitClass, Map<Class<? extends Unit>, Integer> map1, Map<Class<? extends Unit>, Integer> map2) {
    int count1 = Integer.MIN_VALUE;
    int count2 = Integer.MIN_VALUE;

    if (map1.containsKey(unitClass)) {
      count1 = map1.get(unitClass);
    }

    if (map2.containsKey(unitClass)) {
      count2 = map2.get(unitClass);
    }

    return Math.max(count1, count2);
  }

  private void updatePlayerLosses(BattleResult battleResult) {
    for (Class<? extends Unit> unitClass : battleResult.getPlayerLosses().keySet()) {
      int minCount = getMinimum(unitClass, battleResult.getPlayerLosses(), minPlayerLosses);
      minPlayerLosses.put(unitClass, minCount);

      int maxCount = getMaximum(unitClass, battleResult.getPlayerLosses(), maxPlayerLosses);
      maxPlayerLosses.put(unitClass, maxCount);
    }
  }

  private void updateComputerLosses(BattleResult battleResult) {
    for (Class<? extends Unit> unitClass : battleResult.getComputerLosses().keySet()) {
      int minCount = getMinimum(unitClass, battleResult.getComputerLosses(), minComputerLosses);
      minComputerLosses.put(unitClass, minCount);

      int maxCount = getMaximum(unitClass, battleResult.getComputerLosses(), maxComputerLosses);
      maxComputerLosses.put(unitClass, maxCount);
    }
  }
}