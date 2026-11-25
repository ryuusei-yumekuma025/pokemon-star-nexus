package com.gameplay.data.masterdata.template.unit.phase;

import com.gameplay.data.masterdata.data.master.registry.move.combat.PokemonMoveRegistry;
import com.gameplay.data.masterdata.data.master.registry.move.combat.ResonanceMoveRegistry;
import com.gameplay.data.masterdata.data.master.registry.move.tactic.ItemMoveRegistry;
import com.gameplay.data.masterdata.data.master.registry.move.tactic.TrainerMoveRegistry;
import com.gameplay.data.masterdata.data.master.registry.pokemon.PokemonRegistry;
import com.gameplay.data.masterdata.template.move.Move;
import com.gameplay.data.masterdata.template.pokemon.form.PokemonForm;
import com.gameplay.data.masterdata.template.unit.BaseStat;
import com.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class StarBattleState {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final int stateId;
	private final String stateCode;
	private final PokemonForm pokemonForm;
	private final List<Move> moveset;
	private final BaseStat unitBaseStat;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private StarBattleState(Builder builder) {
		this.stateId = builder.stateId;
		this.stateCode = builder.stateCode;
		this.pokemonForm = builder.pokemonForm;
		this.moveset = List.copyOf(builder.moveset);
		this.unitBaseStat = builder.unitBaseStat;
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public int getStateId() {
		return stateId;
	}

	public String getStateCode() {
		return stateCode;
	}

	public PokemonForm getPokemonForm() {
		return pokemonForm;
	}

	public List<Move> getMoveset() {
		return moveset;
	}

	public Move getMove(int slot) {
		return moveset.get(slot - 1);
	}

	public <M extends Move> M getMove(int slot, Class<M> moveClass) {
		return moveClass.cast(getMove(slot));
	}

	public BaseStat getUnitBaseStat() {
		return unitBaseStat;
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Builder																											*/

	public static class Builder {
		private final int stateId;
		private final String stateCode;
		private final PokemonForm pokemonForm;
		private final List<Move> moveset = new ArrayList<>();
		private BaseStat unitBaseStat;

		public Builder(int stateId, String stateCode, String pokemonForm) {
			this.stateId = stateId;
			this.stateCode = StringUtils.requireUpperSnakeCase(stateCode, "State Code");
			this.pokemonForm = PokemonRegistry.getFormByCode(pokemonForm);
		}

		public Builder setMoveset(List<String> moveList) {
			if (moveList != null && !moveList.isEmpty()) {
				for (String move : moveList) {
					String[] moveInfo = move.split("/");
					Move moveSearch = switch (moveInfo[0]) {
						case "PM" -> PokemonMoveRegistry.getByCode(moveInfo[1]);
						case "RM" -> ResonanceMoveRegistry.getByCode(moveInfo[1]);
						case "IM" -> ItemMoveRegistry.getByCode(moveInfo[1]);
						case "TM" -> TrainerMoveRegistry.getByCode(moveInfo[1]);
						default -> null;
					};

					if (moveSearch != null) {
						this.moveset.add(moveSearch);
					}
				}
			}
			return this;
		}

		public Builder setBaseStat(List<Integer> baseStat) {
			this.unitBaseStat = new BaseStat(baseStat);
			return this;
		}

		public StarBattleState build() {
			return new StarBattleState(this);
		}
	}
}
