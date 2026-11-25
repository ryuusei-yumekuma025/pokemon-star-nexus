package org.example;

import com.gameplay.data.masterdata.data.master.GameMasterDataBootstrap;
import com.gameplay.data.masterdata.data.master.registry.unit.enemy.StarEnemyRegistry;
import com.gameplay.data.masterdata.data.master.registry.unit.starpartner.StandbyStarPartnerRegistry;
import com.gameplay.data.masterdata.template.unit.type.enemy.StarEnemy;
import com.gameplay.data.masterdata.template.unit.type.playable.standby.StandbyStarPartner;
import com.gameplay.data.play.PlayProfile;

public class Main {
	static void main() {
		GameMasterDataBootstrap.reload();

		PlayProfile playProfile = new PlayProfile("Ryuusei");

		playProfile.getOwnedStarPartnerCollection().uniteStandbyStarPartner(StandbyStarPartnerRegistry.getStarPartnerByCode("Ethan and Typhlosion"));

		playProfile.getTeamPresetStorage().createNewTeamPreset();

		playProfile.getTeamPresetStorage().getActiveTeamPreset().joinTeam(playProfile.getOwnedStarPartnerCollection().getStarPartner(StandbyStarPartnerRegistry.getStarPartnerByCode("Ethan and Typhlosion")));




	}
}
