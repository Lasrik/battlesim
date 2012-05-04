package de.tle.dso.units;

import de.tle.dso.units.player.General;
import de.tle.dso.units.sort.SortByMaxHitPointsAscThenPrioComparator;
import de.tle.dso.units.sort.SortByPrioComparator;
import de.tle.dso.units.util.UnitPatternHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Army {

  public static int MAX_SIZE = 200;
  public static int VETERAN_MAX_SIZE = 250;

  private List<Unit> units;
  private int maxSize;


  public Army() {
    this(MAX_SIZE);
  }

  public Army(int maxSize) {
    this.maxSize = maxSize;
    units = new ArrayList<Unit>(this.maxSize);
  }

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

  public int getMaxUnits() {
    return containsGeneral() ? maxSize + 1 : maxSize;
  }

  public boolean isValid() {
    return units.size() > 0 && units.size() <= getMaxUnits();
  }

  public int size() {
    return units.size();
  }

  public List<Unit> getUnits() {
    return units;
  }

  public List<Unit> getUnitsByPrio() {
    return sort(new SortByPrioComparator());
  }

  public List<Unit> getUnitsByHitpoints() {
    return sort(new SortByMaxHitPointsAscThenPrioComparator());
  }

  public List<Unit> removeDeadUnits() {
    List<Unit> deadUnits = new ArrayList<Unit>(this.maxSize);
    List<Unit> aliveUnits = new ArrayList<Unit>(this.maxSize);

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

  private List<Unit> sort(Comparator<Unit> comparator) {
    List<Unit> sortedList = new ArrayList<Unit>(units);
    Collections.sort(sortedList, comparator);
    return sortedList;
  }
}