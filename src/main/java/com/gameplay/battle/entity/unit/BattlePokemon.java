package com.gameplay.battle.entity.unit;

import com.gameplay.battle.entity.unit.stat.StatTracker;
import com.gameplay.data.masterdata.attribute.battle.StatusCondition;
import com.gameplay.data.masterdata.attribute.stat.StatType;
import com.gameplay.data.masterdata.attribute.type.PokemonType;
import com.gameplay.data.masterdata.template.pokemon.form.PokemonForm;
import com.gameplay.data.masterdata.template.unit.PartnerPokemon;
import com.gameplay.data.masterdata.template.unit.phase.StarBattleState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BattlePokemon {

	/*------------------------------------------------------------------------------------------------------------------
	.	Constants																											*/

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

	/*------------------------------------------------------------------------------------------------------------------
	.	Constructor																										*/

	protected BattlePokemon(BattleUnit battleUnit, PartnerPokemon partnerPokemon, StarBattleState currentState) {
		this.battleUnit = battleUnit;
		this.partnerPokemon = partnerPokemon;
		this.currentForm = currentState.getPokemonForm();
		this.pokemonTypeList = new ArrayList<>(this.currentForm.getTypeList());

		this.statTracker = new StatTracker(battleUnit.getUnitInstance(), this.battleUnit.getCurrentBattleState().getUnitBaseStat());

		this.maxHP = this.statTracker.getStat(StatType.HP);
		this.currentHP = this.maxHP;

		this.statusCondition = StatusCondition.NONE;
		this.sleepRemainingTurn = 0;
		this.freezeRemainingTurn = 0;
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
		return currentHP <= 0;
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Form Management																									*/

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
	.	Stat Management																									*/

	public StatTracker getStatTracker() {
		return statTracker;
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	HP Management																									*/

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
	.	Status																											*/

	public void resetStatus() {
		this.pokemonTypeList = List.copyOf(this.currentForm.getTypeList());
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Status Condition Management																						*/

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
	.	Turn Flow																										*/

	public enum StartStepResult {
		ACT,
		WOKE_UP,
		THAWED,
		ASLEEP,
		FROZEN,
		FAINTED;

		public boolean canAct() {
			return switch (this) {
				case ACT, WOKE_UP, THAWED -> true;
				default -> false;
			};
		}
	}

	public StartStepResult startStep() {
		if (isFainted()) {
			return StartStepResult.FAINTED;
		}

		ThreadLocalRandom random = ThreadLocalRandom.current();

		if (statusCondition == StatusCondition.SLEEP) {
			if (sleepRemainingTurn > 0) {
				sleepRemainingTurn--;
				return StartStepResult.ASLEEP;
			}

			removeStatusCondition();
			return StartStepResult.WOKE_UP;
		}

		if (this.statusCondition == StatusCondition.FREEZE) {
			if (random.nextDouble() < FREEZE_THAW_CHANCE) {
				removeStatusCondition();
				return StartStepResult.THAWED;
			}

			if (freezeRemainingTurn > 0) {
				freezeRemainingTurn--;
				return StartStepResult.FROZEN;
			}

			removeStatusCondition();
			return StartStepResult.THAWED;
		}

		return StartStepResult.ACT;
	}

	public boolean isFullyParalyzed() {
		return statusCondition == StatusCondition.PARALYSIS && ThreadLocalRandom.current().nextDouble() < FULLY_PARALYZED_CHANCE;
	}
}
