package de.tle.dso.sim;

import de.tle.dso.sim.battle.Battle;
import de.tle.dso.sim.battle.BattleResult;
import de.tle.dso.sim.battle.InvalidArmyException;
import de.tle.dso.units.Army;
import de.tle.dso.units.util.UnitPatternHelper;

public class Simulation {
  private static int MAX_RUNS = 100000;

  private String attackingArmyPattern;
  private String defendingArmyPattern;
  private int numberOfRounds;
  private SimulationResult simResult;
  private Army attackingArmy;
  private Army defendingArmy;

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
    System.out.println("Starting Simulation ....");
    Simulation sim = new Simulation("200 S, 1G", "160 RB, 10 WL, 30 WH", MAX_RUNS);
    SimulationResult simResult = sim.simulate();

    System.out.printf("Done %s Runs\n", new Object[] {simResult.getNumberOfSimulationRuns()});
    System.out.printf("Min Player losses: %s\n", new Object[] {UnitPatternHelper.createPatternFromMap(simResult.getMinPlayerLosses())});
    System.out.printf("Max Player losses: %s\n", new Object[] {UnitPatternHelper.createPatternFromMap(simResult.getMaxPlayerLosses())});
    System.out.printf("Min Computer losses: %s\n", new Object[] {UnitPatternHelper.createPatternFromMap(simResult.getMinComputerLosses())});
    System.out.printf("Max Computer losses: %s\n", new Object[] {UnitPatternHelper.createPatternFromMap(simResult.getMaxComputerLosses())});
  }
}
