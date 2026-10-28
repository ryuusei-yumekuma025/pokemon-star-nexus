package com.gameplay.masterdata.data.master.registry.trainer;

import com.gameplay.masterdata.template.trainer.TrainerAlternate;
import com.gameplay.masterdata.template.trainer.TrainerIdentity;
import com.utils.StringUtils;

import java.util.*;

public class TrainerRegistry {

	//==================================================================================================================
	//	Data Storage
	//==================================================================================================================

	private static final Map<String, TrainerIdentity> TRAINER_IDENTITY_BY_ID_MAP = new LinkedHashMap<>();
	private static final Map<String, TrainerIdentity> TRAINER_IDENTITY_BY_CODE_MAP = new HashMap<>();

	private static final Map<String, TrainerAlternate> TRAINER_ALTERNATE_BY_ID_MAP = new LinkedHashMap<>();
	private static final Map<String, TrainerAlternate> TRAINER_ALTERNATE_BY_CODE_MAP = new HashMap<>();

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private TrainerRegistry() {}

	//==================================================================================================================
	//	Frozen State Management
	//==================================================================================================================

	private static volatile boolean frozen = false;

	public static synchronized void freeze() {
		frozen = true;
	}

	//==================================================================================================================
	//	Register
	//==================================================================================================================

	public static synchronized void register(TrainerIdentity trainerIdentity) {
		if (trainerIdentity == null) {
			return;
		}

		if (frozen) {
			throw new IllegalStateException("TrainerRegistry is frozen. Failed Register : " + trainerIdentity.getTrainerIdentityCode());
		}

		String identityId = StringUtils.upperSnakeCase(trainerIdentity.getTrainerIdentityId());
		String identityCode = StringUtils.upperSnakeCase(trainerIdentity.getTrainerIdentityCode());

		if (!StringUtils.isNullOrBlank(identityId, identityCode)) {
			TRAINER_IDENTITY_BY_ID_MAP.put(identityId, trainerIdentity);
			TRAINER_IDENTITY_BY_CODE_MAP.put(identityCode, trainerIdentity);

			for (TrainerAlternate trainerAlternate : trainerIdentity.getAllAlternates()) {
				String alternateId = StringUtils.upperSnakeCase(trainerAlternate.getTrainerAlternateFullId());
				String alternateCode = StringUtils.upperSnakeCase(trainerAlternate.getTrainerAlternateCode());

				if ("CASUAL".equals(alternateCode)) {
					alternateCode = identityCode;
				} else {
					alternateCode = identityCode + "_" + alternateCode;
				}

				if (!StringUtils.isNullOrBlank(alternateId, alternateCode)) {
					TRAINER_ALTERNATE_BY_ID_MAP.put(alternateId, trainerAlternate);
					TRAINER_ALTERNATE_BY_CODE_MAP.put(alternateCode, trainerAlternate);
				}
			}
		}
	}

	//==================================================================================================================
	//	Search Identity
	//==================================================================================================================

	public static TrainerIdentity getTrainerIdentityById(String identityId) {
		if (StringUtils.isNullOrBlank(identityId)) {
			throw new IllegalArgumentException("Missing Identity Id.");
		}

		TrainerIdentity trainerIdentity = TRAINER_IDENTITY_BY_ID_MAP.get(StringUtils.upperSnakeCase(identityId));
		if (trainerIdentity == null) {
			throw new IllegalArgumentException("Failed Trainer Identity Search : " + identityId);
		}

		return trainerIdentity;
	}

	public static TrainerIdentity getTrainerIdentityByCode(String identityCode) {
		if (StringUtils.isNullOrBlank(identityCode)) {
			throw new IllegalArgumentException("Missing Identity Code.");
		}

		TrainerIdentity trainerIdentity = TRAINER_IDENTITY_BY_CODE_MAP.get(StringUtils.upperSnakeCase(identityCode));
		if (trainerIdentity == null) {
			throw new IllegalArgumentException("Failed Trainer Identity Search : " + identityCode);
		}

		return trainerIdentity;
	}

	public static Collection<TrainerIdentity> getAllTrainerIdentities() {
		return Collections.unmodifiableCollection(TRAINER_IDENTITY_BY_ID_MAP.values());
	}

	public static int getNumberOfTrainerIdentities() {
		return TRAINER_IDENTITY_BY_ID_MAP.size();
	}

	//==================================================================================================================
	//	Search Alternate
	//==================================================================================================================

	public static TrainerAlternate getTrainerAlternateById(String alternateId) {
		if (StringUtils.isNullOrBlank(alternateId)) {
			throw new IllegalArgumentException("Missing Alternate Id.");
		}

		TrainerAlternate trainerAlternate = TRAINER_ALTERNATE_BY_ID_MAP.get(StringUtils.upperSnakeCase(alternateId));
		if (trainerAlternate == null) {
			throw new IllegalArgumentException("Failed Trainer Alternate Search : " + alternateId);
		}

		return trainerAlternate;
	}

	public static TrainerAlternate getTrainerAlternateByCode(String alternateCode) {
		if (StringUtils.isNullOrBlank(alternateCode)) {
			throw new IllegalArgumentException("Missing Alternate Code.");
		}

		TrainerAlternate trainerAlternate = TRAINER_ALTERNATE_BY_CODE_MAP.get(StringUtils.upperSnakeCase(alternateCode));
		if (trainerAlternate == null) {
			throw new IllegalArgumentException("Failed Trainer Alternate Search : " + alternateCode);
		}

		return trainerAlternate;
	}

	public static Collection<TrainerAlternate> getAllTrainerAlternates() {
		return Collections.unmodifiableCollection(TRAINER_ALTERNATE_BY_ID_MAP.values());
	}

	public static int getNumberOfTrainerAlternates() {
		return TRAINER_ALTERNATE_BY_ID_MAP.size();
	}

	//==================================================================================================================
	//	Clear and Reset
	//==================================================================================================================

	public static synchronized void clear() {
		if (frozen) {
			throw new IllegalStateException("TrainerRegistry is frozen. Cannot Clear Registry.");
		}

		TRAINER_IDENTITY_BY_ID_MAP.clear();
		TRAINER_IDENTITY_BY_CODE_MAP.clear();
		TRAINER_ALTERNATE_BY_ID_MAP.clear();
		TRAINER_ALTERNATE_BY_CODE_MAP.clear();
	}

	public static synchronized void reset() {
		frozen = false;
		clear();
	}
}
