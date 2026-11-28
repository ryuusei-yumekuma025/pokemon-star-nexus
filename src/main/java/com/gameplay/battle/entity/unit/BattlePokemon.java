package com.gameplay.battle.entity.unit;

import com.gameplay.battle.entity.unit.stat.StatTracker;
import com.gameplay.data.masterdata.attribute.battle.StatusCondition;
import com.gameplay.data.masterdata.attribute.stat.StatType;
import com.gameplay.data.masterdata.attribute.type.PokemonType;
import com.gameplay.data.masterdata.template.pokemon.form.PokemonForm;
import com.gameplay.data.masterdata.template.unit.PartnerPokemon;
import com.gameplay.data.masterdata.template.unit.phase.StarBattleMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BattlePokemon {

	/*------------------------------------------------------------------------------------------------------------------
	.	Constants																										*/

	private static final int MAX_FROZEN_TURN = 4;
	private static final double FREEZE_THAW_CHANCE = 0.2;
	private static final double FULLY_PARALYZED_CHANCE = 0.25;

	/*------------------------------------------------------------------------------------------------------------------
	.	Fields																											*/

	private final BattleUnit battleUnit;
	private final PartnerPokemon partnerPokemon;
	private PokemonForm currentForm;
	private List<PokemonType> pokemonTypeList;

	private final StatTracker statTracker;

	private int currentHP;
	private int maxHP;

	private StatusCondition statusCondition;
	private int sleepRemainingTurn;
	private int freezeRemainingTurn;

	private boolean enduringEffect;

	/*------------------------------------------------------------------------------------------------------------------
	.	Constructor																										*/

	protected BattlePokemon(BattleUnit battleUnit, PartnerPokemon partnerPokemon, StarBattleMode currentMode) {
		this.battleUnit = battleUnit;
		this.partnerPokemon = partnerPokemon;
		this.currentForm = currentMode.getPokemonForm();
		this.pokemonTypeList = new ArrayList<>(this.currentForm.getTypeList());

		this.statTracker = new StatTracker(battleUnit.getUnitProfile(), this.battleUnit.getCurrentBattleMode().getUnitBaseStat());

		this.maxHP = this.statTracker.getStat(StatType.HP);
		this.currentHP = this.maxHP;

		this.statusCondition = StatusCondition.NONE;
		this.sleepRemainingTurn = 0;
		this.freezeRemainingTurn = 0;

		this.enduringEffect = false;
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Getters																											*/

	public BattleUnit getBattleUnit() {
		return battleUnit;
	}

	public PartnerPokemon getPartnerPokemon() {
		return partnerPokemon;
	}

	public boolean isFainted() {
		return this.currentHP <= 0;
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	HP Controller																									*/

	public int getCurrentHP() {
		return currentHP;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public double getHPPercentage() {
		return (double) currentHP / maxHP;
	}

	public int takeDamage(int damage) {
		if (damage <= 0) {
			return 0;
		}

		int damageDealt = Math.min(this.currentHP, damage);

		this.currentHP -= damageDealt;

		if (isFainted()) {
			removeStatusCondition();
		}

		return damageDealt;
	}

	public int restoreHP(int healAmount) {
		if (healAmount <= 0 || isFainted() || this.currentHP == this.maxHP) {
			return 0;
		}

		int healed = Math.min(this.maxHP - this.currentHP, healAmount);
		this.currentHP += healed;

		return healed;
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Form Controller																									*/

	public PokemonForm getCurrentForm() {
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
	.	Type Controller																									*/

	public List<PokemonType> getPokemonTypeList() {
		return Collections.unmodifiableList(pokemonTypeList);
	}

	public boolean hasType(PokemonType pokemonType) {
		return pokemonTypeList.contains(pokemonType);
	}

	public boolean addType(PokemonType pokemonType) {
		if (pokemonType == null || pokemonTypeList.contains(pokemonType)) {
			return false;
		}

		this.pokemonTypeList.add(pokemonType);

		return true;
	}

	public boolean overrideType(PokemonType pokemonType) {
		if (pokemonType == null || (pokemonTypeList.size() == 1 && pokemonTypeList.contains(pokemonType))) {
			return false;
		}

		this.pokemonTypeList.clear();
		this.pokemonTypeList.add(pokemonType);

		return true;
	}

	public void resetTypes() {
		pokemonTypeList.clear();
		pokemonTypeList.addAll(this.currentForm.getTypeList());
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Stat Tracker																									*/

	public StatTracker getStatTracker() {
		return statTracker;
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	State Controller																								*/

	public void resetState() {
		this.pokemonTypeList = List.copyOf(this.currentForm.getTypeList());
		resetTypes();
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Status Condition Controller																						*/

	public StatusCondition getStatusCondition() {
		return statusCondition;
	}

	public boolean affectedByStatusCondition() {
		return statusCondition != StatusCondition.NONE;
	}

	public int getAsleepTurn() {
		if (statusCondition == StatusCondition.SLEEP) {
			return sleepRemainingTurn;
		}
		return 0;
	}

	public boolean getInflictedWith(StatusCondition statusCondition) {
		if (statusCondition == null || statusCondition == StatusCondition.NONE || this.statusCondition != StatusCondition.NONE) {
			return false;
		}

		this.statusCondition = statusCondition;
		if (this.statusCondition == StatusCondition.SLEEP) {
			this.sleepRemainingTurn = ThreadLocalRandom.current().nextInt(1, 4);
		} else if (this.statusCondition == StatusCondition.FREEZE) {
			this.freezeRemainingTurn = MAX_FROZEN_TURN;
		}

		return true;
	}

	public boolean removeStatusCondition() {
		if (this.statusCondition == StatusCondition.NONE) {
			return false;
		}

		this.statusCondition = StatusCondition.NONE;
		this.sleepRemainingTurn = 0;
		this.freezeRemainingTurn = 0;

		return true;
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Effect																											*/

	public boolean applyEnduringEffect() {
		this.enduringEffect = true;
		return enduringEffect;
	}
}
