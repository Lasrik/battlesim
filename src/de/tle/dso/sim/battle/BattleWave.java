package de.tle.dso.sim.battle;

import static de.tle.dso.units.SpecialAttack.*;
import de.tle.dso.units.Army;
import de.tle.dso.units.Initiative;
import de.tle.dso.units.Unit;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BattleWave {

  private Army attackingArmy;
  private Army defendingArmy;
  private Initiative phase;
  private Iterator<Unit> defendersByPrio;
  private Iterator<Unit> defendersByHp;
  private Iterator<Unit> currentDefenseLine;
  private Unit currentDefender = null;
  private final static Logger LOG = Logger.getLogger(BattleWave.class.getName());

  public BattleWave(Army attackingArmy, Army defendingArmy, Initiative phase) {
    this.attackingArmy = attackingArmy;
    this.defendingArmy = defendingArmy;
    this.phase = phase;
    defendersByPrio = this.defendingArmy.getUnitsByPrio().iterator();
    defendersByHp = this.defendingArmy.getUnitsByHitpoints().iterator();
  }

  public void fight() {
    for (Unit attacker : attackingArmy.getUnitsByPrio()) {
      if (matchesPhase(attacker)) {
        chooseNextUnitToBeAttacked(attacker);

        if (noMoreDefenders()) {
          break;
        }

        int damageDone = attacker.doDamage();

        if (attacker.getSpecialAttacks().equals(SPLASH)) {
          doSplashDamage(damageDone, attacker);
        } else {
          doSingleDamage(damageDone, attacker);
        }
      }
    }
  }

  private void doSingleDamage(int damageDone, Unit attacker) {
    currentDefender.reduceHitpoints(damageDone);
    LOG.log(Level.FINE, "%s verursacht %s Schaden an %s (%s HP)", new Object[]{attacker.getName(), damageDone, currentDefender.getName(), currentDefender.getCurrentHitPoints()});

    if (currentDefender.isDead()) {
      currentDefender = null;
    }
  }

  private void doSplashDamage(int overallDamage, Unit attacker) {
    int damageToBeDone = overallDamage;
    do {
      int actualDamageDone = currentDefender.reduceHitpoints(overallDamage);
      LOG.log(Level.FINE, "%s verursacht %s Schaden an %s (%s HP)", new Object[]{attacker.getName(), actualDamageDone, currentDefender.getName(), currentDefender.isDead() ? "0" : currentDefender.getCurrentHitPoints()});
      damageToBeDone -= actualDamageDone;

      if (currentDefender.isDead()) {
        currentDefender = null;
        chooseNextUnitToBeAttacked(attacker);
      }
    } while (damageToBeDone > 0 && currentDefender != null);
  }

  private boolean noMoreDefenders() {
    return currentDefender == null && !currentDefenseLine.hasNext();
  }

  private void chooseNextUnitToBeAttacked(Unit attacker) {
    chooseDefendersOrder(attacker);
    if (currentDefender == null && currentDefenseLine.hasNext()) {
      currentDefender = currentDefenseLine.next();
    }
  }

  private void chooseDefendersOrder(Unit attacker) {
    currentDefenseLine = defendersByPrio;
    if (attacker.getSpecialAttacks().equals(ATTACK_WEAKEST)) {
      currentDefenseLine = defendersByHp;
    }
  }

  private boolean matchesPhase(Unit attacker) {
    return attacker.getInitiative().equals(phase);
  }
}
