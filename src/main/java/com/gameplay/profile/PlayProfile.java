package com.gameplay.profile;

import com.gameplay.masterdata.template.starpartner.StarPartner;
import com.gameplay.profile.entity.starpartner.OwnedStarPartner;
import com.gameplay.profile.entity.starpartner.OwnedStarPartnerStorage;
import com.gameplay.profile.entity.team.TeamPreset;
import com.gameplay.profile.entity.team.TeamPresetStorage;
import com.utils.StringUtils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PlayProfile {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private static final int MAX_TEAM_PRESET = 50;

	private final UUID trainerId;
	private String profileName;
	private final Instant createTime;

	private final OwnedStarPartnerStorage ownedStarPartnerStorage;
	private final TeamPresetStorage teamPresetStorage;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	public PlayProfile(String profileName, Instant createTime) {
		this.trainerId = UUID.randomUUID();
		this.profileName = profileName;
		this.createTime = createTime;

		this.ownedStarPartnerStorage = new OwnedStarPartnerStorage();
		this.teamPresetStorage = new TeamPresetStorage();
	}

	public PlayProfile(String profileName) {
		this(profileName, Instant.now());
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public UUID getTrainerId() {
		return trainerId;
	}

	public Instant getCreateTime() {
		return createTime;
	}

	public String getCreateDate() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.systemDefault());
		return dateTimeFormatter.format(createTime);
	}

	public String getCreateDateAndTime() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy").withZone(ZoneId.systemDefault());
		return dateTimeFormatter.format(createTime);
	}

	public OwnedStarPartnerStorage getOwnedStarPartnerStorage() {
		return ownedStarPartnerStorage;
	}

	public TeamPresetStorage getTeamPresetStorage() {
		return teamPresetStorage;
	}

	//==================================================================================================================
	//	Name Management
	//==================================================================================================================

	public String getProfileName() {
		return profileName;
	}

	public boolean isValidName(String profileName) {
		return profileName != null && profileName.length() <= StringUtils.MAX_NAME_LENGTH;
	}

	public void changeName(String profileName) {
		if (isValidName(profileName)) {
			this.profileName = profileName;
		}
	}
}
