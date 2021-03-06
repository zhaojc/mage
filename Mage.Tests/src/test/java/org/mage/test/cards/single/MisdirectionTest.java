/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class MisdirectionTest extends CardTestPlayerBase {

    /**
     * Tests if Misdirection for target opponent works correctly
     * https://github.com/magefree/mage/issues/574
     */
    @Test
    public void testChangeTargetOpponent() {
        // Target opponent discards two cards. Put the top two cards of your library into your graveyard.
        addCard(Zone.HAND, playerA, "Rakshasa's Secret");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        /*    
        Misdirection {3}{U}{U}
        Instant
        You may exile a blue card from your hand rather than pay Misdirection's mana cost.
        Change the target of target spell with a single target.
        */
        addCard(Zone.HAND, playerB, "Misdirection");
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rakshasa's Secret", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Misdirection", "Rakshasa's Secret", "Rakshasa's Secret");
        addTarget(playerB, playerA); // only legal target is player B as opponent - so player A should not be allowed
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Rakshasa's Secret", 1);
        assertGraveyardCount(playerB, "Misdirection", 1);
        assertHandCount(playerB, "Silvercoat Lion", 0);
    }
    
    // check to change target permanent creature legal to to a creature the opponent of the spell controller controls
    @Test
    public void testChangePublicExecution() {
        // Destroy target creature an opponent controls. Each other creature that player controls gets -2/-0 until end of turn.
        addCard(Zone.HAND, playerA, "Public Execution");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        /*    
        Misdirection {3}{U}{U}
        Instant
        You may exile a blue card from your hand rather than pay Misdirection's mana cost.
        Change the target of target spell with a single target.
        */
        addCard(Zone.HAND, playerB, "Misdirection");
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Custodian of the Trove", 1); // 4/3
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Public Execution", "Pillarfield Ox");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Misdirection", "Public Execution", "Public Execution");
        addTarget(playerB, "Custodian of the Trove"); 
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Public Execution", 1);
        assertGraveyardCount(playerB, "Misdirection", 1);
        
        assertGraveyardCount(playerB, "Custodian of the Trove",1);
        assertPermanentCount(playerB, "Pillarfield Ox", 1);
        assertPowerToughness(playerB, "Pillarfield Ox", 0, 4);

    } 
    
    // check to change target permanent creature not legal to to a creature the your opponent controls
    @Test
    public void testChangePublicExecution2() {
        // Destroy target creature an opponent controls. Each other creature that player controls gets -2/-0 until end of turn.
        addCard(Zone.HAND, playerA, "Public Execution");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Keeper of the Lens", 1);
        /*    
        Misdirection {3}{U}{U}
        Instant
        You may exile a blue card from your hand rather than pay Misdirection's mana cost.
        Change the target of target spell with a single target.
        */
        addCard(Zone.HAND, playerB, "Misdirection");
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Custodian of the Trove", 1); // 4/3
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Public Execution", "Custodian of the Trove");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Misdirection", "Public Execution", "Public Execution");
        addTarget(playerB, "Keeper of the Lens"); 
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Public Execution", 1);
        assertGraveyardCount(playerB, "Misdirection", 1);
        assertPermanentCount(playerA, "Keeper of the Lens", 1);

        assertPermanentCount(playerB, "Pillarfield Ox", 1);
        assertPowerToughness(playerB, "Pillarfield Ox", 0, 4);
        
        assertGraveyardCount(playerB, "Custodian of the Trove",1);

    }        
}