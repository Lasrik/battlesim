package de.tle.simulation;

import de.tle.dso.sim.Simulation;
import de.tle.dso.sim.SimulationResult;
import de.tle.dso.sim.battle.InvalidArmyException;
import de.tle.dso.units.util.UnitPatternHelper;

public class Individual implements Comparable<Individual> {

  protected Genom genom;
  private int fitness = 0;

  public Individual(Genom genom) {
    this.genom = genom;
  }

  public static Individual createRandom() {
    return new Individual(Genom.createRandom());
  }

  public Individual mate(Individual other) {
    Genom newGenom = genom.crossover(other.genom);

    Individual child = new Individual(newGenom);

    return child;
  }

  public Genom getGenom() {
    return genom;
  }

  public void setGenom(Genom genom) {
    this.genom = genom;
  }

  public int getFitness() {
    return fitness;
  }

  public void setFitness(int fitness) {
    this.fitness = fitness;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{ -");
    sb.append(fitness);
    sb.append("- [");
    sb.append(UnitPatternHelper.createPatternFromArray(genom.getChromosomes()));
    sb.append("]} ");
    return sb.toString();
  }

  @Override
  public int compareTo(Individual o) {
    return this.fitness - o.fitness;
  }
}