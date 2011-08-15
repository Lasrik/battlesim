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

    assertTrue(simResult.getMinPlayerLosses().containsKey(Rekrut.class));
    assertTrue(simResult.getMaxPlayerLosses().containsKey(Rekrut.class));

    assertEquals(2, (int) simResult.getMinPlayerLosses().get(Rekrut.class));
    assertEquals(2, (int) simResult.getMaxPlayerLosses().get(Rekrut.class));
  }

  @Test
  public void testBattleResult2() {
    BattleResult battleResult = battleResultFromPattern("2 R, 1 B, 1C");
    simResult.addBattleResult(battleResult);

    assertEquals(1, simResult.getNumberOfSimulationRuns());

    assertEquals(2, (int) simResult.getMinPlayerLosses().get(Rekrut.class));
    assertEquals(1, (int) simResult.getMinPlayerLosses().get(Bogenschütze.class));
    assertEquals(1, (int) simResult.getMinPlayerLosses().get(Reiterei.class));
  }

  @Test
  public void testAddBattleResult2() {
    BattleResult battleResult = battleResultFromPattern("2 R, 1 B, 1C");
    BattleResult battleResult2 = battleResultFromPattern("2 R, 1 B, 1C");
    simResult.addBattleResult(battleResult);
    simResult.addBattleResult(battleResult2);

    assertEquals(2, simResult.getNumberOfSimulationRuns());

    assertEquals(2, (int) simResult.getMinPlayerLosses().get(Rekrut.class));
    assertEquals(1, (int) simResult.getMinPlayerLosses().get(Bogenschütze.class));
    assertEquals(1, (int) simResult.getMinPlayerLosses().get(Reiterei.class));

    assertEquals(2, (int) simResult.getMaxPlayerLosses().get(Rekrut.class));
    assertEquals(1, (int) simResult.getMaxPlayerLosses().get(Bogenschütze.class));
    assertEquals(1, (int) simResult.getMaxPlayerLosses().get(Reiterei.class));
  }

  @Test
  public void testAddBattleResult3() {
    BattleResult battleResult = battleResultFromPattern("2 R, 5 B, 1C");
    BattleResult battleResult2 = battleResultFromPattern("10 R, 1 B, 1C");
    BattleResult battleResult3 = battleResultFromPattern("7 R, 2 B, 1C");
    simResult.addBattleResult(battleResult);
    simResult.addBattleResult(battleResult2);
    simResult.addBattleResult(battleResult3);

    assertEquals(2, (int) simResult.getMinPlayerLosses().get(Rekrut.class));
    assertEquals(10, (int) simResult.getMaxPlayerLosses().get(Rekrut.class));

    assertEquals(1, (int) simResult.getMinPlayerLosses().get(Bogenschütze.class));
    assertEquals(5, (int) simResult.getMaxPlayerLosses().get(Bogenschütze.class));

    assertEquals(1, (int) simResult.getMinPlayerLosses().get(Reiterei.class));
    assertEquals(1, (int) simResult.getMaxPlayerLosses().get(Reiterei.class));

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
