package de.tle.dso.units.util;

import de.tle.dso.units.Army;
import de.tle.dso.units.player.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class UnitPatternHelperTest {

  @Test
  public void testCreateArmy() {
    Army army = UnitPatternHelper.createArmyFromPattern("1 G");
    assertNotNull(army);
    assertTrue(army.isValid());
    assertTrue(army.containsGeneral());
  }

  @Test(expected=NullPointerException.class)
  public void testNull() {
    UnitPatternHelper.createArmyFromPattern(null);
  }

  @Test(expected=IllegalArgumentException.class)
  public void testInvalidInput() {
    UnitPatternHelper.createArmyFromPattern("");
  }

  @Test
  public void testWhitespaceSafety() {
    Army army = UnitPatternHelper.createArmyFromPattern("            1              G           ");
    assertTrue(army.isValid());
    assertTrue(army.containsGeneral());
    assertEquals(1, army.getUnitsByPrio().size());
  }

  @Test
  public void testWithMoreUnits() {
    Army army = UnitPatternHelper.createArmyFromPattern("1 G,    1 R, 2M, 1 E");

    assertTrue(army.isValid());
    assertEquals(5, army.size());

    assertTrue(army.getUnitsByPrio().get(0) instanceof Rekrut);
    assertTrue(army.getUnitsByPrio().get(1) instanceof Miliz);
    assertTrue(army.getUnitsByPrio().get(2) instanceof Miliz);
    assertTrue(army.getUnitsByPrio().get(3) instanceof Elitesoldat);
    assertTrue(army.getUnitsByPrio().get(4) instanceof General);
  }

  @Test
  public void testGeneratePatternFromArmy() {
    Army army = new Army();
    army.add(new General());
    army.add(new Rekrut());
    army.add(new Rekrut());
    army.add(new Rekrut());
    army.add(new Miliz());
    army.add(new Miliz());
    army.add(new Rekrut());
    army.add(new Soldat());
    army.add(new Soldat());
    army.add(new Elitesoldat());
    army.add(new Soldat());

    String pattern = UnitPatternHelper.createPatternFromArmy(army);
    assertEquals("4 R, 2 M, 3 S, 1 E, 1 G", pattern);
  }

  @Test
  public void testGeneratePatternFromEmpty() {
    Army army = new Army();
    String pattern = UnitPatternHelper.createPatternFromArmy(army);
    assertEquals("", pattern);
  }
  
  @Test
  public void testCase1() {
    String testPattern = "1CK, 50WH, 49WL, 100RB";
    
    Army army = UnitPatternHelper.createArmyFromPattern(testPattern);
    
    assertEquals(200, army.size());
  }
}
