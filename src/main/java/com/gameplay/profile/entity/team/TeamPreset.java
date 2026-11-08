package com.gameplay.profile.entity.team;

import com.gameplay.profile.entity.starpartner.OwnedStarPartner;
import com.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamPreset {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private static final int MAX_TEAM_SLOTS = 3;

	private final int teamNumber;
	private String teamName;
	private final List<OwnedStarPartner> starPartners;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	public TeamPreset(int teamNumber) {
		this.teamNumber = teamNumber;
		this.teamName = "#TEAM" + teamNumber;
		this.starPartners = new ArrayList<>();
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public int getTeamNumber() {
		return teamNumber;
	}

	public String getTeamName() {
		return teamName;
	}

	public List<OwnedStarPartner> getStarPartners() {
		return Collections.unmodifiableList(starPartners);
	}

	public int getTeammateNumber() {
		return starPartners.size();
	}

	public boolean isFullTeam() {
		return starPartners.size() >= MAX_TEAM_SLOTS;
	}

	public boolean isEmpty() {
		return starPartners.isEmpty();
	}

	public OwnedStarPartner getStarPartner(int teamSlot) {
		if (teamSlot <= 0 || teamSlot > getTeammateNumber()) {
			return null;
		}
		return starPartners.get(teamSlot - 1);
	}

	//==================================================================================================================
	//	Name Management
	//==================================================================================================================

	public boolean isValidName(String teamName) {
		return teamName != null && teamName.length() <= StringUtils.MAX_NAME_LENGTH;
	}

	public void changeName(String teamName) {
		if (isValidName(teamName)) {
			this.teamName = teamName;
		}
	}

	//==================================================================================================================
	//	Team Join
	//==================================================================================================================

	public void joinTeam(OwnedStarPartner starPartner) {
		if (starPartner == null || isFullTeam() || starPartners.contains(starPartner)) {
			return;
		}

		this.starPartners.add(starPartner);
	}

	public void leaveTeam(int teamSlot) {
		if (teamSlot <= 0 || teamSlot > starPartners.size()) {
			return;
		}

		this.starPartners.remove(teamSlot - 1);
	}

	public void swap(int slotA, int slotB) {
		if (slotA == slotB || slotA <= 0 || slotB <= 0 || slotA > starPartners.size() || slotB > starPartners.size()) {
			return;
		}

		slotA--;
		slotB--;

		Collections.swap(starPartners, slotA, slotB);
	}

	public void swapFromStorage(OwnedStarPartner starPartner, int slot) {
		if (starPartner == null || slot <= 0 || slot > starPartners.size() || starPartners.contains(starPartner)) {
			return;
		}

		this.starPartners.set(slot - 1, starPartner);
	}
}
