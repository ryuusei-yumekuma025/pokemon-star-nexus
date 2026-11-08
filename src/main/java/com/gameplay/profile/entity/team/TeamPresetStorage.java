package com.gameplay.profile.entity.team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamPresetStorage {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final List<TeamPreset> teamPresetList;
	private int activeTeamPreset;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	public TeamPresetStorage() {
		this.teamPresetList = new ArrayList<>();
		reset();
	}

	//==================================================================================================================
	//	Team Preset Storage Management
	//==================================================================================================================

	public List<TeamPreset> getTeamPresetList() {
		return Collections.unmodifiableList(teamPresetList);
	}

	public TeamPreset getTeamPreset(int id) {
		if (id <= 0 || id > teamPresetList.size()) {
			return null;
		}

		return teamPresetList.get(id - 1);
	}

	public void createNewTeamPreset() {
		TeamPreset teamPreset = new TeamPreset(this.teamPresetList.size() + 1);
		this.teamPresetList.add(teamPreset);
		this.activeTeamPreset = this.teamPresetList.size();
	}

	public void removeTeamPreset(int id) {
		if (id <= 0 || id > teamPresetList.size()) {
			return;
		}

		if (teamPresetList.size() == 1) {
			reset();
			return;
		}

		this.teamPresetList.remove(id - 1);

		if (id < activeTeamPreset) {
			this.activeTeamPreset--;
		} else if (activeTeamPreset > teamPresetList.size()) {
			this.activeTeamPreset = teamPresetList.size();
		}
	}

	public void removeTeamPreset(List<Integer> idList) {
		List<Integer> sortedIdList = new ArrayList<>(idList);
		sortedIdList.removeIf(id -> id == null || id <= 0 || id > teamPresetList.size());
		sortedIdList.sort(Integer::compareTo);

		int misaligned = 0;

		for (int id : sortedIdList) {
			removeTeamPreset(id - misaligned);
			misaligned++;
		}
	}

	public void reset() {
		this.teamPresetList.clear();
		this.teamPresetList.add(new TeamPreset(1));
		this.activeTeamPreset = 1;
	}

	//==================================================================================================================
	//	Active Team Preset Management
	//==================================================================================================================

	public TeamPreset getActiveTeamPreset() {
		return getTeamPreset(activeTeamPreset);
	}

	public void setActiveTeamPreset(int teamNumber) {
		if (teamNumber <= 0 || teamNumber > teamPresetList.size()) {
			return;
		}

		this.activeTeamPreset = teamNumber;
	}
}
