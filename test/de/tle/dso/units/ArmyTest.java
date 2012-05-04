package de.tle.dso.units;

import java.util.List;
import de.tle.dso.units.computer.Plünderer;
import de.tle.dso.units.computer.boss.Bert;
import de.tle.dso.units.player.Rekrut;
import de.tle.dso.units.player.Reiterei;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArmyTest {

  private Army army;
  private int numberOfGeneratedUnits;

  @Before
  public void setUp() {
    army = new Army();
    army.add(new Rekrut());
    army.add(new Rekrut());
    army.add(new Rekrut());
    army.add(new Rekrut());
    army.add(new Plünderer());
    army.add(new Plünderer());
    army.add(new Plünderer());
    army.add(new Bert());
    numberOfGeneratedUnits = 8;
  }

  @Test
  public void testAddGeneral() {
    assertFalse(army.containsGeneral());
    army.addGeneral();
    assertTrue(army.containsGeneral());
    assertEquals(numberOfGeneratedUnits + 1, army.getUnits().size());
    assertEquals(1, army.getNumberOf("G"));
  }

  @Test
  public void testAdd() {
    army.add(new Rekrut());
    army.add(new Reiterei());
    assertEquals(numberOfGeneratedUnits + 2, army.getUnits().size());
  }

  @Test
  public void testIsValid2() {
    assertTrue(army.isValid());
    for (int i = 0; i < army.getMaxUnits() - numberOfGeneratedUnits; i++) {
      army.add(new Rekrut());
    }
    assertTrue(army.isValid());
    army.add(new Rekrut());
    assertFalse(army.isValid());
  }

  @Test
  public void testIsValid() {
    assertTrue(army.isValid());
    for (int i = 0; i < army.getMaxUnits() - numberOfGeneratedUnits; i++) {
      army.add(new Rekrut());
    }
    assertTrue(army.isValid());
    army.addGeneral();
    assertTrue(army.isValid());
  }

  @Test
  public void testIsValid3() {
    army = new Army(Army.VETERAN_MAX_SIZE);

    for (int i = 0; i < army.getMaxUnits(); i++) {
      army.add(new Rekrut());
    }
    assertTrue(army.isValid());
    army.addGeneral();
    assertTrue(army.isValid());
    army.add(new Rekrut());
    assertFalse(army.isValid());
  }

  @Test
  public void testGetUnitsByPrio() {
    assertEquals(numberOfGeneratedUnits, army.getUnitsByPrio().size());
  }

  @Test
  public void testGetUnitsByHitpoints() {
    assertEquals(numberOfGeneratedUnits, army.getUnitsByHitpoints().size());
  }

  @Test
  public void testRemoveDeadUnits() {
    assertEquals(numberOfGeneratedUnits, army.getUnits().size());

    List<Unit> deads = army.removeDeadUnits();
    assertTrue(deads.isEmpty());
    assertEquals(numberOfGeneratedUnits, army.getUnits().size());

    Unit toBeExecuted = army.getUnits().get(1);
    toBeExecuted.reduceHitpoints(toBeExecuted.getCurrentHitPoints());

    deads = army.removeDeadUnits();

    assertEquals(1, deads.size());
    assertSame(toBeExecuted, deads.get(0));
    assertEquals(numberOfGeneratedUnits - 1, army.getUnits().size());
  }

  @Test
  public void testGetNumberOf() {
    assertEquals(4, army.getNumberOf("R"));
    assertEquals(3, army.getNumberOf("PL"));
    assertEquals(1, army.getNumberOf("EB"));
  }
}
