package org.example;

import com.gameplay.data.masterdata.data.master.GameMasterDataBootstrap;
import com.gameplay.data.masterdata.data.master.registry.unit.enemy.StarEnemyRegistry;
import com.gameplay.data.masterdata.template.unit.type.enemy.StarEnemy;
import com.gameplay.data.play.PlayProfile;

public class Main {
	static void main() {
		GameMasterDataBootstrap.reload();

		PlayProfile playProfile = new PlayProfile("Ryuusei");

		System.out.println(playProfile.getCreateDateAndTime());
	}
}
