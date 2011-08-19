package de.tle.dso.resources;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

public class ResourceCost implements Comparable<ResourceCost> {

  protected Map<Resource, Integer> usedResources;

  public ResourceCost(Map<Resource, Integer> usedResources) {
    this.usedResources = usedResources;
  }

  public static ResourceCost build(final Resource r1, final int c1, final Resource r2, final int c2, final Resource r3, final int c3) {
    return new ResourceCost(new EnumMap<Resource, Integer>(Resource.class) {

      {
        put(r1, c1);
        put(r2, c2);
        put(r3, c3);
      }
    });
  }

  public static ResourceCost none() {
    return new ResourceCost(Collections.EMPTY_MAP);
  }

  public int totalWeightPoints() {
    int result = 0;

    for (Resource res : usedResources.keySet()) {
      result += res.weight * usedResources.get(res);
    }

    return result;
  }

  public void add(ResourceCost anotherCost) {
    for (Resource res : anotherCost.usedResources.keySet()) {
      int count = anotherCost.usedResources.get(res);
      if (this.usedResources.containsKey(res)) {
        count += this.usedResources.get(res);
      }
      this.usedResources.put(res, count);
    }
  }

  @Override
  public int compareTo(ResourceCost o) {
    return this.totalWeightPoints() - o.totalWeightPoints();
  }

  @Override
  public String toString() {
    return new Formatter().format("%s -> %s", usedResources, totalWeightPoints()).out().toString();
  }
}