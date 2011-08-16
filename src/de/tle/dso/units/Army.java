package de.tle.dso.units;

import de.tle.dso.units.player.General;
import de.tle.dso.units.sort.HeapSort;
import de.tle.dso.units.sort.SortByMaxHitPointsAscThenPrioComparator;
import de.tle.dso.units.sort.SortByPrioComparator;
import de.tle.dso.units.util.UnitPatternHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Army {

  private List<Unit> units = new ArrayList<Unit>(201);

  public void addGeneral() {
    units.add(new General());
  }

  public void add(Unit unit) {
    units.add(unit);
  }

  public boolean containsGeneral() {
    for (Unit unit : units) {
      if (unit instanceof General) {
        return true;
      }
    }

    return false;
  }

  public boolean isValid() {
    return units.size() > 0 && (containsGeneral() ? units.size() <= 201 : units.size() <= 200);
  }

  public int size() {
    return units.size();
  }

  public List<Unit> getUnits() {
    return units;
  }

  public List<Unit> getUnitsByPrio() {
    // Collections.sort(sortedList, new SortByPrioComparator());
    Unit[] tmpArray = units.toArray(new Unit[] {});
    HeapSort.heapsort(tmpArray, new SortByPrioComparator());
    List<Unit> sortedList = Arrays.asList(tmpArray);
    return sortedList;
  }

  public List<Unit> getUnitsByHitpoints() {
    Unit[] tmpArray = units.toArray(new Unit[] {});
    HeapSort.heapsort(tmpArray, new SortByMaxHitPointsAscThenPrioComparator());
    List<Unit> sortedList = Arrays.asList(tmpArray);
    return sortedList;
  }

  public List<Unit> removeDeadUnits() {
    List<Unit> deadUnits = new ArrayList<Unit>(201);
    List<Unit> aliveUnits = new ArrayList<Unit>(201);

    for (Unit unit : units) {
      if (unit.isDead()) {
        deadUnits.add(unit);
      } else {
        aliveUnits.add(unit);
      }
    }

    this.units = aliveUnits;
    return deadUnits;
  }

  public int getNumberOf(String unitShortName) {
    int result = 0;

    for (Unit unit : units) {
      if (unit.getShortName().equalsIgnoreCase(unitShortName)) {
        result++;
      }
    }

    return result;
  }

  @Override
  public String toString() {
    return UnitPatternHelper.createPatternFromArmy(this);
  }
}