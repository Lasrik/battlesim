package de.tle.dso.units.util;

import de.tle.dso.units.Army;
import de.tle.dso.units.Unit;
import de.tle.dso.units.sort.SortByPrioComparator;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.reflections.Reflections;

public class UnitPatternHelper {

  public final static Map<String, Class<? extends Unit>> unitsByShortName = new HashMap<String, Class<? extends Unit>>();
  private final static Reflections reflections = new Reflections("de.tle.dso");

  static {
    Set<Class<? extends Unit>> subTypes = reflections.getSubTypesOf(Unit.class);
    for (Class<? extends Unit> unitClass : subTypes) {
      if (Modifier.isAbstract(unitClass.getModifiers())) {
        continue;
      }
      try {
        Unit unit = unitClass.newInstance();
        unitsByShortName.put(unit.getShortName(), unitClass);
      } catch (Exception ex) {
        Logger.getLogger(UnitPatternHelper.class).info("Konnte Unit nicht instantiieren: ", ex);
      }
    }
  }

  /*
   * Armeen sind immer nach dem Muster <Anzahl> <Kürzel>, <Anzahl> <Kürzel>
   * aufgebaut. Also z.b. 32 R, 15C für 32 Rekruten und 15 Reiterei
   */
  private final Pattern syntaxPattern = Pattern.compile("\\s*(\\d+)\\s*([A-Z]+)");
  private String originalInputString;
  private String[] splitPatterns;

  public static Army createArmyFromPattern(String inputString) {
    UnitPatternHelper helper = new UnitPatternHelper(inputString);
    return helper.createArmy();
  }

  public static String createPatternFromArmy(Army army) {
    StringBuilder result = new StringBuilder();

    List<Unit> units = army.getUnitsByPrio();

    String lastUnitsShortName = "";
    int currentCount = 0;
    for (Unit unit : units) {
      if (unit.getShortName().equals(lastUnitsShortName)) {
        currentCount++;
      } else {
        if (currentCount > 0) {
          result.append(currentCount);
          result.append(" ");
          result.append(lastUnitsShortName);
          result.append(", ");
        }
        currentCount = 1;
        lastUnitsShortName = unit.getShortName();
      }
    }

    if (currentCount > 0) {
      result.append(currentCount);
      result.append(" ");
      result.append(lastUnitsShortName);
      result.append(", ");
    }
    removeLastComma(result);

    return result.toString();
  }

  private UnitPatternHelper(String pattern) {
    this.originalInputString = pattern.toUpperCase();
    splitPattern();
  }

  public Army createArmy() {
    Army result = new Army();

    for (String singlePattern : splitPatterns) {
      MatchingResult matchingResult = match(singlePattern);
      fillInUnitsForPattern(matchingResult, result);
    }

    return result;
  }

  private void fillInUnitsForPattern(MatchingResult matchingResult, Army result) throws IllegalArgumentException {
    for (int i = 0; i < matchingResult.count; i++) {
      try {
        Unit unit = matchingResult.unitClass.newInstance();
        result.add(unit);
      } catch (Exception ex) {
        throw new IllegalArgumentException("Error while creating army from pattern: " + originalInputString + ". " + ex.toString());
      }
    }
  }

  private void splitPattern() throws IllegalArgumentException {
    this.splitPatterns = originalInputString.split(",");
    if (splitPatterns.length == 0) {
      throw new IllegalArgumentException("Invalid pattern: " + originalInputString);
    }
  }

  private MatchingResult match(String troopPattern) {
    MatchingResult result = new MatchingResult();
    Matcher matcher = syntaxPattern.matcher(troopPattern);
    if (matcher.lookingAt()) {
      String countString = matcher.group(1);
      String unitString = matcher.group(2);

      result.count = Integer.parseInt(countString);
      if (result.count < 1 || result.count > 200) {
        throw new IllegalArgumentException("Count must be between 1 and 200: " + troopPattern);
      }

      result.unitClass = unitsByShortName.get(unitString);
      if (result.unitClass == null) {
        throw new IllegalArgumentException("No matching unit class found: " + unitString);
      }
    } else {
      throw new IllegalArgumentException("Illegal pattern: " + originalInputString);
    }

    return result;
  }

  private static Unit instantiate(Class<? extends Unit> unitClazz) {
    try {
      return unitClazz.newInstance();
    } catch (Exception ex) {
      return null;
    }
  }

  private static List<Unit> makeSortedListOfUnits(Map<Class<? extends Unit>, Integer> map) {
    List<Unit> result = new LinkedList<Unit>();
    for (Class<? extends Unit> unitClass : map.keySet()) {
      Unit unit = instantiate(unitClass);
      result.add(unit);
    }
    Collections.sort(result, new SortByPrioComparator());
    return result;
  }

  private static void removeLastComma(StringBuilder result) {
    if (result.length() > 2) {
      result.delete(result.length() - 2, result.length());
    }
  }

  private static class MatchingResult {

    public int count;
    public Class<? extends Unit> unitClass;
  }
}
