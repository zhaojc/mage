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
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public class DeathlessBehemoth extends CardImpl {

    private final static FilterControlledPermanent filter = new FilterControlledPermanent("two Eldrazi Scions");

    static {
        filter.add(Predicates.and(
                new SubtypePredicate(SubType.ELDRAZI),
                new SubtypePredicate(SubType.SCION)));
    }

    public DeathlessBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}");
        this.subtype.add("Eldrazi");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Sacrifice two Eldrazi Scions: Return Deathless Behemoth from your graveyard to your hand. Activate this ability only any time you could cast a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(Zone.GRAVEYARD,
                new ReturnToHandSourceEffect(), new SacrificeTargetCost(new TargetControlledPermanent(2, 2, filter, true))));
    }

    public DeathlessBehemoth(final DeathlessBehemoth card) {
        super(card);
    }

    @Override
    public DeathlessBehemoth copy() {
        return new DeathlessBehemoth(this);
    }
}
