package de.tle.simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

public class EvolutionSimulation {
  /*
  http://de.wikipedia.org/wiki/Evolutionsstrategie#Definition
  http://de.wikipedia.org/wiki/Genetischer_Algorithmus#Praktisches_Vorgehen
  http://de.wikipedia.org/wiki/Evolutionsstrategie#Arten
   */

  public final static int PARENT_GEN_SIZE = 20;
  public final static int PARENTS_PER_CHILD = 2;
  public final static int CHILD_GEN_SIZE = 5;
  protected final String targetPattern;
  protected List<Individual> currentGeneration = new ArrayList<Individual>(PARENT_GEN_SIZE);
  protected List<Individual> selectedParents = new ArrayList<Individual>();
  protected List<Individual> children = new ArrayList<Individual>();
  private final static Logger LOG = Logger.getLogger(EvolutionSimulation.class);

  public EvolutionSimulation(String targetPattern) {
    this.targetPattern = targetPattern;
  }

  public void evolve() {
    init();
    calculateFitness(currentGeneration);

    do {
      selectParents();
      recombine();
      mutate();
      calculateFitness(children);
      logChildren();
      selectNextGeneration();
    } while (terminationCriteriaNotMet());
  }

  public static void main(String[] args) {
    LOG.info("Starting evlution...");
    EvolutionSimulation sim = new EvolutionSimulation("");
    sim.evolve();
    LOG.info("Done.");
  }

  private void init() {
    for (int i = 0; i < PARENT_GEN_SIZE; i++) {
      currentGeneration.add(Individual.createRandom());
    }

    logCurrentGeneration();
  }

  private void selectParents() {
    selectedParents = new ArrayList<Individual>(CHILD_GEN_SIZE * PARENTS_PER_CHILD);
    for (int i = 0; i < CHILD_GEN_SIZE * PARENTS_PER_CHILD; i++) {
      selectedParents.add(currentGeneration.get(nextRandom(currentGeneration.size())));
    }

    logSelectedParents();
  }

  private void recombine() {
    children = new ArrayList<Individual>(CHILD_GEN_SIZE);
    for (int i = 0; i < CHILD_GEN_SIZE; i++) {
      Individual parent1 = selectedParents.get(PARENTS_PER_CHILD * i);
      Individual parent2 = selectedParents.get(PARENTS_PER_CHILD * i + 1);
      Individual child = parent1.mate(parent2);
      children.add(child);
    }

    logChildren();
  }

  private void mutate() {
    for (Individual child : children) {
      if (nextRandom(100) < 10) {
        int operation = nextRandom(10);
        switch (operation) {
          case 0:
          case 1:
          case 2:
          case 3:
            child.reduceRandomAllele();
            break;
          case 4:
          case 5:
          case 6:
          case 7:
            child.increaseRandomAllele();
            break;
          default:
            child.switchRandomAllele();
        }
      }
    }

    logChildren();
  }

  private void calculateFitness(List<Individual> list) {
    for (Individual individual : list) {
      individual.calculateFitness();
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

  private boolean terminationCriteriaNotMet() {
    return false;
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

  private void logSelectedParents() {
    StringBuilder sb = new StringBuilder();
    sb.append("Parents: ");
    appendIndividuals(sb, selectedParents);

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

    LOG.info(sb.toString());
  }
}
