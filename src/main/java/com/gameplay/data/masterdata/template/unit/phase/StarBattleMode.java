package com.gameplay.data.masterdata.template.unit.phase;

import com.gameplay.data.masterdata.data.master.dto.unit.BaseStatDTO;
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

public class StarBattleMode {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final int modeId;
	private final String modeCode;
	private final PokemonForm pokemonForm;
	private final List<Move> moveset;
	private final BaseStat unitBaseStat;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private StarBattleMode(Builder builder) {
		this.modeId = builder.modeId;
		this.modeCode = builder.modeCode;
		this.pokemonForm = builder.pokemonForm;
		this.moveset = List.copyOf(builder.moveset);
		this.unitBaseStat = builder.unitBaseStat;
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public int getModeId() {
		return modeId;
	}

	public String getModeCode() {
		return modeCode;
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
		private final int modeId;
		private final String modeCode;
		private final PokemonForm pokemonForm;
		private final List<Move> moveset = new ArrayList<>();
		private BaseStat unitBaseStat;

		public Builder(int modeId, String modeCode, String pokemonForm) {
			this.modeId = modeId;
			this.modeCode = StringUtils.requireUpperSnakeCase(modeCode, "Mode Code");
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

		public Builder setBaseStat(BaseStatDTO baseStat) {
			if (baseStat != null) {
				this.unitBaseStat = baseStat.create();
			}
			return this;
		}

		public StarBattleMode build() {
			return new StarBattleMode(this);
		}
	}
}
