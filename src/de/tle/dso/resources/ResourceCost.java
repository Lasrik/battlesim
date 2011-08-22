package de.tle.dso.resources;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

public class ResourceCost implements Comparable<ResourceCost> {

  protected Map<Resource, Integer> usedResources;
  public final static ResourceCost NONE = new ResourceCost(Collections.EMPTY_MAP);

  protected ResourceCost(Map<Resource, Integer> usedResources) {
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

  public static ResourceCost buildEmpty() {
    return new ResourceCost(new EnumMap<Resource, Integer>(Resource.class));
  }

  public int totalWeightPoints() {
    int result = 0;

    for (Resource res : usedResources.keySet()) {
      result += res.weight * usedResources.get(res);
    }

    return result;
  }

  public void add(ResourceCost anotherCost) {
    if (this == NONE) {
      throw new UnsupportedOperationException("Cannot add resources to NONE.");
    }
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

  public boolean greaterThan(ResourceCost other) {
    return this.compareTo(other) > 0;
  }

  public boolean lesserThan(ResourceCost other) {
    return this.compareTo(other) < 0;
  }

  public int get(Resource res) {
    return usedResources.get(res);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ResourceCost other = (ResourceCost) obj;
    return this.compareTo(other) == 0;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 59 * hash + this.totalWeightPoints();
    return hash;
  }

  @Override
  public String toString() {
    if (this == NONE) {
      return "NONE";
    }
    return new Formatter().format("%s -> %s", usedResources, totalWeightPoints()).out().toString();
  }
}