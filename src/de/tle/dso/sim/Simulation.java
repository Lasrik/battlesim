package de.tle.dso.sim;

import de.tle.dso.sim.battle.Battle;
import de.tle.dso.sim.battle.InvalidArmyException;
import de.tle.dso.units.Army;
import de.tle.dso.units.util.UnitPatternHelper;
import org.apache.log4j.Logger;


public class Simulation {
 public final static int MAX_RUNS = 50;
  private String attackingArmyPattern;
  private String defendingArmyPattern;
  private int numberOfRounds;
  private SimulationResult simResult;
  private final static Logger LOG = Logger.getLogger(Simulation.class);

  public Simulation(String attackingArmyPattern, String defendingArmyPattern) {
    this.attackingArmyPattern = attackingArmyPattern;
    this.defendingArmyPattern = defendingArmyPattern;
    this.numberOfRounds = MAX_RUNS;
    this.simResult = new SimulationResult();
  }

  public SimulationResult simulate() throws InvalidArmyException {
    long start = System.currentTimeMillis();

    for (int rounds = 0; rounds < numberOfRounds; rounds++) {
      Army attackingArmy = createArmy(attackingArmyPattern);
      Army defendingArmy = createArmy(defendingArmyPattern);
      Battle battle = new Battle(attackingArmy, defendingArmy);
      simResult.addBattleResult(battle.simulateBattle());
    }

    simResult.setSimDuration(System.currentTimeMillis() - start);
    logResults();

    return simResult;
  }

  private Army createArmy(String pattern) {
    Army attackingArmy = UnitPatternHelper.createArmyFromPattern(pattern);
    return attackingArmy;
  }

  public static void main(String[] args) throws InvalidArmyException {
    LOG.info("Starting Simulation ....");
    Simulation sim = new Simulation("200 S, 1G", "50WH, 49WL, 100RB, 1CK");
    sim.simulate();
  }

  private void logResults() {
    if (LOG.isDebugEnabled()) {
      LOG.debug(String.format("Done %s Runs", simResult.getNumberOfSimulationRuns()));
      LOG.debug(String.format("Min Player losses: %s", UnitPatternHelper.createPatternFromArmy(simResult.getMinPlayerLosses())));
      LOG.debug(String.format("Max Player losses: %s", UnitPatternHelper.createPatternFromArmy(simResult.getMaxPlayerLosses())));
      LOG.debug(String.format("Min Computer losses: %s", UnitPatternHelper.createPatternFromArmy(simResult.getMinComputerLosses())));
      LOG.debug(String.format("Max Computer losses: %s", UnitPatternHelper.createPatternFromArmy(simResult.getMaxComputerLosses())));
      LOG.debug(String.format("Max resource cost: %s", simResult.getMaxResourceCosts()));
      LOG.debug(String.format("Always win: %s", simResult.alwaysWin));
      LOG.debug(String.format("Battles simulated per second: %.2f/s", simResult.getBattlesPerSecond()));
      LOG.debug(String.format("Total time: %dms", (simResult.getSimDuration())));
    }
  }
}