package com.gameplay.data.play;

import com.gameplay.data.play.collection.ownedstarpartner.OwnedStarPartnerCollection;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class PlayProfile {

	private final UUID trainerId;
	private String profileName;
	private final Instant createTime;
	private final OwnedStarPartnerCollection ownedStarPartnerCollection;

	public PlayProfile(String profileName) {
		this.trainerId = UUID.randomUUID();
		this.profileName = profileName;
		this.createTime = Instant.now();
		this.ownedStarPartnerCollection = new OwnedStarPartnerCollection();
	}

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

	public String getProfileName() {
		return profileName;
	}

	public boolean changeProfileName(String newProfileName) {
		if (profileName.equals(newProfileName)) {
			return false;
		}

		this.profileName = newProfileName;

		return true;
	}

	public OwnedStarPartnerCollection getOwnedStarPartnerCollection() {
		return ownedStarPartnerCollection;
	}
}
