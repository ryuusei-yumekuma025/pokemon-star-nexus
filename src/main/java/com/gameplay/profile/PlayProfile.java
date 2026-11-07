package com.gameplay.profile;

import com.gameplay.masterdata.template.starpartner.StarPartner;
import com.gameplay.profile.unit.starpartner.OwnedStarPartner;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class PlayProfile {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final UUID trainerId;
	private String profileName;
	private final Instant createTime;

	private final Map<String, OwnedStarPartner> ownedStarPartnerMap;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	public PlayProfile(String profileName, Instant createTime) {
		this.trainerId = UUID.randomUUID();
		this.profileName = profileName;
		this.createTime = createTime;

		this.ownedStarPartnerMap = new LinkedHashMap<>();
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
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.systemDefault());
		return dateTimeFormatter.format(createTime);
	}

	public String getCreateDateAndTime() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy").withZone(ZoneId.systemDefault());
		return dateTimeFormatter.format(createTime);
	}

	//==================================================================================================================
	//	Name Management
	//==================================================================================================================

	public String getProfileName() {
		return profileName;
	}

	public boolean isValidName(String profileName) {
		if (profileName.length() > 20) {
			return false;
		}

		return true;
	}

	public void changeName(String profileName) {
		if (isValidName(profileName)) {
			this.profileName = profileName;
		}
	}

	//==================================================================================================================
	//	Star Partner Management
	//==================================================================================================================

	public Map<String, OwnedStarPartner> getOwnedStarPartnerMap() {
		return ownedStarPartnerMap;
	}

	public void starPartnerUnite(StarPartner starPartner) {
		if (ownedStarPartnerMap.containsKey(starPartner.getStarPartnerId())) {
			return;
		}

		this.ownedStarPartnerMap.put(starPartner.getStarPartnerId(), new OwnedStarPartner(starPartner));
	}
}
