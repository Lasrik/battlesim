package de.tle.simulation;

import com.spinn3r.log5j.LogManager;
import de.tle.dso.sim.DSOFitnessSimulationFunction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import static java.lang.Math.*;

public class EvolutionSimulation {
  /*
  http://de.wikipedia.org/wiki/Evolutionsstrategie#Definition
  http://de.wikipedia.org/wiki/Genetischer_Algorithmus#Praktisches_Vorgehen
  http://de.wikipedia.org/wiki/Evolutionsstrategie#Arten
   */

  public final static int PARENT_GEN_SIZE = 20;
  public final static int PARENTS_PER_CHILD = 2;
  public final static int CHILD_GEN_SIZE = 40;
  public final static String TARGET_PATTERN = "1 DWW";
  protected List<Individual> currentGeneration = new ArrayList<Individual>(PARENT_GEN_SIZE);
  protected List<Individual> children = new ArrayList<Individual>();
  protected final Random rand = new Random();
  private final static Logger LOG = Logger.getLogger(EvolutionSimulation.class);
  private int generationsRun = 0;
  private int bestFitnessSoFar = Integer.MAX_VALUE;
  private int successiveRunsWithoutImprovement = 0;

  public EvolutionSimulation() {
  }

  public void evolve() {
    init();
    calculateFitness(currentGeneration);
    logCurrentGeneration();

    do {
      LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
      LOG.info("Generation: " + generationsRun);
      recombine();
      mutate();
      calculateFitness(children);
      logChildren();
      selectNextGeneration();
      generationsRun++;
      LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
    } while (terminationCriteriaNotMet());
  }

  public static void main(String[] args) {
    LogManager.enableExplicitShutdown();
    LOG.info("Starting evolution...");
    EvolutionSimulation sim = new EvolutionSimulation();
    sim.evolve();
    LOG.info("Done.");
    LogManager.shutdown();
  }

  private void init() {
    for (int i = 0; i < PARENT_GEN_SIZE; i++) {
      currentGeneration.add(Individual.createRandom());
    }
  }

  private void recombine() {
    children = new ArrayList<Individual>(CHILD_GEN_SIZE);
    for (int i = 0; i < CHILD_GEN_SIZE; i++) {
      Individual parent1 = selectParent();
      Individual parent2 = selectParent();
      Individual child = parent1.mate(parent2);
      children.add(child);
    }

    logChildren();
  }

  private void mutate() {
    for (Individual child : children) {
      if (nextRandom(100) < 60) {
        int operation = nextRandom(10);
        switch (operation) {
          case 0:
          case 1:
          case 2:
          case 3:
            child.getGenom().reduceRandomChromosom();
            break;
          case 4:
          case 5:
          case 6:
          case 7:
            child.getGenom().increaseRandomChromosom();
            break;
          default:
            child.getGenom().switchRandomChromosomes();
        }
      }
    }

    logChildren();
  }

  private void calculateFitness(List<Individual> list) {
    FitnessFunction function = new DSOFitnessSimulationFunction();
    for (Individual individual : list) {
      function.evaluate(individual);
    }
  }

  private void selectNextGeneration() {
    List<Individual> candidates = new ArrayList<Individual>();
    candidates.addAll(currentGeneration);
    candidates.addAll(children);
    Collections.sort(candidates);
    currentGeneration = candidates.subList(0, PARENT_GEN_SIZE);
    logCurrentGeneration();
  }

  private boolean terminationCriteriaMet() {
    Individual currentBestCandidate = currentGeneration.get(0);
    if (bestFitnessSoFar > currentBestCandidate.getFitness()) {
      bestFitnessSoFar = currentBestCandidate.getFitness();
      successiveRunsWithoutImprovement = 0;
    } else {
      successiveRunsWithoutImprovement++;
    }

    return successiveRunsWithoutImprovement > 10 || generationsRun > 10000;
  }

  private boolean terminationCriteriaNotMet() {
    return !terminationCriteriaMet();
  }

  private int nextRandom(int ceiling) {
    return (int) (Math.random() * ceiling);
  }

  private void logCurrentGeneration() {
    StringBuilder sb = new StringBuilder();
    sb.append("Current: ");
    appendIndividuals(sb, currentGeneration);
    LOG.info(sb.toString());
  }

  private void appendIndividuals(StringBuilder sb, List<Individual> list) {
    for (Individual individual : list) {
      sb.append(individual.toString());
    }
  }

  private void logChildren() {
    StringBuilder sb = new StringBuilder();
    sb.append("Children: ");
    appendIndividuals(sb, children);

    LOG.debug(sb.toString());
  }

  private Individual selectParent() {
    int position = (int) (Math.random() * (currentGeneration.size() / 2));
    Individual parent = currentGeneration.get(position);
    return parent;
  }
}
