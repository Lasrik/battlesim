package de.tle.simulation;

import java.util.Arrays;

public class Genom {

  public final static int NUMBER_OF_CHROMOSOMES = 9;
  public final static int MAX_CROSS_SUM = 200;
  public final static int MAX_SINGLE_VALUE = 200;
  private int[] chromosomes;

  public Genom(int[] chromosomes) {
    this.chromosomes = chromosomes;
  }

  public Genom() {
    this(new int[NUMBER_OF_CHROMOSOMES]);
  }

  public static Genom createRandom() {
    Genom instance = new Genom();
    instance.createRandomChromosomes();
    return instance;
  }

  public int[] getChromosomes() {
    return chromosomes;
  }

  public int getChromosom(int position) {
    return chromosomes[position];
  }

  public void setChromosom(int position, int value) {
    chromosomes[position] = value;
  }

  public void setChromosomes(int[] chromosomes) {
    this.chromosomes = chromosomes;
  }

  public Genom crossover(Genom other) {
    int[] newChromosomes = new int[NUMBER_OF_CHROMOSOMES];
    int randomPosition = getRandomChromosomPosition();

    firstPartFromThisGenom(newChromosomes, randomPosition);
    secondPartFromOtherGenom(other, randomPosition, newChromosomes);
    Genom result = new Genom(newChromosomes);

    return result;
  }

  public void reduceRandomChromosom() {
    if (crossSum() < 2) {
      return;
    }

    boolean end = false;
    do {
      int random = getRandomChromosomPosition();
      if (chromosomes[random] > 0) {
        chromosomes[random] -= 1;
        end = true;
      }
    } while (!end);
  }

  public void increaseRandomChromosom() {
    if (crossSum() >= MAX_CROSS_SUM) {
      return;
    }

    boolean end = false;
    do {
      int random = getRandomChromosomPosition();
      if (chromosomes[random] < MAX_SINGLE_VALUE) {
        chromosomes[random] += 1;
        end = true;
      }
    } while (!end);
  }

  public void switchRandomChromosomes() {
    int rand1 = getRandomChromosomPosition();
    int rand2 = getDifferentRandomChromosomPosition(rand1);

    int tmp = chromosomes[rand1];
    chromosomes[rand1] = chromosomes[rand2];
    chromosomes[rand2] = tmp;
  }

  public boolean isValid() {
    int crossSum = crossSum();
    if ((crossSum > 0 && crossSum < MAX_CROSS_SUM)) {
      return true;
    }

    return false;
  }

  @Override
  public String toString() {
    return Arrays.toString(chromosomes);
  }

  protected int getRandomChromosomPosition() {
    return getRandomInt(chromosomes.length);
  }

  private int getDifferentRandomChromosomPosition(int rand1) {
    int rand2 = getRandomChromosomPosition();
    while (rand1 == rand2) {
      rand2 = getRandomChromosomPosition();
    }
    return rand2;
  }

  protected int createRandomChromosomeValue() {
    return (int) (getRandom() * MAX_SINGLE_VALUE);
  }

  protected double getRandom() {
    return Math.random();
  }

  protected int getRandomInt(int ceiling) {
    return (int) (getRandom() * ceiling);
  }

  protected void createRandomChromosomes() {
    int[] newChromosomes = new int[NUMBER_OF_CHROMOSOMES];

    int crossSum = 0;

    for (int i = 0; i < newChromosomes.length; i++) {
      if (getRandom() < 0.5) {
        continue;
      }

      int nextValue = createRandomChromosomeValue();
      if (crossSum + nextValue > MAX_CROSS_SUM) {
        continue;
      }

      crossSum += nextValue;
      newChromosomes[i] = nextValue;
    }

    this.chromosomes = newChromosomes;
  }

  private int crossSum() {
    int result = 0;
    for (int chromosom : chromosomes) {
      result += chromosom;
    }
    return result;
  }

  private void secondPartFromOtherGenom(Genom other, int randomPosition, int[] newChromosomes) {
    System.arraycopy(other.chromosomes, randomPosition, newChromosomes, randomPosition, newChromosomes.length - randomPosition);
  }

  private void firstPartFromThisGenom(int[] newChromosomes, int randomPosition) {
    System.arraycopy(this.chromosomes, 0, newChromosomes, 0, randomPosition);
  }
}