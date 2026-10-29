package org.example;

import com.gameplay.masterdata.attribute.region.WorldRegion;
import com.gameplay.masterdata.data.master.MasterDataBootstrap;
import com.gameplay.masterdata.data.master.loader.move.item.ItemMoveLoader;
import com.gameplay.masterdata.data.master.loader.move.pokemon.PokemonMoveLoader;
import com.gameplay.masterdata.data.master.loader.starpartner.StarPartnerLoader;
import com.gameplay.masterdata.data.master.loader.trainer.TrainerLoader;
import com.gameplay.masterdata.data.master.registry.move.battle.PokemonMoveRegistry;
import com.gameplay.masterdata.data.master.registry.move.starnexus.StarNexusMoveRegistry;
import com.gameplay.masterdata.data.master.registry.move.tactic.ItemMoveRegistry;
import com.gameplay.masterdata.data.master.registry.move.tactic.TrainerMoveRegistry;
import com.gameplay.masterdata.data.master.registry.pokemon.PokemonRegistry;
import com.gameplay.masterdata.data.master.registry.starpartner.StarPartnerRegistry;
import com.gameplay.masterdata.data.master.registry.trainer.TrainerRegistry;
import com.gameplay.masterdata.template.move.battle.resonance.ResonanceMove;
import com.gameplay.masterdata.template.move.tactic.trainer.TrainerMove;
import com.gameplay.masterdata.template.trainer.TrainerAlternate;
import com.utils.StringUtils;

import java.util.Objects;

public class Main {
	static void main() {
		MasterDataBootstrap.init();

		printTrainer(TrainerRegistry.getTrainerAlternateByCode("DAWN"));

		System.out.println(TrainerRegistry.getTrainerAlternateByCode("DAWN").getStarPartnerByPartnerPokemon("Empoleon").getStarPartnerId());

		System.out.println(PokemonMoveRegistry.getPokemonMoveById(61).getMoveCode());

		System.out.println(StarNexusMoveRegistry.getStarNexusMoveByCode("Brand New Dawn of Bubble Beam Shine"));

		System.out.println(StarPartnerRegistry.getStarPartnerByCode("DAWN_#_EMPOLEON").getDefaultPartnerState().getMove(1, ResonanceMove.class).getMovePrototype());
	}

	public static void printTrainer(TrainerAlternate trainerAlternate) {
		System.out.println(trainerAlternate.getTrainerAlternateFullId() + " " + trainerAlternate.getTrainerAlternateFullCode());
		System.out.println(trainerAlternate.getRegion());
		for (TrainerMove trainerMove : trainerAlternate.getAllTrainerMoves()) {
			System.out.println(trainerMove.getMoveId() + " " + trainerMove.getMoveCode() + " " + trainerMove.getTacticPoint());
		}
	}
}
