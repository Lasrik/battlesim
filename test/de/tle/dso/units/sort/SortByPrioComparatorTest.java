package de.tle.dso.units.sort;

import de.tle.dso.units.Army;
import de.tle.dso.units.Unit;
import de.tle.dso.units.computer.Plünderer;
import de.tle.dso.units.computer.Raufbold;
import de.tle.dso.units.computer.Schläger;
import de.tle.dso.units.computer.Steinwerfer;
import de.tle.dso.units.computer.Wachhund;
import de.tle.dso.units.computer.Waldläufer;
import de.tle.dso.units.computer.boss.Bert;
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
import de.tle.dso.units.util.UnitPatternHelper;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class SortByPrioComparatorTest {

  @Test
  public void testCompare() {
    Army army = UnitPatternHelper.createArmyFromPattern("1 M, 1 R, 1 S, 1 LB, 1 A, 1 K, 1G, 1 E, 1 B, 1 C");
    List<Unit> units = army.getUnits();
    Collections.sort(units, new SortByPrioComparator());
    assertTrue(units.get(0) instanceof Rekrut);
    assertTrue(units.get(1) instanceof Miliz);
    assertTrue(units.get(2) instanceof Reiterei);
    assertTrue(units.get(3) instanceof Soldat);
    assertTrue(units.get(4) instanceof Elitesoldat);
    assertTrue(units.get(5) instanceof Bogenschütze);
    assertTrue(units.get(6) instanceof Langbogenschütze);
    assertTrue(units.get(7) instanceof Armbrustschütze);
    assertTrue(units.get(8) instanceof Kanonier);
    assertTrue(units.get(9) instanceof General);
  }

  @Test
  public void testCompareComputerUnits() {
    Army army = UnitPatternHelper.createArmyFromPattern("1 WH, 1 SW, 1 RB, 1 WL, 1 PL, 1 SL");
    List<Unit> units = army.getUnits();
    Collections.sort(units, new SortByPrioComparator());
    assertTrue(units.get(0) instanceof Plünderer);
    assertTrue(units.get(1) instanceof Schläger);
    assertTrue(units.get(2) instanceof Wachhund);
    assertTrue(units.get(3) instanceof Raufbold);
    assertTrue(units.get(4) instanceof Steinwerfer);
    assertTrue(units.get(5) instanceof Waldläufer);

  }

  @Test
  public void test2() {
    Army army = UnitPatternHelper.createArmyFromPattern("2 M, 3 R, 1G, 1 C");
    List<Unit> units = army.getUnits();
    Collections.sort(units, new SortByPrioComparator());
    assertTrue(units.get(0) instanceof Rekrut);
    assertTrue(units.get(1) instanceof Rekrut);
    assertTrue(units.get(2) instanceof Rekrut);
    assertTrue(units.get(3) instanceof Miliz);
    assertTrue(units.get(4) instanceof Miliz);
    assertTrue(units.get(5) instanceof Reiterei);
    assertTrue(units.get(6) instanceof General);
  }

  @Test
  public void test3() {
    List<Unit> units = new LinkedList<Unit>();
    units.add(new General());
    units.add(new Miliz());
    units.add(new Rekrut());
    units.add(new Rekrut());
    units.get(3).reduceHitpoints(3);

    Collections.sort(units, new SortByPrioComparator());

    assertEquals(37, units.get(0).getCurrentHitPoints());
    assertEquals(40, units.get(1).getCurrentHitPoints());
  }

  @Test
  public void test4() {
    Army army = UnitPatternHelper.createArmyFromPattern("199 WH, 1 DWW");
    List<Unit> units = army.getUnits();
    Collections.sort(units, new SortByPrioComparator());

    assertTrue(units.get(0) instanceof Waltraud);
    assertTrue(units.get(1) instanceof Wachhund);
  }

    @Test
  public void testCompareComputerUnits2() {
    Army army = UnitPatternHelper.createArmyFromPattern("1 EB, 1 WH, 1 SW, 1 RB, 1 WL, 1 PL, 1 SL");
    List<Unit> units = army.getUnits();
    Collections.sort(units, new SortByPrioComparator());
    assertTrue(units.get(0) instanceof Plünderer);
    assertTrue(units.get(1) instanceof Schläger);
    assertTrue(units.get(2) instanceof Wachhund);
    assertTrue(units.get(3) instanceof Raufbold);
    assertTrue(units.get(4) instanceof Steinwerfer);
    assertTrue(units.get(5) instanceof Waldläufer);
    assertTrue(units.get(6) instanceof Bert);

  }

}
