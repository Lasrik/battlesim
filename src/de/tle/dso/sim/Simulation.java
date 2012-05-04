package de.tle.dso.sim;

import de.tle.dso.sim.battle.Battle;
import de.tle.dso.sim.battle.InvalidArmyException;
import de.tle.dso.units.Army;
import de.tle.dso.units.util.UnitPatternHelper;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Simulation {
  public static final String ATTACKING_ARMY_PATTERN = "41M, 96C, 41A, 1G";
  public static final String DEFENDING_ARMY_PATTERN = "75WK, 50Kar";

  public final static int MAX_RUNS = 100;
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

  public void setNumberOfRounds(int numberOfRounds) {
    this.numberOfRounds = numberOfRounds;
  }

  private Army createArmy(String pattern) {
    Army attackingArmy = UnitPatternHelper.createArmyFromPattern(pattern);
    return attackingArmy;
  }

  public static void main(String[] args) throws InvalidArmyException {
    BasicConfigurator.configure();
    // Logger.getRootLogger().setLevel(Level.TRACE);
    LOG.info("Starting Simulation ....");
    Simulation sim = new Simulation(ATTACKING_ARMY_PATTERN, DEFENDING_ARMY_PATTERN);
    sim.setNumberOfRounds(1500);
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