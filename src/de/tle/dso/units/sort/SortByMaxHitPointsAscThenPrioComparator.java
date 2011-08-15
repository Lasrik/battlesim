package de.tle.dso.units.sort;

import static de.tle.dso.units.SpecialDefense.*;
import de.tle.dso.units.Unit;
import java.util.Comparator;

public class SortByMaxHitPointsAscThenPrioComparator implements Comparator<Unit> {

  @Override
  public int compare(Unit unit1, Unit unit2) {
    if (unit1.getSpecialDefense() == ALWAYS_LAST) {
      return 1;
    }

    if (unit2.getSpecialDefense() == ALWAYS_LAST) {
      return -1;
    }

    int hp1 = unit1.getMaxHitPoints();
    int hp2 = unit2.getMaxHitPoints();

    if (hp1 == hp2) {
      int prio1 = unit1.getPriority();
      int prio2 = unit2.getPriority();

      if (prio1 == prio2) {
        return unit1.getCurrentHitPoints() - unit2.getCurrentHitPoints();
      }

      return prio1 - prio2;
    }

    return hp1 - hp2;
  }
}
