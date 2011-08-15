package de.tle.dso.units;

import de.tle.dso.units.player.General;
import de.tle.dso.units.sort.SortByMaxHitPointsAscThenPrioComparator;
import de.tle.dso.units.sort.SortByPrioComparator;
import de.tle.dso.units.util.UnitPatternHelper;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Army {

  private LinkedList<Unit> units = new LinkedList<Unit>();

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

  public LinkedList<Unit> getUnits() {
    return units;
  }

  public List<Unit> getUnitsByPrio() {
    List<Unit> sortedList = new LinkedList<Unit>(units);
    Collections.sort(sortedList, new SortByPrioComparator());
    return sortedList;
  }

  public List<Unit> getUnitsByHitpoints() {
    List<Unit> sortedList = new LinkedList<Unit>(units);
    Collections.sort(sortedList, new SortByMaxHitPointsAscThenPrioComparator());
    return sortedList;
  }

  public List<Unit> removeDeadUnits() {
    LinkedList<Unit> deadUnits = new LinkedList<Unit>();
    LinkedList<Unit> aliveUnits = new LinkedList<Unit>();

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