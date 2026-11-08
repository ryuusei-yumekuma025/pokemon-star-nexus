package com.gameplay.profile.entity.team;

import java.util.ArrayList;
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
		TeamPreset teamPreset = new TeamPreset(1);
		this.teamPresetList.add(teamPreset);
		this.activeTeamPreset = 1;
	}

	//==================================================================================================================
	//	Team Preset Storage Management
	//==================================================================================================================

	public List<TeamPreset> getTeamPresetList() {
		return teamPresetList;
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
	}

	public void removeTeamPreset(int id) {
		if (id <= 0 || id > teamPresetList.size()) {
			return;
		}

		if (id >= teamPresetList.size()) {
			this.activeTeamPreset--;
		}

		this.teamPresetList.remove(id - 1);
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
