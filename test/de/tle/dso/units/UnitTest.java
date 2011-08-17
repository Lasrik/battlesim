package de.tle.dso.units;

import de.tle.dso.units.player.Rekrut;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UnitTest {

  @Before
  public void setUp() {
  }

  public void testHp() {
    Unit unit = new Rekrut();
    assertEquals(unit.getMaxHitPoints(), unit.getCurrentHitPoints());
  }

  @Test
  public void testDoDamage() {
    Unit unit = new Rekrut();
    int damage = unit.doDamage();
    assertTrue(unit.getMinDamage() <= damage);
    assertTrue(unit.getMaxDamage() >= damage);
  }

  @Test
  public void testReduceHitpoints() {
    Unit unit = new Rekrut();
    unit.reduceHitpoints(unit.getMaxHitPoints());
    assertTrue(unit.isDead());
    assertFalse(unit.isAlive());
  }

  @Test
  public void testReduceHitpoints2() {
    Unit unit = new Rekrut();
    unit.reduceHitpoints(unit.getMaxHitPoints() - 1);
    assertFalse(unit.isDead());
    assertTrue(unit.isAlive());
  }

}
