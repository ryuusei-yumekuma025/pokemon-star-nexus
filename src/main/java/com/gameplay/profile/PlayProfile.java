package com.gameplay.profile;

public class PlayProfile {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private String profileName;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	public PlayProfile(String profileName) {
		this.profileName = profileName;
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
}
