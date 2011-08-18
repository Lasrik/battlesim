package de.tle.dso.resources;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ResourceCostTest {

  @Before
  public void setUp() {
  }

  @Test
  public void testBuild() {
    ResourceCost cost = ResourceCost.build(Resource.BEER, 1, Resource.SETTLER, 1, Resource.BRONCE_SWORD, 1);
    int expectedNumber = Resource.BEER.weight + Resource.SETTLER.weight + Resource.BRONCE_SWORD.weight;
    int actualCost = cost.totalWeightPoints();
    assertEquals(expectedNumber, actualCost);
  }

  @Test
  public void testBuild2() {
    ResourceCost cost = ResourceCost.build(Resource.BEER, 10, Resource.HORSE, 8, Resource.BRONCE_SWORD, 1);
    int expectedNumber = Resource.BEER.weight * 10 + Resource.HORSE.weight * 8 + Resource.BRONCE_SWORD.weight * 1;
    int actualCost = cost.totalWeightPoints();
    assertEquals(expectedNumber, actualCost);
  }

  @Test
  public void testNone() {
    ResourceCost noCosts = ResourceCost.none();
    assertEquals(0, noCosts.totalWeightPoints());
  }
}
