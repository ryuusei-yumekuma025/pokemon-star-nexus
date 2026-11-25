package com.gameplay.data.masterdata.template.unit.phase;

import com.utils.IdCodeKeyMap;

import java.util.Collection;

public class StarBattlePhase {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final int phaseOrdinal;
	private final String phaseCode;
	private final int defaultState;
	private final IdCodeKeyMap<Integer, StarBattleState> starBattleStates;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private StarBattlePhase(Builder builder) {
		this.phaseOrdinal = builder.phaseOrdinal;
		this.phaseCode = builder.phaseCode;
		this.defaultState = builder.defaultState;
		this.starBattleStates = builder.starBattleStates.build();
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public int getPhaseOrdinal() {
		return phaseOrdinal;
	}

	public String getPhaseCode() {
		return phaseCode;
	}

	public StarBattleState getStateById(int id) {
		return starBattleStates.getById(id);
	}

	public StarBattleState getStateByCode(String code) {
		return starBattleStates.getByCode(code);
	}

	public int getDefaultStateId() {
		return defaultState;
	}

	public StarBattleState getDefaultState() {
		return getStateById(defaultState);
	}

	public Collection<StarBattleState> getAllStates() {
		return starBattleStates.getAll();
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Builder																											*/

	public static class Builder {
		private final int phaseOrdinal;
		private final String phaseCode;
		private final int defaultState;
		private final IdCodeKeyMap.Builder<Integer, StarBattleState> starBattleStates = new IdCodeKeyMap.Builder<>();

		public Builder(int phaseOrdinal, String phaseCode, int defaultState) {
			this.phaseOrdinal = phaseOrdinal;
			this.phaseCode = phaseCode;
			this.defaultState = defaultState;
		}

		public void addState(StarBattleState starBattleState) {
			if (starBattleState != null) {
				this.starBattleStates.put(starBattleState.getStateId(), starBattleState.getStateCode(), starBattleState);
			}
		}

		public StarBattlePhase build() {
			return new StarBattlePhase(this);
		}
	}
}
