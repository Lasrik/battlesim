package de.tle.dso.sim.battle;

import org.junit.BeforeClass;
import de.tle.dso.units.Army;
import de.tle.dso.units.Initiative;
import de.tle.dso.units.Unit;
import de.tle.dso.units.computer.Plünderer;
import de.tle.dso.units.player.Rekrut;
import de.tle.dso.units.util.UnitPatternHelper;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static de.tle.dso.units.Initiative.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class BattleWaveTest {

  private Army attackers;
  private Army defenders;

  @BeforeClass
  public static void BeforeClass() {
    Logger.getRootLogger().removeAllAppenders();
  }

  @Before
  public void setUp() {
    attackers = new Army();
    defenders = new Army();
  }

  @Test
  public void testInitiative() {
    attackers = createMockArmy("3 R, 3 C, 1 K");
    defenders = createMockArmy("200 PL");

    startFight(Initiative.MEDIUM);

    for (Unit unit : attackers.getUnits()) {
      if (unit.getInitiative().equals(HIGH) || unit.getInitiative().equals(LOW)) {
        verify(unit, never()).doDamage();
      } else if (unit.getInitiative().equals(MEDIUM)) {
        verify(unit, times(1)).doDamage();
      }
    }
  }

  @Test
  public void testFight2() {
    Unit mockRekrut = spy(new Rekrut());

    when(mockRekrut.doDamage()).thenReturn(666);

    Unit mockPlünderer = spy(new Plünderer());

    attackers.add(mockRekrut);
    defenders.add(mockPlünderer);

    startFight(MEDIUM);

    verify(mockPlünderer, times(1)).reduceHitpoints(666);

    List<Unit> deads = defenders.removeDeadUnits();
    assertEquals(1, deads.size());
    assertSame(mockPlünderer, deads.get(0));
  }

  @Test
  public void testFight3() {
    attackers = createMockArmy("1 R");
    defenders = createMockArmy("2 PL");

    when(attackers.getUnits().get(0).doDamage()).thenReturn(666);

    startFight(MEDIUM);

    verify(defenders.getUnits().get(1), never()).reduceHitpoints(anyInt());
  }

  @Test
  public void testFight4() {
    attackers = createMockArmy("2 LB");
    defenders = createMockArmy("1 PL");

    // when(attackers.getUnits().get(0).doDamage()).thenReturn(1);
    when(defenders.getUnits().get(0).isDead()).thenReturn(Boolean.FALSE);

    startFight(MEDIUM);

    verify(attackers.getUnits().get(1), times(1)).doDamage();
  }

  @Test
  public void testAttackersAreNotAttacked() {
    attackers = createMockArmy("1 R, 2 C, 1 K, 1G");
    defenders = createMockArmy("1 PL, 2 WH, 1 EB, 1 DWW");

    startFight(MEDIUM);

    for (Unit unit : attackers.getUnits()) {
      verify(unit, never()).reduceHitpoints(anyInt());
    }

    for (Unit unit : defenders.getUnits()) {
      verify(unit, never()).doDamage();
    }
  }

  @Test
  public void testSplash() {
    attackers = createMockArmy("1 CK");
    defenders = createMockArmy("50 R");

    startFight(LOW);

    for (Unit unit : defenders.getUnits()) {
      verify(unit, times(1)).reduceHitpoints(anyInt());
    }
  }

  private Army createMockArmy(String pattern) {
    Army army = UnitPatternHelper.createArmyFromPattern(pattern);
    List<Unit> units = army.getUnits();

    for (int i = 0; i < units.size(); i++) {
      Unit unit = units.get(i);
      units.set(i, spy(unit));
    }

    return army;
  }

  private void startFight(Initiative phase) {
    new BattleWave(attackers, defenders, phase).fight();
  }
}