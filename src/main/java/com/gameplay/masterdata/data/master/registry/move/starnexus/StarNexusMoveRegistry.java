package com.gameplay.masterdata.data.master.registry.move.starnexus;

import com.gameplay.masterdata.template.move.battle.resonance.ResonanceMove;
import com.gameplay.masterdata.template.move.starnexus.StarNexusMove;
import com.utils.StringUtils;

import java.util.*;

public class StarNexusMoveRegistry {

	//==================================================================================================================
	//	Data Storage
	//==================================================================================================================

	private static final Map<String, StarNexusMove> STAR_NEXUS_MOVE_BY_ID_MAP = new LinkedHashMap<>();
	private static final Map<String, StarNexusMove> STAR_NEXUS_MOVE_BY_CODE_MAP = new HashMap<>();

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private StarNexusMoveRegistry() {}

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

	public static synchronized void register(StarNexusMove starNexusMove) {
		if (starNexusMove == null) {
			return;
		}

		if (frozen) {
			throw new IllegalStateException("StarNexusMoveRegistry is frozen. Failed Register : " + starNexusMove.getMoveCode());
		}

		String moveId = StringUtils.upperSnakeCase(starNexusMove.getMoveId());
		String moveCode = StringUtils.upperSnakeCase(starNexusMove.getMoveCode());

		if (moveId != null && !StringUtils.isNullOrBlank(moveId, moveCode)) {
			moveId = moveId.substring(moveId.indexOf('_') + 1);
			STAR_NEXUS_MOVE_BY_ID_MAP.put(moveId, starNexusMove);
			STAR_NEXUS_MOVE_BY_CODE_MAP.put(moveCode, starNexusMove);
		}
	}

	//==================================================================================================================
	//	Search
	//==================================================================================================================

	public static StarNexusMove getStarNexusMoveById(String moveId) {
		if (StringUtils.isNullOrBlank(moveId)) {
			throw new IllegalArgumentException("Missing Move Id.");
		}

		String id = StringUtils.upperSnakeCase(moveId);
		if (id == null) {
			throw new IllegalArgumentException("Missing Move Id.");
		}

		if (id.contains("SNM_")) {
			id = id.substring(id.indexOf("SNM_" + 4));
		}

		StarNexusMove starNexusMove = STAR_NEXUS_MOVE_BY_ID_MAP.get(id);
		if (starNexusMove == null) {
			throw new IllegalArgumentException("Failed Star Nexus Move Search : " + moveId);
		}

		return starNexusMove;
	}

	public static StarNexusMove getStarNexusMoveByCode(String moveCode) {
		if (StringUtils.isNullOrBlank(moveCode)) {
			throw new IllegalArgumentException("Missing Move Code.");
		}

		StarNexusMove starNexusMove = STAR_NEXUS_MOVE_BY_CODE_MAP.get(StringUtils.upperSnakeCase(moveCode));
		if (starNexusMove == null) {
			throw new IllegalArgumentException("Failed Star Nexus Move Search : " + moveCode);
		}

		return starNexusMove;
	}

	public static Collection<StarNexusMove> getAllStarNexusMoves() {
		return Collections.unmodifiableCollection(STAR_NEXUS_MOVE_BY_ID_MAP.values());
	}

	public static int getNumberOfStarNexusMoves() {
		return STAR_NEXUS_MOVE_BY_ID_MAP.size();
	}

	//==================================================================================================================
	//	Clear and Reset
	//==================================================================================================================

	public static synchronized void clear() {
		if (frozen) {
			throw new IllegalStateException("StarNexusMoveRegistry is frozen. Cannot Clear Registry.");
		}

		STAR_NEXUS_MOVE_BY_ID_MAP.clear();
		STAR_NEXUS_MOVE_BY_CODE_MAP.clear();
	}

	public static synchronized void reset() {
		frozen = false;
		clear();
	}
}
