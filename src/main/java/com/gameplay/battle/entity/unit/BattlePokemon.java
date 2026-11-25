package com.gameplay.battle.entity.unit;

import com.gameplay.data.masterdata.attribute.type.PokemonType;
import com.gameplay.data.masterdata.template.pokemon.form.PokemonForm;
import com.gameplay.data.masterdata.template.unit.PartnerPokemon;
import com.gameplay.data.masterdata.template.unit.phase.StarBattleState;

import java.util.ArrayList;
import java.util.List;

public class BattlePokemon {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final BattleUnit battleUnit;
	private final PartnerPokemon partnerPokemon;
	private PokemonForm currentForm;
	private List<PokemonType> pokemonTypeList;

	private int currentHP;
	private int maxHP;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	protected BattlePokemon(BattleUnit battleUnit, PartnerPokemon partnerPokemon, StarBattleState currentState) {
		this.battleUnit = battleUnit;
		this.partnerPokemon = partnerPokemon;
		this.currentForm = currentState.getPokemonForm();
		this.pokemonTypeList = new ArrayList<>(this.currentForm.getTypeList());

		this.maxHP = 100;
		this.currentHP = this.maxHP;
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public PartnerPokemon getPartnerPokemon() {
		return partnerPokemon;
	}

	public boolean isFainted() {
		return currentHP <= 0;
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Form Management																											*/

	public PokemonForm getPokemonForm() {
		return currentForm;
	}

	public int changeForm(PokemonForm pokemonForm) {
		if (this.currentForm == pokemonForm) {
			return 0;
		}

		this.currentForm = pokemonForm;
		this.pokemonTypeList = List.copyOf(this.currentForm.getTypeList());

		return 1;
	}

	/*------------------------------------------------------------------------------------------------------------------
	5.	Status																											*/

	public void resetStatus() {
		this.pokemonTypeList = List.copyOf(this.currentForm.getTypeList());
	}
}
