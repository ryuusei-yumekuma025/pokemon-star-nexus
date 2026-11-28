package com.gameplay.data.masterdata.template.unit.phase;

import com.utils.IdCodeKeyMap;

import java.util.Collection;

public class StarBattlePhase {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final int phaseOrdinal;
	private final String phaseCode;
	private final int defaultMode;
	private final IdCodeKeyMap<Integer, StarBattleMode> starBattleModes;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private StarBattlePhase(Builder builder) {
		this.phaseOrdinal = builder.phaseOrdinal;
		this.phaseCode = builder.phaseCode;
		this.defaultMode = builder.defaultMode;
		this.starBattleModes = builder.starBattleModes.build();
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public int getPhaseOrdinal() {
		return phaseOrdinal;
	}

	public String getPhaseCode() {
		return phaseCode;
	}

	public StarBattleMode getModeById(int id) {
		return starBattleModes.getById(id);
	}

	public StarBattleMode getModeByCode(String code) {
		return starBattleModes.getByCode(code);
	}

	public int getDefaultModeId() {
		return defaultMode;
	}

	public StarBattleMode getDefaultMode() {
		return getModeById(defaultMode);
	}

	public Collection<StarBattleMode> getAllModes() {
		return starBattleModes.getAll();
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Builder																											*/

	public static class Builder {
		private final int phaseOrdinal;
		private final String phaseCode;
		private final int defaultMode;
		private final IdCodeKeyMap.Builder<Integer, StarBattleMode> starBattleModes = new IdCodeKeyMap.Builder<>();

		public Builder(int phaseOrdinal, String phaseCode, int defaultMode) {
			this.phaseOrdinal = phaseOrdinal;
			this.phaseCode = phaseCode;
			this.defaultMode = defaultMode;
		}

		public void addMode(StarBattleMode starBattleMode) {
			if (starBattleMode != null) {
				this.starBattleModes.put(starBattleMode.getModeId(), starBattleMode.getModeCode(), starBattleMode);
			}
		}

		public StarBattlePhase build() {
			return new StarBattlePhase(this);
		}
	}
}
