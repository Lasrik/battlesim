package de.tle.dso.sim.battle;

import java.util.Map;
import de.tle.dso.units.Unit;
import de.tle.dso.units.util.UnitPatternHelper;
import de.tle.dso.units.Army;
import de.tle.dso.units.computer.Steinwerfer;
import de.tle.dso.units.computer.Wachhund;
import de.tle.dso.units.computer.Waldläufer;
import de.tle.dso.units.player.Elitesoldat;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BattleTest {

  private Army playerArmy;
  private Army computerArmy;

  @Before
  public void setUp() {
  }

  @Test
  public void test1R_1PL() {
    testBattle("1 R, 1 G", "1 PL", "", "1 PL", true);
  }

  @Test
  public void test1R_2PL() {
    testBattle("1 R, 1 G", "2 PL", null, "2 PL", true);
  }

  @Test
  public void test1R_4PL() {
    testBattle("1 R, 1 G", "4 PL", "1 R, 1 G", "1 PL", false);
  }

  @Test
  public void test5R_5PL() {
    testBattle("5 R, 1 G", "5 PL", null, "5 PL", true);
  }

  @Test
  public void test5R_10PL() {
    testBattle("5 R, 1G", "10 PL", "5 R, 1 G", null, false);
  }

  @Test
  public void test2C_1SW() {
    testBattle("2 C, 1 G", "1 SW", "", "1 SW", true);
  }

  @Test
  public void test2C_1PL() {
    testBattle("2 C, 1 G", "1 PL", "1 C", "1 PL", true);
  }

  @Test
  public void test2B_5WH() {
    testBattle("2 B, 1 G", "5 WH", "2 B, 1 G", "", false);
  }

  @Test
  public void test1R1B_2WH() {
    testBattle("1 R, 1 B, 1 G", "2 WH", "1 B", "2 WH", true);
  }

  @Test
  public void test10B_1ST() {
    testBattle("10 B, 1G", "1ST", "10 B, 1 G", "", false);
  }

  @Test
  public void test100R10B_1CK() {
    testBattle("100R, 10B, 1G", "1CK", "100 R, 10 B, 1 G", "", false);
  }

  @Test
  public void testBossFirst() {
    playerArmy = createArmy("10R, 10M, 10C, 10S, 10E, 10B, 10LB, 10A, 10K, 1G");
    computerArmy = createArmy("5PL, 5SL, 5WH, 5RB, 5SW, 5WL, 1CK");

    Battle battle = new Battle(playerArmy, computerArmy);
    BattleResult result = null;
    try {
      result = battle.simulateBattle();
    } catch (InvalidArmyException ex) {
      fail(ex.toString());
    }

    assertNotNull(result);

    assertFalse(result.isBattleWon());
    assertEquals("10 R, 10 M, 10 C, 10 S, 10 E, 10 B, 10 LB, 10 A, 10 K, 1 G", toPattern(result.getPlayerLosses()));

    assertEquals(5, getLossesFor("WH", result));
    assertBetween(2, 5, getLossesFor("SW", result));
  }

  @Test
  public void test38R_160RB10WL30WH() {
    playerArmy = createArmy("38R, 1G");
    computerArmy = createArmy("160 RB, 10 WL, 30 WH");

    Battle battle = new Battle(playerArmy, computerArmy);
    BattleResult result = null;
    try {
      result = battle.simulateBattle();
    } catch (InvalidArmyException ex) {
      fail(ex.toString());
    }

    assertNotNull(result);

    assertEquals(30, getLossesFor("WH", result));
    assertBetween(0, 3, getLossesFor("WL", result));
  }

  @Test
  public void test200E_30WH160RB10WL() {
    playerArmy = createArmy("200 E, 1G");
    computerArmy = createArmy("160 RB, 10 WL, 30 WH");

    Battle battle = new Battle(playerArmy, computerArmy);
    BattleResult result = null;
    try {
      result = battle.simulateBattle();
    } catch (InvalidArmyException ex) {
      fail(ex.toString());
    }

    assertNotNull(result);
    assertTrue(result.isBattleWon());

    assertEquals("30 WH, 160 RB, 10 WL", toPattern(result.computerLosses));
    assertBetween(92, 102, getLossesFor("E", result));
  }

  private void testBattle(String attackerArmyPattern, String defenderArmyPattern, String attackerResultPattern, String defenderResultPattern, boolean win) {
    playerArmy = createArmy(attackerArmyPattern);
    computerArmy = createArmy(defenderArmyPattern);

    Battle battle = new Battle(playerArmy, computerArmy);
    BattleResult result = null;
    try {
      result = battle.simulateBattle();
    } catch (InvalidArmyException ex) {
      fail(ex.toString());
    }

    assertNotNull(result);

    if (attackerResultPattern != null) {
      assertEquals(attackerResultPattern, toPattern(result.getPlayerLosses()));
    }

    if (defenderResultPattern != null) {
      assertEquals(defenderResultPattern, toPattern(result.getComputerLosses()));
    }

    assertEquals(win, result.isBattleWon());
  }

  protected String toPattern(Army army) {
    return UnitPatternHelper.createPatternFromArmy(army);
  }

  protected int getLossesFor(String unit, BattleResult result) {
    int count = 0;

    if (result.getComputerLosses().getNumberOf(unit) == 0) {
      return result.getPlayerLosses().getNumberOf(unit);
    }

    return count;
  }

  protected void assertBetween(int min, int max, int actualValue) {
    assertTrue(min <= actualValue && actualValue <= max);
  }

  protected Army createArmy(String unitPattern) {
    return UnitPatternHelper.createArmyFromPattern(unitPattern);
  }
}
