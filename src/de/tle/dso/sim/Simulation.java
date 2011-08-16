package de.tle.dso.sim;

import com.spinn3r.log5j.LogManager;
import com.spinn3r.log5j.Logger;
import de.tle.dso.sim.battle.Battle;
import de.tle.dso.sim.battle.BattleResult;
import de.tle.dso.sim.battle.InvalidArmyException;
import de.tle.dso.units.Army;
import de.tle.dso.units.util.UnitPatternHelper;

public class Simulation {
  private static int MAX_RUNS = 10000;

  private String attackingArmyPattern;
  private String defendingArmyPattern;
  private int numberOfRounds;
  private SimulationResult simResult;
  private Army attackingArmy;
  private Army defendingArmy;
  private final static Logger LOG = Logger.getLogger(false);

  public Simulation(String attackingArmyPattern, String defendingArmyPattern, int numberOfRounds) {
    this.attackingArmyPattern = attackingArmyPattern;
    this.defendingArmyPattern = defendingArmyPattern;
    this.numberOfRounds = numberOfRounds;
    this.simResult = new SimulationResult();
  }

  public SimulationResult simulate() throws InvalidArmyException {
    for (int i = 0; i < numberOfRounds; i++) {
      createArmies();
      Battle battle = new Battle(attackingArmy, defendingArmy);
      BattleResult roundResult = battle.simulateBattle();
      simResult.addBattleResult(roundResult);
    }

    return simResult;
  }

  private void createArmies() {
    attackingArmy = UnitPatternHelper.createArmyFromPattern(attackingArmyPattern);
    defendingArmy = UnitPatternHelper.createArmyFromPattern(defendingArmyPattern);
  }

  public static void main(String[] args) throws InvalidArmyException {
    LOG.info("Starting Simulation ....");
    long start = System.currentTimeMillis();
    Simulation sim = new Simulation("200 S, 1G", "160 RB, 10 WL, 30 WH", MAX_RUNS);
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