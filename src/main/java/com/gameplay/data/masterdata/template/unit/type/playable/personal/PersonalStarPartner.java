package com.gameplay.data.masterdata.template.unit.type.playable.personal;

import com.gameplay.data.masterdata.data.master.registry.trainer.TrainerRegistry;
import com.gameplay.data.masterdata.template.pokemon.form.PokemonForm;
import com.gameplay.data.masterdata.template.unit.PartnerPokemon;
import com.gameplay.data.masterdata.template.unit.type.playable.PlayableStarPartner;

public class PersonalStarPartner extends PlayableStarPartner {

	private PersonalStarPartner(Builder builder) {
		super(builder);
	}

	public static class Builder extends PlayableStarPartner.Builder<Builder> {
		public Builder(int id, String pokemonForm, String gender, boolean isShiny) {
			PartnerPokemon partnerPokemon = new PartnerPokemon.Builder(pokemonForm, gender, isShiny)
					.build();
			super("PSP_" + String.format("%04d%06d%04d", 0, partnerPokemon.getPokemonSpecies().getPokedexNumber(), id),
					"RYUUSEI_AND_" + partnerPokemon.getDefaultForm() + "_" + String.format("%04d", id),
					TrainerRegistry.getAlternateByCode("RYUUSEI"), partnerPokemon);
		}

		@Override
		public PersonalStarPartner build() {
			return new PersonalStarPartner(this);
		}
	}
}
