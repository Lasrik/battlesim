package de.tle.simulation;

import de.tle.dso.sim.Simulation;
import de.tle.dso.sim.SimulationResult;
import de.tle.dso.sim.battle.InvalidArmyException;
import de.tle.dso.units.util.UnitPatternHelper;

public class Individual implements Comparable<Individual> {

  public final static int MAX = 200;
  public final static int GENOME_SIZE = 7;
  protected int[] genom;
  private int fitness = 0;

  public Individual(int[] genom) {
    this.genom = genom;
  }

  public static Individual createRandom() {
    return new Individual(randomGenome());
  }

  public Individual mate(Individual other) {
    int[] newGenome = new int[GENOME_SIZE];

    int random = getRandomAllele();
    System.arraycopy(this.genom, 0, newGenome, 0, random);
    System.arraycopy(other.genom, random, newGenome, random, newGenome.length - random);

    Individual child = new Individual(newGenome);
    while (child.crossSum() > 200) {
      child.reduceRandomAllele();
    }
    while (child.crossSum() < 1) {
      child.increaseRandomAllele();
    }

    return child;
  }

  public int[] getGenom() {
    return genom;
  }

  public void setGenom(int[] genom) {
    this.genom = genom;
  }

  public void calculateFitness() {
    Simulation sim = new Simulation(UnitPatternHelper.createPatternFromArray(genom), EvolutionSimulation.TARGET_PATTERN);
    try {
      SimulationResult simResult = sim.simulate();
      fitness = simResult.getMaxResourceCosts().totalWeightPoints();
      fitness += crossSum();
      if (!simResult.isAlwaysWin()) {
        fitness += 1000000;
      }
    } catch (InvalidArmyException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  public int getFitness() {
    return fitness;
  }

  private static int getRandom() {
    return (int) (Math.random() * MAX);
  }

  private static int[] randomGenome() {
    int[] newGenom = new int[GENOME_SIZE];

    int sum = 0;

    for (int i = 0; i < newGenom.length; i++) {
      if (Math.random() < 0.6) {
        continue;
      }

      int nextRandom = getRandom();
      if (sum + nextRandom > MAX) {
        continue;
      }
      sum += nextRandom;
      newGenom[i] = nextRandom;
    }

    return newGenom;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{ -");
    sb.append(fitness);
    sb.append("- [");
    sb.append(UnitPatternHelper.createPatternFromArray(genom));
    sb.append("]} ");
    return sb.toString();
  }

  public void reduceRandomAllele() {
    if (crossSum() < 2) {
      return;
    }

    boolean end = false;
    do {
      int random = getRandomAllele();;
      if (genom[random] > 0) {
        genom[random] -= 1;
        end = true;
      }
    } while (!end);
  }

  public void increaseRandomAllele() {
    if (crossSum() > 199) {
      return;
    }

    boolean end = false;
    do {
      int random = getRandomAllele();;
      if (genom[random] < 199) {
        genom[random] += 1;
        end = true;
      }
    } while (!end);
  }

  public void switchRandomAllele() {
    int rand1 = getRandomAllele();
    int rand2 = getRandomAllele();;

    int tmp = genom[rand1];
    genom[rand1] = genom[rand2];
    genom[rand2] = tmp;
  }

  private int getRandomAllele() {
    int rand1 = (int) (Math.random() * genom.length);
    return rand1;
  }

  private int crossSum() {
    int result = 0;
    for (int i = 0; i < genom.length; i++) {
      result += genom[i];
    }
    return result;
  }

  @Override
  public int compareTo(Individual o) {
    return this.fitness - o.fitness;
  }
}