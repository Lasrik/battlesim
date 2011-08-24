package de.tle.dso.units.util;

import de.tle.dso.units.Army;
import de.tle.dso.units.Unit;
import de.tle.dso.units.computer.Plünderer;
import de.tle.dso.units.computer.Raufbold;
import de.tle.dso.units.computer.Schläger;
import de.tle.dso.units.computer.Steinwerfer;
import de.tle.dso.units.computer.Wachhund;
import de.tle.dso.units.computer.Waldläufer;
import de.tle.dso.units.computer.boss.Bert;
import de.tle.dso.units.computer.boss.Chuck;
import de.tle.dso.units.computer.boss.Metallgebiss;
import de.tle.dso.units.computer.boss.Stinktier;
import de.tle.dso.units.computer.boss.Waltraud;
import de.tle.dso.units.player.Armbrustschütze;
import de.tle.dso.units.player.Bogenschütze;
import de.tle.dso.units.player.Elitesoldat;
import de.tle.dso.units.player.General;
import de.tle.dso.units.player.Kanonier;
import de.tle.dso.units.player.Langbogenschütze;
import de.tle.dso.units.player.Miliz;
import de.tle.dso.units.player.Reiterei;
import de.tle.dso.units.player.Rekrut;
import de.tle.dso.units.player.Soldat;
import de.tle.dso.units.sort.SortByPrioComparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnitPatternHelper {

  public final static Map<String, Class<? extends Unit>> unitsByShortName = new HashMap<String, Class<? extends Unit>>();

  static {
    // Player
    unitsByShortName.put("R", Rekrut.class);
    unitsByShortName.put("M", Miliz.class);
    unitsByShortName.put("C", Reiterei.class);
    unitsByShortName.put("S", Soldat.class);
    unitsByShortName.put("E", Elitesoldat.class);
    unitsByShortName.put("B", Bogenschütze.class);
    unitsByShortName.put("LB", Langbogenschütze.class);
    unitsByShortName.put("A", Armbrustschütze.class);
    unitsByShortName.put("K", Kanonier.class);
    unitsByShortName.put("G", General.class);

    // Computer
    unitsByShortName.put("PL", Plünderer.class);
    unitsByShortName.put("SL", Schläger.class);
    unitsByShortName.put("WH", Wachhund.class);
    unitsByShortName.put("RB", Raufbold.class);
    unitsByShortName.put("SW", Steinwerfer.class);
    unitsByShortName.put("WL", Waldläufer.class);

    // Bosses
    unitsByShortName.put("ST", Stinktier.class);
    unitsByShortName.put("CK", Chuck.class);
    unitsByShortName.put("EB", Bert.class);
    unitsByShortName.put("MG", Metallgebiss.class);
    unitsByShortName.put("DWW", Waltraud.class);
  }
  private final Pattern syntaxPattern = Pattern.compile("\\s*(\\d+)\\s*([A-Z]+)");
  private String originalInputString;
  private String[] splitPatterns;

  public static Army createArmyFromPattern(String inputString) {
    UnitPatternHelper helper = new UnitPatternHelper(inputString);
    return helper.createArmy();
  }

  public static String createPatternFromMap(Map<Class<? extends Unit>, Integer> map) {
    StringBuilder result = new StringBuilder();
    List<Unit> appearingUnitsSortedByPrio = makeSortedListOfUnits(map);
    for (Unit unit : appearingUnitsSortedByPrio) {
      int count = map.get(unit.getClass());
      result.append(count);
      result.append(" ");
      result.append(unit.getShortName());
      result.append(", ");
    }
    removeLastComma(result);


    return result.toString();
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

  public static String createPatternFromArray(int[] array) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < array.length; i++) {
      if (array[i] > 0) {
        sb.append(array[i]);
        sb.append(" ");

        switch (i) {
          case 0:
            sb.append("R");
            break;
          case 1:
            sb.append("M");
            break;
          case 2:
            sb.append("C");
            break;
          case 3:
            sb.append("S");
            break;
          case 4:
            sb.append("E");
            break;
          case 5:
            sb.append("B");
            break;
          case 6:
            sb.append("LB");
            break;
          case 7:
            sb.append("A");
            break;
          case 8:
            sb.append("K");
            break;
        }
        sb.append(", ");
      }
    }

    sb.append("1 G");
    return sb.toString();
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
