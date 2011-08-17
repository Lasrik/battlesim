package de.tle.dso.sim.battle;

import de.tle.dso.units.Unit;
import de.tle.dso.units.computer.Plünderer;
import de.tle.dso.units.computer.boss.Bert;
import de.tle.dso.units.player.Reiterei;
import de.tle.dso.units.player.Rekrut;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BattleResultTest {

  private BattleResult result;

  public BattleResultTest() {
  }

  @Before
  public void setUp() {
    result = new BattleResult();
  }

  @Test
  public void testAddDeadPlayerUnit() {
    result.addDeadPlayerUnit(new Rekrut());
    assertNotNull(result.getPlayerLosses());
    assertEquals(1, (int) result.getPlayerLosses().get(Rekrut.class));
    assertEquals(null, result.getPlayerLosses().get(Plünderer.class));
  }

  @Test
  public void testAddDeadComputerUnits() {
    List<Unit> units = new LinkedList<Unit>();
    units.add(new Rekrut());
    units.add(new Rekrut());
    units.add(new Rekrut());
    units.add(new Bert());
    units.add(new Reiterei());

    result.addDeadComputerUnits(units);

    assertEquals(3, (int) result.getComputerLosses().get(Rekrut.class));
    assertEquals(1, (int) result.getComputerLosses().get(Bert.class));
  }
}