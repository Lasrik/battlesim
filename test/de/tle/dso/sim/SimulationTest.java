package de.tle.dso.sim;

import de.tle.dso.units.computer.boss.Waltraud;
import de.tle.dso.units.player.Elitesoldat;
import de.tle.dso.units.player.Soldat;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SimulationTest {

  public static final int MAX_RUNS = 100;

  @Before
  public void setUp() {
  }

  @Test
  public void testSimulate() throws Exception {
    Simulation sim = new Simulation("200 E, 1G", "160 RB, 10 WL, 30 WH", MAX_RUNS);
    SimulationResult simResult = sim.simulate();

    assertNotNull(simResult);

    assertEquals(MAX_RUNS, simResult.getNumberOfSimulationRuns());

    assertTrue(91 <= simResult.getMinPlayerLosses().get(Elitesoldat.class));
    assertTrue(104 >= simResult.getMaxPlayerLosses().get(Elitesoldat.class));
  }

  @Test
  public void testSimulate2() throws Exception {
    Simulation sim = new Simulation("200 S, 1G", "160 RB, 10 WL, 30 WH", MAX_RUNS);
    SimulationResult simResult = sim.simulate();

    assertNotNull(simResult);

    assertEquals(MAX_RUNS, simResult.getNumberOfSimulationRuns());

    assertTrue(115 <= simResult.getMinPlayerLosses().get(Soldat.class));
    assertTrue(128 >= simResult.getMaxPlayerLosses().get(Soldat.class));
  }

  @Test
  public void testSimulate3() throws Exception {
    Simulation sim = new Simulation("70 S, 130 LB, 1G", "1 DWW", MAX_RUNS);
    SimulationResult simResult = sim.simulate();

    assertNotNull(simResult);

    assertEquals(MAX_RUNS, simResult.getNumberOfSimulationRuns());

    assertEquals(1, (int) simResult.getMinComputerLosses().get(Waltraud.class));
    assertEquals(1, (int) simResult.getMaxComputerLosses().get(Waltraud.class));
  }
}
