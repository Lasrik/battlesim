package de.tle.dso.sim;

import com.spinn3r.log5j.LogManager;
import com.spinn3r.log5j.Logger;
import de.tle.dso.sim.battle.Battle;
import de.tle.dso.sim.battle.BattleResult;
import de.tle.dso.sim.battle.InvalidArmyException;
import de.tle.dso.units.Army;
import de.tle.dso.units.util.UnitPatternHelper;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class Simulation {

  private static int MAX_RUNS = 10000;
  private String attackingArmyPattern;
  private String defendingArmyPattern;
  private int numberOfRounds;
  private SimulationResult simResult;
  private final static Logger LOG = Logger.getLogger(false);
  private final ThreadPoolExecutor threadPool;

  public Simulation(String attackingArmyPattern, String defendingArmyPattern, int numberOfRounds) {
    this.attackingArmyPattern = attackingArmyPattern;
    this.defendingArmyPattern = defendingArmyPattern;
    this.numberOfRounds = numberOfRounds;
    this.simResult = new SimulationResult();

    int numberOfThreads = Runtime.getRuntime().availableProcessors() * 2;
    LOG.info("Number of processors: %s", numberOfThreads);

    this.threadPool = new ThreadPoolExecutor(numberOfThreads, numberOfThreads, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
  }

  public SimulationResult simulate() throws InvalidArmyException {
    List<BattleSimulatorRunner> listOfBattleRunners = new LinkedList<BattleSimulatorRunner>();

    for (int i = 0; i < numberOfRounds; i++) {
      Army attackingArmy = UnitPatternHelper.createArmyFromPattern(attackingArmyPattern);
      Army defendingArmy = UnitPatternHelper.createArmyFromPattern(defendingArmyPattern);

      listOfBattleRunners.add(new BattleSimulatorRunner(attackingArmy, defendingArmy));
    }

    try {
      List<Future<BattleResult>> results = threadPool.invokeAll(listOfBattleRunners);
      for (Future<BattleResult> battleResult : results) {
        try {
          simResult.addBattleResult(battleResult.get());
        } catch (ExecutionException ex) {
          java.util.logging.Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    } catch (InterruptedException ex) {
      // ignore
    }

    return simResult;
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
    System.exit(0);
  }

  private static class BattleSimulatorRunner implements Callable<BattleResult> {

    private Army attackingArmy;
    private Army defendingArmy;

    public BattleSimulatorRunner(Army attackingArmy, Army defendingArmy) {
      this.attackingArmy = attackingArmy;
      this.defendingArmy = defendingArmy;
    }

    @Override
    public BattleResult call() throws Exception {
      Battle battle = new Battle(attackingArmy, defendingArmy);
      return battle.simulateBattle();
    }
  }
}