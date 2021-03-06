package de.tle.dso.sim.battle;

import de.tle.dso.units.Army;
import de.tle.dso.units.Initiative;
import de.tle.dso.units.Unit;
import de.tle.dso.units.util.UnitPatternHelper;
import java.util.List;
import org.apache.log4j.Logger;

public class Battle {

  protected Army attackingArmy;
  protected Army defendingArmy;
  protected BattleResult battleResult;
  private final static Logger LOG = Logger.getLogger(Battle.class);

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
    LOG.trace("Kampf beginnt.");

    int roundNumber = 0;
    while (!endBattle()) {
      LOG.trace("Runde " + ++roundNumber);

      for (Initiative phase : Initiative.values()) {
        LOG.trace("Phase " + phase);

        attackersWave(phase);
        defendersFightBack(phase);

        collectLosses();
      }
    }

    LOG.trace("Kampf ende.");
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
      throw new InvalidArmyException("Player Army is null or invalid: " + attackingArmy.toString());
    }
    if (!attackingArmy.containsGeneral()) {
      throw new InvalidArmyException("Player army must contain a General");
    }
  }

  private void logArmies() {
    LOG.trace("Angreifer: " + attackingArmy.toString() + " ---- Verteidiger: " + defendingArmy.toString());
  }

  private void logBattleOutcome() {
    if (LOG.isDebugEnabled()) {
      LOG.debug("+++++++++++++++++++++++++++++++++++++++++++++++++");
      LOG.debug("Verluste Spieler: " + UnitPatternHelper.createPatternFromArmy(battleResult.playerLosses));
      LOG.debug("Verluste Computer: " + UnitPatternHelper.createPatternFromArmy(battleResult.computerLosses));
      LOG.debug(battleResult.battleWon ? "GEWONNEN" : "VERLOREN");
      LOG.debug("Resourcen: " + battleResult.getResourceCosts());
      LOG.debug("+++++++++++++++++++++++++++++++++++++++++++++++++");
    }
  }
}