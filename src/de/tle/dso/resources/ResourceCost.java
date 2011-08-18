package de.tle.dso.resources;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResourceCost {

  protected Map<Resource, Integer> usedResources;

  protected ResourceCost(Map<Resource, Integer> usedResources) {
    this.usedResources = usedResources;
  }

  public static ResourceCost build(final Resource r1, final int c1, final Resource r2, final int c2, final Resource r3, final int c3) {
    return new ResourceCost(new HashMap<Resource, Integer>() {

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
}