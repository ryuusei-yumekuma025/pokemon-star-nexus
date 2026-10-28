package com.gameplay.masterdata.template.trainer;

import com.gameplay.masterdata.attribute.region.WorldRegion;
import com.gameplay.masterdata.attribute.trainer.TrainerClass;
import com.gameplay.masterdata.data.master.registry.pokemon.PokemonRegistry;
import com.gameplay.masterdata.data.master.registry.starpartner.StarPartnerRegistry;
import com.gameplay.masterdata.data.master.registry.trainer.TrainerRegistry;
import com.gameplay.masterdata.template.move.tactic.trainer.TrainerMove;
import com.gameplay.masterdata.template.pokemon.PokemonSpecies;
import com.gameplay.masterdata.template.starpartner.StarPartner;
import com.utils.StringUtils;

import java.util.*;

public class TrainerAlternate {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final String trainerIdentityId;
	private final int trainerAlternateId;
	private final String trainerAlternateCode;
	private final WorldRegion region;
	private final List<TrainerClass> trainerClassList;
	private final Map<Integer, TrainerMove> trainerMoveByIdMap;
	private final Map<String, TrainerMove> trainerMoveByCodeMap;
	private final List<PokemonSpecies> partnerPokemonList;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private TrainerAlternate(Builder builder) {
		this.trainerIdentityId = builder.trainerIdentityId;
		this.trainerAlternateId = builder.trainerAlternateId;
		this.trainerAlternateCode = builder.trainerAlternateCode;
		this.region = builder.region;
		this.trainerClassList = List.copyOf(builder.trainerClassList);
		this.trainerMoveByIdMap = Collections.unmodifiableMap(new LinkedHashMap<>(builder.trainerMoveByIdMap));
		this.trainerMoveByCodeMap = Map.copyOf(builder.trainerMoveByCodeMap);
		this.partnerPokemonList = List.copyOf(builder.partnerPokemonList);
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public TrainerIdentity getTrainerIdentity() {
		return TrainerRegistry.getTrainerIdentityById(trainerIdentityId);
	}

	public int getTrainerAlternateId() {
		return trainerAlternateId;
	}

	public String getTrainerAlternateFullId() {
		return trainerIdentityId + String.format("%03d", trainerAlternateId);
	}

	public String getTrainerAlternateCode() {
		return trainerAlternateCode;
	}

	public String getTrainerAlternateFullCode() {
		if ("CASUAL".equalsIgnoreCase(trainerAlternateCode)) {
			return getTrainerIdentity().getTrainerIdentityCode();
		}
		return getTrainerIdentity().getTrainerIdentityCode() + "_" + trainerAlternateCode;
	}

	public WorldRegion getRegion() {
		return region;
	}

	public List<TrainerClass> getTrainerClassList() {
		return trainerClassList;
	}

	public TrainerClass getTrainerClass(int ordinal) {
		if (ordinal <= 0 || ordinal > trainerClassList.size()) {
			return null;
		}
		return trainerClassList.get(ordinal - 1);
	}

	public TrainerClass getTrainerClass() {
		return getTrainerClass(1);
	}

	public TrainerMove getTrainerMoveById(int moveId) {
		return trainerMoveByIdMap.get(moveId);
	}

	public TrainerMove getTrainerMoveByCode(String moveCode) {
		if (StringUtils.isNullOrBlank(moveCode)) {
			throw new IllegalArgumentException("Missing Trainer Move Code.");
		}

		TrainerMove trainerMove = trainerMoveByCodeMap.get(StringUtils.upperSnakeCase(moveCode));
		if (trainerMove == null) {
			throw new IllegalArgumentException("Failed Search : " + moveCode);
		}

		return trainerMove;
	}

	public Collection<TrainerMove> getAllTrainerMoves() {
		return Collections.unmodifiableCollection(trainerMoveByIdMap.values());
	}

	public StarPartner getStarPartnerByPartnerPokemon(String partnerPokemon) {
		return StarPartnerRegistry.getStarPartnerByCode(getTrainerAlternateFullCode() + "_#_" + partnerPokemon);
	}

	public List<PokemonSpecies> getPartnerPokemonList() {
		return partnerPokemonList;
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public static class Builder {
		private final String trainerIdentityId;
		private final int trainerAlternateId;
		private final String trainerAlternateCode;
		private WorldRegion region;
		private final List<TrainerClass> trainerClassList = new ArrayList<>();
		private final Map<Integer, TrainerMove> trainerMoveByIdMap = new LinkedHashMap<>();
		private final Map<String, TrainerMove> trainerMoveByCodeMap = new HashMap<>();
		private final List<PokemonSpecies> partnerPokemonList = new ArrayList<>();

		public Builder(String trainerIdentityId, int trainerAlternateId, String trainerAlternateCode) {
			this.trainerIdentityId = StringUtils.upperSnakeCase(trainerIdentityId);
			this.trainerAlternateId = trainerAlternateId;
			this.trainerAlternateCode = StringUtils.upperSnakeCase(trainerAlternateCode);
		}

		public Builder setRegion(String regionCode) {
			WorldRegion region = WorldRegion.fromCode(regionCode);
			if (region != null) {
				this.region = region;
			}
			return this;
		}

		public Builder setTrainerClasses(List<String> trainerClassList) {
			if (trainerClassList != null) {
				for (String trainerClassCode : trainerClassList) {
					TrainerClass trainerClass = TrainerClass.fromCode(trainerClassCode);
					if (trainerClass != null) {
						this.trainerClassList.add(trainerClass);
					}
				}
			}
			return this;
		}

		public void addTrainerMove(TrainerMove trainerMove) {
			if (trainerMove != null) {
				int moveId = trainerMove.getMoveIdNo();
				String moveCode = StringUtils.upperSnakeCase(trainerMove.getMoveCode());

				if (!StringUtils.isNullOrBlank(moveCode)) {
					this.trainerMoveByIdMap.put(moveId, trainerMove);
					this.trainerMoveByCodeMap.put(moveCode, trainerMove);
				}
			}
		}

		public Builder setPartnerPokemonList(List<String> partnerList) {
			if (partnerList != null) {
				for (String partner : partnerList) {
					PokemonSpecies partnerPokemon = PokemonRegistry.getPokemonSpeciesByCode(partner);
					if (partnerPokemon != null) {
						this.partnerPokemonList.add(partnerPokemon);
					}
				}
			}
			return this;
		}

		public TrainerAlternate build() {
			return new TrainerAlternate(this);
		}
	}
}
