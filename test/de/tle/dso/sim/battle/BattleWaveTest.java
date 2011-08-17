package de.tle.dso.sim.battle;

import de.tle.dso.units.Army;
import de.tle.dso.units.Initiative;
import de.tle.dso.units.computer.Plünderer;
import de.tle.dso.units.player.Rekrut;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BattleWaveTest {

  private Army attackers;
  private Army defenders;

  @Before
  public void setUp() {
    attackers = new Army();
    defenders = new Army();
  }

  @Test
  public void testFight() {
    
  }

  @Test
  public void testFight2() {
    Rekrut mockRekrut = spy(new Rekrut());
    Plünderer mockPlünderer = spy(new Plünderer());

    when(mockRekrut.doDamage()).thenReturn(666);

    attackers.add(mockRekrut);
    defenders.add(mockPlünderer);

    BattleWave wave = new BattleWave(attackers, defenders, Initiative.MEDIUM);
    wave.fight();

    verify(mockPlünderer, times(1)).reduceHitpoints(666);
  }
}
