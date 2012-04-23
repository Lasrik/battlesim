package de.tle.dso.sim;

import de.tle.dso.sim.battle.Battle;
import de.tle.dso.sim.battle.InvalidArmyException;
import de.tle.dso.units.Army;
import de.tle.dso.units.util.UnitPatternHelper;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Simulation {

  public final static int MAX_RUNS = 50;
  private String attackingArmyPattern;
  private String defendingArmyPattern;
  private int numberOfRounds;
  private SimulationResult simResult;
  private final static Logger LOG = Logger.getLogger(Simulation.class.getName());

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
    WeldContainer weld = new Weld().initialize();
    LOG.info("Starting Simulation ....");
    Simulation sim = new Simulation("200 S, 1G", "160 RB, 10 WL, 30 WH");
    sim.simulate();
  }

  private void logResults() {
    LOG.log(Level.INFO, "Done %s Runs", simResult.getNumberOfSimulationRuns());
    LOG.log(Level.INFO, "Min Player losses: %1s", UnitPatternHelper.createPatternFromArmy(simResult.getMinPlayerLosses()));
    LOG.log(Level.INFO, "Max Player losses: %1s", UnitPatternHelper.createPatternFromArmy(simResult.getMaxPlayerLosses()));
    LOG.log(Level.INFO, "Min Computer losses: %1s", UnitPatternHelper.createPatternFromArmy(simResult.getMinComputerLosses()));
    LOG.log(Level.INFO, "Max Computer losses: %1s", UnitPatternHelper.createPatternFromArmy(simResult.getMaxComputerLosses()));
    LOG.log(Level.INFO, "Max resource cost: %1s", simResult.getMaxResourceCosts());
    LOG.log(Level.INFO, "Always win: %1s", simResult.alwaysWin);
    LOG.log(Level.INFO, "Battles simulated per second: %.2f/s", simResult.getBattlesPerSecond());
    LOG.log(Level.INFO, "Total time: %ss", (simResult.getSimDuration() / 1000));
  }
}