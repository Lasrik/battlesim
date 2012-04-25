package de.tle.dso.resources;

import static de.tle.dso.resources.Resource.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ResourceCostTest {

  @Before
  public void setUp() {
  }

  @Test
  public void testBuild() {
    ResourceCost cost = ResourceCost.build(BEER, 1, SETTLER, 1, BRONCE_SWORD, 1);
    int expectedNumber = BEER.weight + SETTLER.weight + BRONCE_SWORD.weight;
    int actualCost = cost.totalWeightPoints();
    assertEquals(expectedNumber, actualCost);
  }

  @Test
  public void testBuild2() {
    ResourceCost cost = ResourceCost.build(BEER, 10, HORSE, 8, BRONCE_SWORD, 1);
    int expectedNumber = BEER.weight * 10 + HORSE.weight * 8 + BRONCE_SWORD.weight * 1;
    int actualCost = cost.totalWeightPoints();
    assertEquals(expectedNumber, actualCost);
  }

  @Test
  public void testNone() {
    ResourceCost noCosts = ResourceCost.NONE;
    assertEquals(0, noCosts.totalWeightPoints());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testNoneCannotAdd() {
    ResourceCost noCosts = ResourceCost.NONE;
    ResourceCost otherCosts = ResourceCost.build(BEER, 10, HORSE, 8, BRONCE_SWORD, 1);

    noCosts.add(otherCosts);
  }

  @Test
  public void testNoneIsSmallerThanAll() {
    ResourceCost noCosts = ResourceCost.NONE;
    ResourceCost otherCosts = ResourceCost.build(BEER, 1, SETTLER, 1, BRONCE_SWORD, 1);
    assertTrue(noCosts.compareTo(otherCosts) < 0);
    assertTrue(noCosts.lesserThan(otherCosts));
  }

  @Test
  public void testCompare() {
    ResourceCost cost1 = ResourceCost.build(SETTLER, 1, BEER, 1, BRONCE_SWORD, 1);
    ResourceCost cost2 = ResourceCost.build(SETTLER, 1, BEER, 1, BRONCE_SWORD, 2);
    ResourceCost cost3 = ResourceCost.build(SETTLER, 1, BEER, 1, BRONCE_SWORD, 1);

    assertTrue(cost1.compareTo(cost2) < 0);
    assertTrue(cost2.compareTo(cost1) > 0);
    assertTrue(cost1.compareTo(cost3) == 0);
  }

  @Test
  public void testLesserGreaterThan() {
    ResourceCost cost1 = ResourceCost.build(SETTLER, 1, BEER, 1, BRONCE_SWORD, 1);
    ResourceCost cost2 = ResourceCost.build(SETTLER, 1, BEER, 1, BRONCE_SWORD, 2);
    ResourceCost cost3 = ResourceCost.build(SETTLER, 1, BEER, 1, BRONCE_SWORD, 1);

    assertTrue(cost1.lesserThan(cost2));
    assertTrue(cost2.greaterThan(cost1));
    assertTrue(cost1.equals(cost3));

    assertFalse(cost1.lesserThan(cost3));
    assertFalse(cost1.greaterThan(cost3));
  }
}
