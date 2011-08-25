package de.tle.dso.sim;

import de.tle.dso.sim.battle.InvalidArmyException;
import de.tle.dso.units.util.UnitPatternHelper;
import de.tle.simulation.EvolutionSimulation;
import de.tle.simulation.FitnessFunction;
import de.tle.simulation.Individual;

public class DSOFitnessSimulationFunction implements FitnessFunction {

  public final static int LOOSE_PENALTY = 1000000;
  public final static int INVALID_GENOM_PENALTY = 10000000;

  @Override
  public void evaluate(Individual individual) {
    try {
      Simulation sim = new Simulation(UnitPatternHelper.createPatternFromArray(individual.getGenom().getChromosomes()), EvolutionSimulation.TARGET_PATTERN);
      SimulationResult simResult = sim.simulate();
      int fitness = calculateFitness(simResult);
      individual.setFitness(fitness);
    } catch (InvalidArmyException ex) {
      individual.setFitness(INVALID_GENOM_PENALTY);
    }
  }

  protected int calculateFitness(SimulationResult simResult) {
    int fitness = 0;
    fitness = simResult.getMaxResourceCosts().totalWeightPoints();
    // fitness += crossSum();
    if (!simResult.isAlwaysWin()) {
      fitness += LOOSE_PENALTY;
    }
    return fitness;
  }
}
