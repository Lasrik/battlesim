package de.tle.dso.sim.battle;

import de.tle.dso.units.Army;
import de.tle.dso.units.Initiative;
import de.tle.dso.units.Unit;
import de.tle.dso.units.util.UnitPatternHelper;
import java.util.List;

public class Battle {

  protected Army attackingArmy;
  protected Army defendingArmy;
  protected BattleResult battleResult;

  public Battle(Army attacker, Army defender) {
    this.attackingArmy = attacker;
    this.defendingArmy = defender;
    this.battleResult = new BattleResult();
  }

  public BattleResult simulateBattle() throws InvalidArmyException {
    validateArmies();
    logArmies();
    startBattle();
    calculateBattleOutcome();
    logBattleOutcome();
    return battleResult;
  }

  private void startBattle() {
//    System.out.println("Kampf beginnt.");

    int roundNumber = 0;
    while (!endBattle()) {
//      System.out.printf("\nRunde %s\n", ++roundNumber);

      for (Initiative phase : Initiative.values()) {
//        System.out.printf("Phase %s\n", new Object[]{phase});

        attackersWave(phase);
        defendersFightBack(phase);

        collectLosses();
      }
    }

//    System.out.println("Kampf ende.\n");
  }

  private void defendersFightBack(Initiative phase) {
    BattleWave wave = new BattleWave(defendingArmy, attackingArmy, phase);
    wave.fight();
  }

  private void attackersWave(Initiative phase) {
    BattleWave wave = new BattleWave(attackingArmy, defendingArmy, phase);
    wave.fight();
  }

  private void collectLosses() {
    List<Unit> attackerLosses = attackingArmy.removeDeadUnits();
    battleResult.addDeadPlayerUnits(attackerLosses);

    List<Unit> defenderLosses = defendingArmy.removeDeadUnits();
    battleResult.addDeadComputerUnits(defenderLosses);
  }

  private void calculateBattleOutcome() {
    if (defendingArmy.size() == 0) {
      battleResult.setBattleWon(true);
    } else {
      battleResult.setBattleWon(false);
    }
  }

  private boolean endBattle() {
    return attackingArmy.size() == 0 || defendingArmy.size() == 0;
  }

  private void validateArmies() throws InvalidArmyException {
    validatePlayerArmy();
    validateComputerArmy();
  }

  private void validateComputerArmy() throws InvalidArmyException {
    if (defendingArmy == null || !defendingArmy.isValid()) {
      throw new InvalidArmyException("Computer Army is empty");
    }
  }

  private void validatePlayerArmy() throws InvalidArmyException {
    if (attackingArmy == null || !attackingArmy.isValid()) {
      throw new InvalidArmyException("Player Army is null");
    }
    if (!attackingArmy.containsGeneral()) {
      throw new InvalidArmyException("Player army must contain a General");
    }
  }

  private void logArmies() {
//    System.out.printf("Angreifer: %s ---- Verteidiger: %s\n", new Object[]{attackingArmy.toString(), defendingArmy.toString()});
  }

  private void logBattleOutcome() {
//    System.out.printf("Verluste Spieler: %s\n", UnitPatternHelper.createPatternFromMap(battleResult.playerLosses));
//    System.out.printf("Verluste Computer: %s\n", UnitPatternHelper.createPatternFromMap(battleResult.computerLosses));
//    System.out.println(battleResult.battleWon ? "GEWONNEN" : "VERLOREN");
//    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
//    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
  }
}
