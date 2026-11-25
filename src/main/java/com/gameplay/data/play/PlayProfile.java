package com.gameplay.data.play;

import com.gameplay.data.play.collection.ownedstarpartner.OwnedStarPartnerCollection;

public class PlayProfile {
	private final OwnedStarPartnerCollection ownedStarPartnerCollection;

	public PlayProfile() {
		this.ownedStarPartnerCollection = new OwnedStarPartnerCollection();
	}

	public OwnedStarPartnerCollection getOwnedStarPartnerCollection() {
		return ownedStarPartnerCollection;
	}
}
