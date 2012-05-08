package de.tle.dso.units.util;

import de.tle.dso.sim.battle.InvalidArmyException;
import de.tle.dso.units.Army;
import de.tle.dso.units.Unit;
import de.tle.dso.units.sort.SortByPrioComparator;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.reflections.Reflections;

public class UnitPatternHelper {

  /*
   * Dieser Block dient nur dazu, alle verfügbaren Units (also alle Klasse, die
   * von Unit erben) zu finden
   */
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
   * Armeen sind immer nach dem Muster <Anzahl> <Kürzel> <Anzahl> <Kürzel> mit
   * oder ohne Komma aufgebaut. Also z.b. 32 R, 15C für 32 Rekruten und 15
   * Reiterei
   */
  private final Pattern syntaxPattern = Pattern.compile("\\s*?(\\d+)\\s*?([A-Z]+)\\s*?,?\\s*?");
  private final String originalInputString;
  private final Matcher matcher;

  public static Army createArmyFromPattern(String inputString) {
    return createArmyFromPattern(inputString, false);
  }

  public static Army createArmyFromPattern(String inputString, boolean useLargeArmy) {
    UnitPatternHelper helper = new UnitPatternHelper(inputString);
    return helper.createArmy(true);
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
    matcher = syntaxPattern.matcher(originalInputString);
    if (!matcher.lookingAt()) {
      throw new IllegalArgumentException("Konnte Armee Pattern nicht matchen: " + originalInputString);
    }
    matcher.reset();
  }

  public Army createArmy(boolean useLargeArmy) {
    int size = useLargeArmy ? Army.VETERAN_MAX_SIZE : Army.MAX_SIZE;
    Army result = new Army(size);
    while (matcher.find()) {
      String countString = matcher.group(1);
      String unitString = matcher.group(2);

      fillInUnits(countString, unitString, result);
    }

    validate(result);
    return result;
  }

  private void fillInUnits(String countString, String unitString, Army result) throws IllegalArgumentException {
    int count = toInt(countString);

    Class<? extends Unit> unitClass = unitsByShortName.get(unitString);
    for (int i = 0; i < count; i++) {
      try {
        Unit unit = unitClass.newInstance();
        result.add(unit);
      } catch (Exception ex) {
        throw new IllegalArgumentException("Error while creating army from pattern: " + originalInputString + ". " + ex.toString());
      }
    }
  }

  private int toInt(String countString) {
    try {
      return Integer.parseInt(countString);
    } catch (Exception ex) {
      throw new IllegalArgumentException("Konnte Unit Anzahl nicht parsen: " + countString);
    }
  }

  private static void removeLastComma(StringBuilder result) {
    if (result.length() > 2) {
      result.delete(result.length() - 2, result.length());
    }
  }

  private void validate(Army result) {
    if (!result.isValid()) {
      throw new IllegalArgumentException("Pattern gab ungültige Armee: " + originalInputString);
    }
  }
}
