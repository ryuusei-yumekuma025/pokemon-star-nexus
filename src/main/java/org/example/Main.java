package org.example;

import com.gameplay.battle.entity.unit.BattlePokemon;
import com.gameplay.battle.entity.unit.BattleStarPartner;
import com.gameplay.masterdata.attribute.battle.field.Terrain;
import com.gameplay.masterdata.attribute.region.WorldRegion;
import com.gameplay.masterdata.attribute.type.PokemonType;
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
import com.gameplay.masterdata.template.pokemon.form.PokemonForm;
import com.gameplay.masterdata.template.trainer.TrainerAlternate;
import com.gameplay.profile.PlayProfile;
import com.gameplay.profile.unit.starpartner.OwnedStarPartner;
import com.utils.StringUtils;

import java.util.Objects;

public class Main {
	static void main() {
		MasterDataBootstrap.init();

		System.out.println(TrainerRegistry.getTrainerAlternateByCode("DAWN").getStarPartnerByPartnerPokemon("Empoleon").getStarPartnerId());

		System.out.println(PokemonMoveRegistry.getPokemonMoveById(61).getMoveCode());

		System.out.println(StarNexusMoveRegistry.getStarNexusMoveByCode("Brand New Dawn of Bubble Beam Shine"));

		System.out.println(PokemonMoveRegistry.getPokemonMoveByCode("LUNGE").getMoveTier());

		System.out.println(Terrain.equalsTerrain(Terrain.fromCode("Grassy Terrain"), Terrain.OVERGROWN_GRASSY_TERRAIN));

		/*for (PokemonForm pokemonForm : PokemonRegistry.getAllPokemonForms()) {
			printPokemon(pokemonForm);
		}*/

		System.out.println(StarPartnerRegistry.getStarPartnerByCode("Dawn and Empoleon").getStarPartnerStateById(2));

		PlayProfile playProfile = new PlayProfile("Ryuusei");
		OwnedStarPartner ownedStarPartner = new OwnedStarPartner(StarPartnerRegistry.getStarPartnerByCode("Dawn and Empoleon"));
		BattleStarPartner battleStarPartner = new BattleStarPartner(ownedStarPartner);

		battleStarPartner.changeState(1);

		System.out.println(battleStarPartner.getPartnerPokemon().getCurrentForm());
		System.out.println(ownedStarPartner.getUniteDateAndTime());

		System.out.println(playProfile.getTrainerId());
	}

	public static void printTrainer(TrainerAlternate trainerAlternate) {
		System.out.println(trainerAlternate.getTrainerAlternateFullId() + " " + trainerAlternate.getTrainerAlternateFullCode());
		System.out.println(trainerAlternate.getRegion());
		for (TrainerMove trainerMove : trainerAlternate.getAllTrainerMoves()) {
			System.out.println(trainerMove.getMoveId() + " " + trainerMove.getMoveCode() + " " + trainerMove.getTacticPoint());
		}
	}

	public static void printPokemon(PokemonForm pokemonForm) {
		System.out.println(pokemonForm.getPokemonSpecies().getPokedexNumber() + " " + pokemonForm.getPokemonFormFullCode() + " " +
				pokemonForm.getPrimaryType() + ((pokemonForm.getSecondaryType() == PokemonType.TYPELESS) ? "" : "/" + pokemonForm.getSecondaryType()));
	}
}
