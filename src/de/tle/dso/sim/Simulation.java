package de.tle.dso.sim;

import com.spinn3r.log5j.LogManager;
import com.spinn3r.log5j.Logger;
import de.tle.dso.sim.battle.Battle;
import de.tle.dso.sim.battle.InvalidArmyException;
import de.tle.dso.units.Army;
import de.tle.dso.units.util.UnitPatternHelper;

public class Simulation {

  public final static int MAX_RUNS = 50;
  private String attackingArmyPattern;
  private String defendingArmyPattern;
  private int numberOfRounds;
  private SimulationResult simResult;
  private final static Logger LOG = Logger.getLogger(false);

  public Simulation(String attackingArmyPattern, String defendingArmyPattern) {
    this.attackingArmyPattern = attackingArmyPattern;
    this.defendingArmyPattern = defendingArmyPattern;
    this.numberOfRounds = MAX_RUNS;
    this.simResult = new SimulationResult();
  }

  public SimulationResult simulate() throws InvalidArmyException {
    for (int rounds = 0; rounds < numberOfRounds; rounds++) {
      Army attackingArmy = createArmy(attackingArmyPattern);
      Army defendingArmy = createArmy(defendingArmyPattern);
      Battle battle = new Battle(attackingArmy, defendingArmy);
      simResult.addBattleResult(battle.simulateBattle());
    }

    return simResult;
  }

  private Army createArmy(String pattern) {
    Army attackingArmy = UnitPatternHelper.createArmyFromPattern(pattern);
    return attackingArmy;
  }

  public static void main(String[] args) throws InvalidArmyException {
    LOG.info("Starting Simulation ....");
    long start = System.currentTimeMillis();
    Simulation sim = new Simulation("200 S, 1G", "160 RB, 10 WL, 30 WH");
    SimulationResult simResult = sim.simulate();

    LOG.info("Done %s Runs", simResult.getNumberOfSimulationRuns());
    LOG.info("Min Player losses: %s", UnitPatternHelper.createPatternFromMap(simResult.getMinPlayerLosses()));
    LOG.info("Max Player losses: %s", UnitPatternHelper.createPatternFromMap(simResult.getMaxPlayerLosses()));
    LOG.info("Min Computer losses: %s", UnitPatternHelper.createPatternFromMap(simResult.getMinComputerLosses()));
    LOG.info("Max Computer losses: %s", UnitPatternHelper.createPatternFromMap(simResult.getMaxComputerLosses()));
    LOG.info("Time: %s s", (System.currentTimeMillis() - start) / 1000);
    LogManager.shutdown();
  }
}