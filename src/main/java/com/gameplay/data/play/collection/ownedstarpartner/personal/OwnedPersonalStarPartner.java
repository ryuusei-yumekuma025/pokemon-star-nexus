package com.gameplay.data.play.collection.ownedstarpartner.personal;

import com.gameplay.data.masterdata.attribute.starpartner.Starsoul;
import com.gameplay.data.masterdata.attribute.type.PokemonType;
import com.gameplay.data.masterdata.template.pokemon.form.PokemonForm;
import com.gameplay.data.masterdata.template.unit.starjewel.StarJewelEmblem;
import com.gameplay.data.masterdata.template.unit.type.playable.personal.PersonalStarPartner;
import com.gameplay.data.play.collection.ownedstarpartner.OwnedStarPartner;

public class OwnedPersonalStarPartner extends OwnedStarPartner<PersonalStarPartner> {

	public OwnedPersonalStarPartner(int id, String pokemonForm, String gender, boolean isShiny, String starType, String starsoul) {
		PersonalStarPartner personalStarPartner = new PersonalStarPartner.Builder(id, pokemonForm, gender, isShiny)
				.setStarJewel(new StarJewelEmblem(starType, starsoul))
				.build();
		super(personalStarPartner, "OWN" + personalStarPartner.getStarUnitId());
	}
}
