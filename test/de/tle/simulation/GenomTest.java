package de.tle.simulation;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GenomTest {

  private int[] testChromosomes;
  private Genom instance;

  @Before
  public void setUp() {
    testChromosomes = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    instance = new Genom(testChromosomes);
  }

  @Test
  public void testCreateRandom() {
    Genom result = Genom.createRandom();
    assertNotNull(result);
    assertTrue(result.isValid());
  }

  @Test
  public void testGetChromosomes() {
    int[] result = instance.getChromosomes();
    assertEquals(testChromosomes, result);
  }

  @Test
  public void testGetChromosom() {
    assertEquals(5, instance.getChromosom(4));
  }

  @Test
  public void testSetChromosom() {
    instance.setChromosom(4, 666);
    assertEquals(666, instance.getChromosomes()[4]);
  }

  @Test
  public void testSetChromosomes() {
    instance.setChromosomes(testChromosomes);
    assertTrue(Arrays.equals(testChromosomes, instance.getChromosomes()));
  }

  @Test
  public void testCrossover() {
    int[] testChromosomes1 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    int[] testChromosomes2 = new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90};

    Genom parent1 = new GenomWithoutRandomness(testChromosomes1);
    Genom parent2 = new Genom(testChromosomes2);

    Genom child = parent1.crossover(parent2);

    int[] newChromosome = new int[]{1, 2, 3, 4, 50, 60, 70, 80, 90};
    assertTrue(Arrays.equals(newChromosome, child.getChromosomes()));
  }

  @Test
  public void testReduceRandomChromosom() {
    instance = new GenomWithoutRandomness(testChromosomes);
    assertEquals(5, instance.getChromosom(4));
    instance.reduceRandomChromosom();
    assertEquals(4, instance.getChromosom(4));
  }

  @Test
  public void testIncreaseRandomChromosom() {
    instance = new GenomWithoutRandomness(testChromosomes);
    assertEquals(5, instance.getChromosom(4));
    instance.increaseRandomChromosom();
    assertEquals(6, instance.getChromosom(4));

    instance.setChromosom(4, 155);
    instance.increaseRandomChromosom();
    assertTrue(instance.isValid());
  }

  @Test
  public void testSwitchRandomChromosomes() {
    instance = new GenomWithoutRandomness(testChromosomes);
    instance.switchRandomChromosomes();
    int[] expected = new int[] {1,2,3,4,6,5,7,8,9};
    assertTrue(Arrays.equals(expected, instance.getChromosomes()));
  }

  private static class GenomWithoutRandomness extends Genom {

    private int[] randomList = new int[] {4, 5};
    private int position = 0;

    public GenomWithoutRandomness() {
      super();
    }

    public GenomWithoutRandomness(int[] chromosomes) {
      super(chromosomes);
    }

    @Override
    protected int getRandomChromosomPosition() {
      int result = randomList[position];
      position = (position + 1) % randomList.length;
      return result;
    }
  }
}
