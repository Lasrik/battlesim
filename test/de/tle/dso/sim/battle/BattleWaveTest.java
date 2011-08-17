package de.tle.dso.sim.battle;

import de.tle.dso.units.Army;
import de.tle.dso.units.Initiative;
import de.tle.dso.units.Unit;
import de.tle.dso.units.computer.Plünderer;
import de.tle.dso.units.player.General;
import de.tle.dso.units.player.Reiterei;
import de.tle.dso.units.player.Rekrut;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BattleWaveTest {

  private Army attackers;
  private Army defenders;
  private BattleWave wave;
  private Unit mockRekrut;
  private Unit mockPlünderer;
  private Unit mockGeneral;
  private Unit mockReiterei;

  @Before
  public void setUp() {
    attackers = new Army();
    defenders = new Army();

    mockRekrut = spy(new Rekrut());
    mockPlünderer = spy(new Plünderer());
    mockGeneral = spy(new General());
    mockReiterei = spy(new Reiterei());

    attackers.add(mockRekrut);
    defenders.add(mockPlünderer);

    wave = new BattleWave(attackers, defenders, Initiative.MEDIUM);
  }

  @Test
  public void testFight() {
    attackers.add(mockReiterei);
    wave.fight();
    verify(mockReiterei, never()).doDamage();
  }

  @Test
  public void testFight2() {
    when(mockRekrut.doDamage()).thenReturn(666);

    wave.fight();

    verify(mockPlünderer, times(1)).reduceHitpoints(666);

    List<Unit> deads = defenders.removeDeadUnits();
    assertEquals(1, deads.size());
    assertSame(mockPlünderer, deads.get(0));
  }

  @Test
  public void testFight3() {
    Unit mockRekrut2 = spy(new Rekrut());
    attackers.add(mockRekrut2);

    when(mockRekrut.doDamage()).thenReturn(666);

    wave.fight();

    verify(mockRekrut2, never()).doDamage();
  }

  @Test
  public void testFight4() {
    Unit mockRekrut2 = spy(new Rekrut());
    attackers.add(mockRekrut2);

    when(mockRekrut.doDamage()).thenReturn(1);

    wave.fight();

    verify(mockRekrut2, times(1)).doDamage();
  }

  @Test
  public void testAttackersAreNotAttacked() {
    wave.fight();
    verify(mockRekrut, never()).reduceHitpoints(anyInt());
  }
}
