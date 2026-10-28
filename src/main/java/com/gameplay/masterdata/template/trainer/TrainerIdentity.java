package com.gameplay.masterdata.template.trainer;

import com.utils.StringUtils;

import java.util.*;

public class TrainerIdentity {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final String trainerIdentityId;
	private final String trainerIdentityCode;
	private final Map<Integer, TrainerAlternate> trainerAlternateByIdMap;
	private final Map<String, TrainerAlternate> trainerAlternateByCodeMap;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private TrainerIdentity(Builder builder) {
		this.trainerIdentityId = builder.trainerIdentityId;
		this.trainerIdentityCode = builder.trainerIdentityCode;
		this.trainerAlternateByIdMap = Collections.unmodifiableMap(new LinkedHashMap<>(builder.trainerAlternateByIdMap));
		this.trainerAlternateByCodeMap = Map.copyOf(builder.trainerAlternateByCodeMap);
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public String getTrainerIdentityId() {
		return trainerIdentityId;
	}

	public String getTrainerIdentityCode() {
		return trainerIdentityCode;
	}

	public TrainerAlternate getAlternateById(int alternateId) {
		return trainerAlternateByIdMap.get(alternateId);
	}

	public TrainerAlternate getAlternateByCode(String alternateCode) {
		if (StringUtils.isNullOrBlank(alternateCode)) {
			return null;
		}
		return trainerAlternateByCodeMap.get(StringUtils.upperSnakeCase(alternateCode));
	}

	public Collection<TrainerAlternate> getAllAlternates() {
		return Collections.unmodifiableCollection(trainerAlternateByIdMap.values());
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public static class Builder {
		private final String trainerIdentityId;
		private final String trainerIdentityCode;
		private final Map<Integer, TrainerAlternate> trainerAlternateByIdMap = new LinkedHashMap<>();
		private final Map<String, TrainerAlternate> trainerAlternateByCodeMap = new HashMap<>();

		public Builder(String trainerIdentityId, String trainerIdentityCode) {
			this.trainerIdentityId = StringUtils.upperSnakeCase(trainerIdentityId);
			this.trainerIdentityCode = StringUtils.upperSnakeCase(trainerIdentityCode);
		}

		public void addTrainerAlternate(TrainerAlternate trainerAlternate) {
			if (trainerAlternate != null) {
				int alternateId = trainerAlternate.getTrainerAlternateId();
				String alternateCode = StringUtils.upperSnakeCase(trainerAlternate.getTrainerAlternateCode());

				if (!StringUtils.isNullOrBlank(alternateCode)) {
					this.trainerAlternateByIdMap.put(alternateId, trainerAlternate);
					this.trainerAlternateByCodeMap.put(alternateCode, trainerAlternate);
				}
			}
		}

		public TrainerIdentity build() {
			return new TrainerIdentity(this);
		}
	}
}
