package de.tle.dso.sim;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SimulationTest {

  @Before
  public void setUp() {
  }

  @Test
  public void testSimulate() throws Exception {
    Simulation sim = new Simulation("200 E, 1G", "160 RB, 10 WL, 30 WH");
    SimulationResult simResult = sim.simulate();

    assertNotNull(simResult);

    assertEquals(Simulation.MAX_RUNS, simResult.getNumberOfSimulationRuns());

    assertTrue(91 <= simResult.getMinPlayerLosses().getNumberOf("E"));
    assertTrue(104 >= simResult.getMaxPlayerLosses().getNumberOf("E"));
  }

  @Test
  public void testSimulate2() throws Exception {
    Simulation sim = new Simulation("200 S, 1G", "160 RB, 10 WL, 30 WH");
    SimulationResult simResult = sim.simulate();

    assertNotNull(simResult);

    assertEquals(Simulation.MAX_RUNS, simResult.getNumberOfSimulationRuns());

    assertTrue(115 <= simResult.getMinPlayerLosses().getNumberOf("S"));
    assertTrue(128 >= simResult.getMaxPlayerLosses().getNumberOf("S"));
  }

  @Test
  public void testSimulate3() throws Exception {
    Simulation sim = new Simulation("70 S, 130 LB, 1G", "1 DWW");
    SimulationResult simResult = sim.simulate();

    assertNotNull(simResult);

    assertEquals(Simulation.MAX_RUNS, simResult.getNumberOfSimulationRuns());

    assertEquals(1, (int) simResult.getMinComputerLosses().getNumberOf("DWW"));
    assertEquals(1, (int) simResult.getMaxComputerLosses().getNumberOf("DWW"));
  }
}