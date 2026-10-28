package com.gameplay.masterdata.data.master.registry.move.tactic;

import com.gameplay.masterdata.template.move.tactic.trainer.TrainerMove;
import com.utils.StringUtils;

import java.util.*;

public class TrainerMoveRegistry {

	//==================================================================================================================
	//	Data Storage
	//==================================================================================================================

	private static final Map<String, TrainerMove> TRAINER_MOVE_BY_ID_MAP = new LinkedHashMap<>();
	private static final Map<String, TrainerMove> TRAINER_MOVE_BY_CODE_MAP = new HashMap<>();

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private TrainerMoveRegistry() {}

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

	public static synchronized void register(TrainerMove trainerMove) {
		if (trainerMove == null) {
			return;
		}

		if (frozen) {
			throw new IllegalStateException("TrainerMoveRegistry is frozen. Failed Register : " + trainerMove.getMoveCode());
		}

		String moveId = StringUtils.upperSnakeCase(trainerMove.getMoveId());
		String moveCode = StringUtils.upperSnakeCase(trainerMove.getMoveCode());

		if (moveId != null && !StringUtils.isNullOrBlank(moveId, moveCode)) {
			moveId = moveId.substring(moveId.indexOf('_') + 1);
			TRAINER_MOVE_BY_ID_MAP.put(moveId, trainerMove);
			TRAINER_MOVE_BY_CODE_MAP.put(moveCode, trainerMove);
		}
	}

	//==================================================================================================================
	//	Search
	//==================================================================================================================

	public static TrainerMove getTrainerMoveById(String moveId) {
		if (StringUtils.isNullOrBlank(moveId)) {
			throw new IllegalArgumentException("Missing Move Id.");
		}

		String id = StringUtils.upperSnakeCase(moveId);
		if (id == null) {
			throw new IllegalArgumentException("Missing Move Id.");
		}

		if (id.contains("TM_")) {
			id = id.substring(id.indexOf("TM_" + 3));
		}

		TrainerMove trainerMove = TRAINER_MOVE_BY_ID_MAP.get(id);
		if (trainerMove == null) {
			throw new IllegalArgumentException("Failed Trainer Move Search : " + moveId);
		}

		return trainerMove;
	}

	public static TrainerMove getTrainerMoveByCode(String moveCode) {
		if (StringUtils.isNullOrBlank(moveCode)) {
			throw new IllegalArgumentException("Missing Move Code.");
		}

		TrainerMove trainerMove = TRAINER_MOVE_BY_CODE_MAP.get(StringUtils.upperSnakeCase(moveCode));
		if (trainerMove == null) {
			throw new IllegalArgumentException("Failed Trainer Move Search : " + moveCode);
		}

		return trainerMove;
	}

	public static Collection<TrainerMove> getAllTrainerMoves() {
		return Collections.unmodifiableCollection(TRAINER_MOVE_BY_ID_MAP.values());
	}

	public static int getNumberOfTrainerMoves() {
		return TRAINER_MOVE_BY_ID_MAP.size();
	}

	//==================================================================================================================
	//	Clear and Reset
	//==================================================================================================================

	public static synchronized void clear() {
		if (frozen) {
			throw new IllegalStateException("TrainerMoveRegistry is frozen. Cannot Clear Registry.");
		}

		TRAINER_MOVE_BY_ID_MAP.clear();
		TRAINER_MOVE_BY_CODE_MAP.clear();
	}

	public static synchronized void reset() {
		frozen = false;
		clear();
	}
}
