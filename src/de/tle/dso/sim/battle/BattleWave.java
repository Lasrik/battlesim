package de.tle.dso.sim.battle;

import com.spinn3r.log5j.Logger;
import static de.tle.dso.units.SpecialAttack.*;
import de.tle.dso.units.Army;
import de.tle.dso.units.Initiative;
import de.tle.dso.units.Unit;
import java.util.Iterator;

public class BattleWave {

  private Army attackingArmy;
  private Army defendingArmy;
  private Initiative phase;
  Iterator<Unit> defendersByPrio;
  Iterator<Unit> defendersByHp;
  Iterator<Unit> currentDefenseLine;
  Unit currentlyAttackedUnit = null;
  private final static Logger LOG = Logger.getLogger(false);

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

        chooseDefendersOrder(attacker);
        chooseNextUnitToBeAttacked();

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
    currentlyAttackedUnit.reduceHitpoints(damageDone);
    LOG.debug("%s verursacht %s Schaden an %s (%s HP)", new Object[]{attacker.getName(), damageDone, currentlyAttackedUnit.getName(), currentlyAttackedUnit.getCurrentHitPoints()});

    if (currentlyAttackedUnit.isDead()) {
      currentlyAttackedUnit = null;
    }
  }

  private void doSplashDamage(int overallDamage, Unit attacker) {
    do {
      int actualDamageDone = currentlyAttackedUnit.reduceHitpoints(overallDamage);
      LOG.debug("%s verursacht %s Schaden an %s (%s HP)", new Object[]{attacker.getName(), actualDamageDone, currentlyAttackedUnit.getName(), currentlyAttackedUnit.isDead() ? "0" : currentlyAttackedUnit.getCurrentHitPoints()});
      overallDamage -= actualDamageDone;

      if (currentlyAttackedUnit.isDead()) {
        currentlyAttackedUnit = null;
        chooseNextUnitToBeAttacked();
      }
    } while (overallDamage > 0 && currentlyAttackedUnit != null);
  }

  private boolean noMoreDefenders() {
    return currentlyAttackedUnit == null && !currentDefenseLine.hasNext();
  }

  private void chooseNextUnitToBeAttacked() {
    if (currentlyAttackedUnit == null && currentDefenseLine.hasNext()) {
      currentlyAttackedUnit = currentDefenseLine.next();
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
