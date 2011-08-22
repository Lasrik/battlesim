package de.tle.dso.sim;

import de.tle.dso.units.Unit;
import de.tle.dso.sim.battle.BattleResult;
import de.tle.dso.units.player.Bogenschütze;
import de.tle.dso.units.player.Reiterei;
import de.tle.dso.units.player.Rekrut;
import de.tle.dso.units.util.UnitPatternHelper;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SimulationResultTest {

  SimulationResult simResult = null;

  @Before
  public void setUp() {
    simResult = new SimulationResult();
  }

  @Test
  public void testAddBattleResult() {
    BattleResult battleResult = battleResultFromPattern("2 R");

    simResult.addBattleResult(battleResult);

    assertEquals(1, simResult.getNumberOfSimulationRuns());

    assertEquals(2, (int) simResult.getMinPlayerLosses().getNumberOf("R"));
    assertEquals(2, (int) simResult.getMaxPlayerLosses().getNumberOf("R"));
  }

  @Test
  public void testBattleResult2() {
    BattleResult battleResult = battleResultFromPattern("2 R, 1 B, 1C");
    simResult.addBattleResult(battleResult);

    assertEquals(1, simResult.getNumberOfSimulationRuns());

    assertEquals(2, (int) simResult.getMinPlayerLosses().getNumberOf("R"));
    assertEquals(1, (int) simResult.getMinPlayerLosses().getNumberOf("B"));
    assertEquals(1, (int) simResult.getMinPlayerLosses().getNumberOf("C"));
  }

  @Test
  public void testAddBattleResult2() {
    BattleResult battleResult = battleResultFromPattern("2 R, 1 B, 1C");
    BattleResult battleResult2 = battleResultFromPattern("2 R, 1 B, 1C");
    simResult.addBattleResult(battleResult);
    simResult.addBattleResult(battleResult2);

    assertEquals(2, simResult.getNumberOfSimulationRuns());

    assertEquals(2, (int) simResult.getMinPlayerLosses().getNumberOf("R"));
    assertEquals(1, (int) simResult.getMinPlayerLosses().getNumberOf("B"));
    assertEquals(1, (int) simResult.getMinPlayerLosses().getNumberOf("C"));

    assertEquals(2, (int) simResult.getMaxPlayerLosses().getNumberOf("R"));
    assertEquals(1, (int) simResult.getMaxPlayerLosses().getNumberOf("B"));
    assertEquals(1, (int) simResult.getMaxPlayerLosses().getNumberOf("C"));
  }

  @Test
  public void testAddBattleResult3() {
    BattleResult avgResult = battleResultFromPattern("7 R, 2 B, 1C");
    BattleResult worstResult = battleResultFromPattern("10 R, 1 B, 1C");
    BattleResult bestResult = battleResultFromPattern("2 R, 5 B, 1C");
    simResult.addBattleResult(avgResult);
    simResult.addBattleResult(worstResult);
    simResult.addBattleResult(bestResult);

    assertEquals(2, (int) simResult.getMinPlayerLosses().getNumberOf("R"));
    assertEquals(10, (int) simResult.getMaxPlayerLosses().getNumberOf("R"));

    assertEquals(5, (int) simResult.getMinPlayerLosses().getNumberOf("B"));
    assertEquals(1, (int) simResult.getMaxPlayerLosses().getNumberOf("B"));

    assertEquals(1, (int) simResult.getMinPlayerLosses().getNumberOf("C"));
    assertEquals(1, (int) simResult.getMaxPlayerLosses().getNumberOf("C"));

    assertEquals(3, simResult.getNumberOfSimulationRuns());
  }

  private BattleResult battleResultFromPattern(String pattern) {
    BattleResult battleResult = new BattleResult();
    battleResult.addDeadPlayerUnits(listFromPattern(pattern));
    return battleResult;
  }

  private List<Unit> listFromPattern(String pattern) {
    List<Unit> deadUnits = UnitPatternHelper.createArmyFromPattern(pattern).getUnits();
    return deadUnits;
  }
}
