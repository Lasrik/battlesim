package de.tle.dso.units.sort;

import de.tle.dso.units.Unit;
import java.util.Comparator;
import static de.tle.dso.units.SpecialDefense.*;

public class SortByPrioComparator implements Comparator<Unit> {

  @Override
  public int compare(Unit unit1, Unit unit2) {
    if (unit1.getSpecialDefense() == ALWAYS_LAST || unit2.getSpecialDefense() == ALYWAYS_FIRST) {
      return 1;
    }

    if (unit1.getSpecialDefense() == ALYWAYS_FIRST || unit2.getSpecialDefense() == ALWAYS_LAST) {
      return -1;
    }

    int prio1 = unit1.getPriority();
    int prio2 = unit2.getPriority();

    if (prio1 == prio2) {
      return unit1.getCurrentHitPoints() - unit2.getCurrentHitPoints();
    }

    return prio1 - prio2;
  }
}
