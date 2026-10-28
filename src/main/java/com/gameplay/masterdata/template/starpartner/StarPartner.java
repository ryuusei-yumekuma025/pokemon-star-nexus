package com.gameplay.masterdata.template.starpartner;

import com.gameplay.masterdata.data.master.registry.trainer.TrainerRegistry;
import com.gameplay.masterdata.template.move.battle.resonance.ResonanceMove;
import com.gameplay.masterdata.template.move.starnexus.StarNexusMove;
import com.gameplay.masterdata.template.move.tactic.trainer.TrainerMove;
import com.gameplay.masterdata.template.trainer.TrainerAlternate;
import com.utils.StringUtils;

import java.util.*;

public class StarPartner {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final String starPartnerId;
	private final String starPartnerCode;
	private final TrainerAlternate partnerTrainer;
	private final PartnerPokemon partnerPokemon;
	private final StarJewel starJewel;
	private final Map<Integer, ResonanceMove> resonanceMoveByIdMap;
	private final Map<String, ResonanceMove> resonanceMoveByCodeMap;
	private final Map<Integer, StarNexusMove> starNexusMoveByIdMap;
	private final Map<String, StarNexusMove> starNexusMoveByCodeMap;
	private final Map<Integer, PartnerState> partnerStateByIdMap;
	private final Map<String, PartnerState> partnerStateByCodeMap;
	private final int defaultPartnerStateId;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private StarPartner(Builder builder) {
		this.starPartnerId = builder.starPartnerId;
		this.starPartnerCode = builder.starPartnerCode;
		this.partnerTrainer = builder.partnerTrainer;
		this.partnerPokemon = builder.partnerPokemon;
		this.starJewel = builder.starJewel;
		this.resonanceMoveByIdMap = Collections.unmodifiableMap(new LinkedHashMap<>(builder.resonanceMoveByIdMap));
		this.resonanceMoveByCodeMap = Map.copyOf(builder.resonanceMoveByCodeMap);
		this.starNexusMoveByIdMap = Collections.unmodifiableMap(new LinkedHashMap<>(builder.starNexusMoveByIdMap));
		this.starNexusMoveByCodeMap = Map.copyOf(builder.starNexusMoveByCodeMap);
		this.partnerStateByIdMap = Collections.unmodifiableMap(new LinkedHashMap<>(builder.partnerStateByIdMap));
		this.partnerStateByCodeMap = Map.copyOf(builder.partnerStateByCodeMap);
		this.defaultPartnerStateId = builder.defaultPartnerStateId;
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public String getStarPartnerId() {
		return starPartnerId;
	}

	public String getStarPartnerCode() {
		return starPartnerCode;
	}

	public TrainerAlternate getPartnerTrainer() {
		return partnerTrainer;
	}

	public PartnerPokemon getPartnerPokemon() {
		return partnerPokemon;
	}

	public StarJewel getStarJewel() {
		return starJewel;
	}

	public ResonanceMove getResonanceMoveById(int moveId) {
		return resonanceMoveByIdMap.get(moveId);
	}

	public ResonanceMove getResonanceMoveByCode(String moveCode) {
		if (StringUtils.isNullOrBlank(moveCode)) {
			throw new IllegalArgumentException("Missing Resonance Move Code.");
		}

		ResonanceMove resonanceMove = resonanceMoveByCodeMap.get(StringUtils.upperSnakeCase(moveCode));
		if (resonanceMove == null) {
			throw new IllegalArgumentException("Failed Search : " + moveCode);
		}

		return resonanceMove;
	}

	public Collection<ResonanceMove> getAllResonanceMoves() {
		return Collections.unmodifiableCollection(resonanceMoveByIdMap.values());
	}

	public StarNexusMove getStarNexusMoveById(int moveId) {
		return starNexusMoveByIdMap.get(moveId);
	}

	public StarNexusMove getStarNexusMoveByCode(String moveCode) {
		if (StringUtils.isNullOrBlank(moveCode)) {
			throw new IllegalArgumentException("Missing Star Nexus Move Code.");
		}

		StarNexusMove starNexusMove = starNexusMoveByCodeMap.get(StringUtils.upperSnakeCase(moveCode));
		if (starNexusMove == null) {
			throw new IllegalArgumentException("Failed Search : " + moveCode);
		}

		return starNexusMove;
	}

	public Collection<StarNexusMove> getAllStarNexusMoves() {
		return Collections.unmodifiableCollection(starNexusMoveByIdMap.values());
	}
	
	public PartnerState getStarPartnerStateById(int stateId) {
		return partnerStateByIdMap.get(stateId);
	}
	
	public PartnerState getStarPartnerStateByCode(String stateCode) {
		if (StringUtils.isNullOrBlank(stateCode)) {
			throw new IllegalArgumentException("Missing Partner State Code.");
		}

		PartnerState partnerState = partnerStateByCodeMap.get(StringUtils.upperSnakeCase(stateCode));
		if (partnerState == null) {
			throw new IllegalArgumentException("Failed Search : " + stateCode);
		}

		return partnerState;
	}

	public PartnerState getDefaultPartnerState() {
		return getStarPartnerStateById(defaultPartnerStateId);
	}

	public Collection<PartnerState> getAllPartnerStates() {
		return Collections.unmodifiableCollection(partnerStateByIdMap.values());
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public static class Builder {
		private final String starPartnerId;
		private final String starPartnerCode;
		private final TrainerAlternate partnerTrainer;
		private PartnerPokemon partnerPokemon;
		private StarJewel starJewel;
		private final Map<Integer, ResonanceMove> resonanceMoveByIdMap = new LinkedHashMap<>();
		private final Map<String, ResonanceMove> resonanceMoveByCodeMap = new HashMap<>();
		private final Map<Integer, StarNexusMove> starNexusMoveByIdMap = new LinkedHashMap<>();
		private final Map<String, StarNexusMove> starNexusMoveByCodeMap = new HashMap<>();
		private final Map<Integer, PartnerState> partnerStateByIdMap = new LinkedHashMap<>();
		private final Map<String, PartnerState> partnerStateByCodeMap = new HashMap<>();
		private int defaultPartnerStateId;

		public Builder(int starPartnerId, String starPartnerCode, String trainerAlternateCode) {
			TrainerAlternate trainerAlternate = TrainerRegistry.getTrainerAlternateByCode(trainerAlternateCode);

			this.starPartnerId = trainerAlternate.getTrainerAlternateFullId() + String.format("%03d", starPartnerId);
			this.starPartnerCode = StringUtils.upperSnakeCase(starPartnerCode);
			this.partnerTrainer = trainerAlternate;
		}

		public String getStarPartnerId() {
			return starPartnerId;
		}

		public Builder setPartnerPokemon(PartnerPokemon partnerPokemon) {
			if (partnerPokemon != null) {
				this.partnerPokemon = partnerPokemon;
			}
			return this;
		}

		public Builder setStarJewel(StarJewel starJewel) {
			this.starJewel = starJewel;
			return this;
		}

		public void addResonanceMove(ResonanceMove resonanceMove) {
			if (resonanceMove != null) {
				int moveId = resonanceMove.getMoveIdNo();
				String moveCode = StringUtils.upperSnakeCase(resonanceMove.getMoveCode());

				if (!StringUtils.isNullOrBlank(moveCode)) {
					this.resonanceMoveByIdMap.put(moveId, resonanceMove);
					this.resonanceMoveByCodeMap.put(moveCode, resonanceMove);
				}
			}
		}

		public void addStarNexusMove(StarNexusMove starNexusMove) {
			if (starNexusMove != null) {
				int moveId = starNexusMove.getMoveIdNo();
				String moveCode = StringUtils.upperSnakeCase(starNexusMove.getMoveCode());

				if (!StringUtils.isNullOrBlank(moveCode)) {
					this.starNexusMoveByIdMap.put(moveId, starNexusMove);
					this.starNexusMoveByCodeMap.put(moveCode, starNexusMove);
				}
			}
		}

		public Builder setDefaultPartnerState(int defaultPartnerStateId) {
			this.defaultPartnerStateId = defaultPartnerStateId;
			return this;
		}
		
		public void addStarPartnerState(PartnerState partnerState) {
			if (partnerState != null) {
				int stateId = partnerState.getStateId();
				String stateCode = StringUtils.upperSnakeCase(partnerState.getStateCode());
				
				if (!StringUtils.isNullOrBlank(stateCode)) {
					this.partnerStateByIdMap.put(stateId, partnerState);
					this.partnerStateByCodeMap.put(stateCode, partnerState);
				}
			}
		}

		public StarPartner build() {
			return new StarPartner(this);
		}
	}
}
