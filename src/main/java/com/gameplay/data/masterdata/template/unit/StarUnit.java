package com.gameplay.data.masterdata.template.unit;

import com.gameplay.data.masterdata.data.master.registry.trainer.TrainerRegistry;
import com.gameplay.data.masterdata.template.move.Move;
import com.gameplay.data.masterdata.template.move.tactic.TacticMove;
import com.gameplay.data.masterdata.template.trainer.Trainer;
import com.gameplay.data.masterdata.template.unit.phase.StarBattlePhase;
import com.gameplay.data.masterdata.template.unit.phase.StarBattleMode;
import com.gameplay.data.masterdata.template.unit.starjewel.StarJewel;
import com.utils.IdCodeKeyMap;
import com.utils.StringUtils;

import java.util.*;

public abstract class StarUnit<T extends Trainer, SJ extends StarJewel> {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final String starUnitId;
	private final String starUnitCode;
	private final T partnerTrainer;
	private final PartnerPokemon partnerPokemon;
	private final SJ starJewel;
	private final IdCodeKeyMap<Integer, StarBattlePhase> starBattlePhases;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	protected StarUnit(Builder<?, T, SJ> builder) {
		builder.flushBasePhase();
		this.starUnitId = builder.starUnitId;
		this.starUnitCode = builder.starUnitCode;
		this.partnerPokemon = builder.partnerPokemon;
		this.partnerTrainer = builder.partnerTrainer;
		this.starJewel = builder.starJewel;
		this.starBattlePhases = builder.starBattlePhases.build();
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public boolean isStarPartner() {
		return partnerTrainer != null;
	}

	public PartnerPokemon getPartnerPokemon() {
		return partnerPokemon;
	}

	public T getPartnerTrainer() {
		return partnerTrainer;
	}

	public String getStarUnitId() {
		return starUnitId;
	}

	public String getStarUnitCode() {
		return starUnitCode;
	}

	public SJ getStarJewel() {
		return starJewel;
	}

	public StarBattlePhase getPhase() {
		List<StarBattlePhase> starBattlePhaseList = getAllPhases().stream().toList();
		return getPhaseById(starBattlePhaseList.getFirst().getPhaseOrdinal());
	}

	public StarBattlePhase getPhaseById(int id) {
		return starBattlePhases.getById(id);
	}

	public StarBattlePhase getPhaseByCode(String code) {
		return starBattlePhases.getByCode(code);
	}

	public Collection<StarBattlePhase> getAllPhases() {
		return starBattlePhases.getAll();
	}

	public Collection<TacticMove> getAllTacticMoves() {
		Set<TacticMove> tacticMoves = new LinkedHashSet<>();
		for (StarBattlePhase starBattlePhase : getAllPhases()) {
			for (StarBattleMode starBattleMode : starBattlePhase.getAllModes()) {
				for (Move move : starBattleMode.getMoveset()) {
					if (move instanceof TacticMove tacticMove) {
						tacticMoves.add(tacticMove);
					}
				}
			}
		}
		return Collections.unmodifiableSet(tacticMoves);
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Builder																											*/

	public abstract static class Builder<B extends Builder<B, T, SJ>, T extends Trainer, SJ extends StarJewel> {
		private final String starUnitId;
		private final String starUnitCode;
		private T partnerTrainer = null;
		private PartnerPokemon partnerPokemon;
		private SJ starJewel;
		private final IdCodeKeyMap.Builder<Integer, StarBattlePhase> starBattlePhases = new IdCodeKeyMap.Builder<>();
		private boolean hasMultiPhase = false;
		private StarBattlePhase.Builder nonPhase = new StarBattlePhase.Builder(0, "BASE", 0);

		protected Builder(String starUnitId, String starUnitCode, String trainerCode, Class<T> trainerType) {
			this.starUnitId = StringUtils.upperSnakeCase(starUnitId);
			this.starUnitCode = StringUtils.requireUpperSnakeCase(starUnitCode, "Star Unit Code");
			if (trainerCode != null) {
				Trainer trainer = TrainerRegistry.getTrainerByCode(trainerCode);
				this.partnerTrainer = trainerType.cast(trainer);
			}
		}

		protected Builder(String starUnitId, String starUnitCode, T partnerTrainer) {
			this.starUnitId = StringUtils.requireUpperSnakeCase(starUnitId, "Star Unit Id");
			this.starUnitCode = StringUtils.requireUpperSnakeCase(starUnitCode, "Star Unit Code");
			this.partnerTrainer = partnerTrainer;
		}

		protected Builder(String starUnitId, String starUnitCode, T partnerTrainer, PartnerPokemon partnerPokemon) {
			this.starUnitId = StringUtils.requireUpperSnakeCase(starUnitId, "Star Unit Id");
			this.starUnitCode = StringUtils.requireUpperSnakeCase(starUnitCode, "Star Unit Code");
			this.partnerTrainer = partnerTrainer;
			this.partnerPokemon = partnerPokemon;
		}

		public String getStarUnitId() {
			return starUnitId;
		}

		@SuppressWarnings("unchecked")
		protected B self() {
			return (B) this;
		}

		public B setPartnerTrainer(T partnerTrainer) {
			this.partnerTrainer = partnerTrainer;
			return self();
		}

		public B setPartnerPokemon(PartnerPokemon partnerPokemon) {
			this.partnerPokemon = partnerPokemon;
			return self();
		}

		public B setStarJewel(SJ starJewel) {
			this.starJewel = starJewel;
			return self();
		}

		public void addMode(StarBattleMode starBattleMode) {
			if (starBattleMode != null) {
				nonPhase.addMode(starBattleMode);
			}
		}

		public void addPhase(StarBattlePhase starBattlePhase) {
			if (starBattlePhase != null) {
				this.starBattlePhases.put(starBattlePhase.getPhaseOrdinal(), starBattlePhase.getPhaseCode(), starBattlePhase);
				hasMultiPhase = true;
			}
		}

		private void flushBasePhase() {
			if (hasMultiPhase) {
				return;
			}

			addPhase(nonPhase.build());
			nonPhase = null;
		}

		public abstract StarUnit<T, SJ> build();
	}
}
