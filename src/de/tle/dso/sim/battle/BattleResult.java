package de.tle.dso.sim.battle;

import de.tle.dso.resources.Resource;
import de.tle.dso.resources.ResourceCost;
import de.tle.dso.units.Unit;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleResult {

  protected Map<Class<? extends Unit>, Integer> playerLosses;
  protected Map<Class<? extends Unit>, Integer> computerLosses;
  protected boolean battleWon = false;
  protected long battleDuration;
  protected ResourceCost resourceCosts;

  public BattleResult() {
    this.playerLosses = new HashMap<Class<? extends Unit>, Integer>();
    this.computerLosses = new HashMap<Class<? extends Unit>, Integer>();
    this.resourceCosts = new ResourceCost(new EnumMap<Resource, Integer>(Resource.class));
  }

  public boolean isBattleWon() {
    return battleWon;
  }

  public void setBattleWon(boolean battleWon) {
    this.battleWon = battleWon;
  }

  public Map<Class<? extends Unit>, Integer> getComputerLosses() {
    return computerLosses;
  }

  public Map<Class<? extends Unit>, Integer> getPlayerLosses() {
    return playerLosses;
  }

  public void addDeadPlayerUnit(Unit deadUnit) {
    incCount(deadUnit, playerLosses);
    resourceCosts.add(deadUnit.getResourceCost());
  }

  public void addDeadComputerUnit(Unit deadUnit) {
    incCount(deadUnit, computerLosses);
  }

  public void addDeadPlayerUnits(List<Unit> deadUnits) {
    for (Unit unit : deadUnits) {
      addDeadPlayerUnit(unit);
    }
  }

  public void addDeadComputerUnits(List<Unit> deadUnits) {
    for (Unit unit : deadUnits) {
      addDeadComputerUnit(unit);
    }
  }

  public long getBattleDuration() {
    return battleDuration;
  }

  public void setBattleDuration(long battleDuration) {
    this.battleDuration = battleDuration;
  }

  private void incCount(Unit deadUnit, Map<Class<? extends Unit>, Integer> map) {
    int currentCount = 1;
    if (map.containsKey(deadUnit.getClass())) {
      currentCount = map.get(deadUnit.getClass());
      currentCount++;
    }
    map.put(deadUnit.getClass(), currentCount);
  }
}