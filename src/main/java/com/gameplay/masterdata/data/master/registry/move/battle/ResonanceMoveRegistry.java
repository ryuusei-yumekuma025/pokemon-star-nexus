package com.gameplay.masterdata.data.master.registry.move.battle;

import com.gameplay.masterdata.template.move.battle.resonance.ResonanceMove;
import com.utils.StringUtils;

import java.util.*;

public class ResonanceMoveRegistry {

	//==================================================================================================================
	//	Data Storage
	//==================================================================================================================

	private static final Map<String, ResonanceMove> RESONANCE_MOVE_BY_ID_MAP = new LinkedHashMap<>();
	private static final Map<String, ResonanceMove> RESONANCE_MOVE_BY_CODE_MAP = new HashMap<>();

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private ResonanceMoveRegistry() {}

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

	public static synchronized void register(ResonanceMove resonanceMove) {
		if (resonanceMove == null) {
			return;
		}

		if (frozen) {
			throw new IllegalStateException("ResonanceMoveRegistry is frozen. Failed Register : " + resonanceMove.getMoveCode());
		}

		String moveId = StringUtils.upperSnakeCase(resonanceMove.getMoveId());
		String moveCode = StringUtils.upperSnakeCase(resonanceMove.getMoveCode());

		if (moveId != null && !StringUtils.isNullOrBlank(moveId, moveCode)) {
			moveId = moveId.substring(moveId.indexOf('_') + 1);
			RESONANCE_MOVE_BY_ID_MAP.put(moveId, resonanceMove);
			RESONANCE_MOVE_BY_CODE_MAP.put(moveCode, resonanceMove);
		}
	}

	//==================================================================================================================
	//	Search
	//==================================================================================================================

	public static ResonanceMove getResonanceMoveById(String moveId) {
		if (StringUtils.isNullOrBlank(moveId)) {
			throw new IllegalArgumentException("Missing Move Id.");
		}

		String id = StringUtils.upperSnakeCase(moveId);
		if (id == null) {
			throw new IllegalArgumentException("Missing Move Id.");
		}

		if (id.contains("RM_")) {
			id = id.substring(id.indexOf("RM_" + 3));
		}

		ResonanceMove resonanceMove = RESONANCE_MOVE_BY_ID_MAP.get(id);
		if (resonanceMove == null) {
			throw new IllegalArgumentException("Failed Resonance Move Search : " + moveId);
		}

		return resonanceMove;
	}

	public static ResonanceMove getResonanceMoveByCode(String moveCode) {
		if (StringUtils.isNullOrBlank(moveCode)) {
			throw new IllegalArgumentException("Missing Move Code.");
		}

		ResonanceMove resonanceMove = RESONANCE_MOVE_BY_CODE_MAP.get(StringUtils.upperSnakeCase(moveCode));
		if (resonanceMove == null) {
			throw new IllegalArgumentException("Failed Resonance Move Search : " + moveCode);
		}

		return resonanceMove;
	}

	public static Collection<ResonanceMove> getAllResonanceMoves() {
		return Collections.unmodifiableCollection(RESONANCE_MOVE_BY_ID_MAP.values());
	}

	public static int getNumberOfResonanceMoves() {
		return RESONANCE_MOVE_BY_ID_MAP.size();
	}

	//==================================================================================================================
	//	Clear and Reset
	//==================================================================================================================

	public static synchronized void clear() {
		if (frozen) {
			throw new IllegalStateException("ResonanceMoveRegistry is frozen. Cannot Clear Registry.");
		}

		RESONANCE_MOVE_BY_ID_MAP.clear();
		RESONANCE_MOVE_BY_CODE_MAP.clear();
	}

	public static synchronized void reset() {
		frozen = false;
		clear();
	}
}
