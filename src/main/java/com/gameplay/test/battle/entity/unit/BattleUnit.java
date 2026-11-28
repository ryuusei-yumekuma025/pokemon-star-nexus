package com.gameplay.test.battle.entity.unit;

import com.gameplay.battle.entity.unit.profile.UnitProfile;
import com.gameplay.test.battle.entity.unit.action.ActionInterval;
import com.gameplay.data.masterdata.attribute.stat.StatType;
import com.gameplay.data.masterdata.content.EnemyProfile;
import com.gameplay.data.masterdata.data.config.EnergyConfig;
import com.gameplay.data.masterdata.template.move.Move;
import com.gameplay.data.masterdata.template.move.tactic.TacticMove;
import com.gameplay.data.masterdata.template.unit.phase.StarBattlePhase;
import com.gameplay.data.masterdata.template.unit.phase.StarBattleMode;
import com.gameplay.data.play.collection.ownedstarpartner.OwnedStarPartner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BattleUnit {

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	private final String battleId;
	private final String profileId;
	private final UnitProfile unitProfile;
	private final boolean isAlly;
	private final BattleTrainer battleTrainer;
	private final BattlePokemon battlePokemon;

	private StarBattlePhase currentBattlePhase;
	private StarBattleMode currentBattleMode;

	private final ActionInterval actionInterval;

	private int stardust;

	private int syncEther;
	private int maxSyncEther;

	private final List<Move> baseMoveset;
	private List<Move> moveset;

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	public BattleUnit(int slot, boolean isAlly, UnitProfile unitProfile, List<Move> moveset) {
		this.battleId = ((isAlly) ? "AS_" : "OS_") + String.format("%02d", slot);
		this.profileId = unitProfile.getProfileId();
		this.unitProfile = unitProfile;
		this.isAlly = isAlly;
		this.baseMoveset = (moveset == null) ? List.of() : List.copyOf(moveset);

		this.battleTrainer = (unitProfile.getStarUnit().getPartnerTrainer() == null) ? null :
				new BattleTrainer(this, unitProfile.getStarUnit().getPartnerTrainer(),
						unitProfile.getStarUnit().getAllTacticMoves().stream().toList(),
						this.baseMoveset);
		this.currentBattlePhase = unitProfile.getStarUnit().getPhase();
		this.currentBattleMode = this.currentBattlePhase.getDefaultMode();
		this.battlePokemon = new BattlePokemon(this, unitProfile.getStarUnit().getPartnerPokemon(), this.currentBattleMode);

		this.actionInterval = new ActionInterval(this.battlePokemon.getStatTracker().getStat(StatType.SPEED));

		this.stardust = 0;

		this.maxSyncEther = EnergyConfig.BASE_MAX_SYNC_ETHER;
		this.syncEther = this.maxSyncEther;

		rebuildMoveset();
	}

	public BattleUnit(int slot, boolean isAlly, OwnedStarPartner<?> ownedStarPartner) {
		this(slot, isAlly, ownedStarPartner, ownedStarPartner.getMoveset());
	}

	public BattleUnit(int slot, EnemyProfile enemyProfile) {
		this(slot, false, enemyProfile, List.of());
	}

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	public String getBattleId() {
		return battleId;
	}

	public String getProfileId() {
		return profileId;
	}

	public UnitProfile getUnitProfile() {
		return unitProfile;
	}

	public boolean isAlly() {
		return isAlly;
	}

	public BattleTrainer getBattleTrainer() {
		return battleTrainer;
	}

	public BattlePokemon getBattlePokemon() {
		return battlePokemon;
	}

	public boolean isFainted() {
		return battlePokemon.isFainted();
	}

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	public StarBattlePhase getCurrentBattlePhase() {
		return currentBattlePhase;
	}

	public int changePhase(int phase) {
		if (phase <= 0 || phase == currentBattlePhase.getPhaseOrdinal() || phase > getUnitProfile().getStarUnit().getAllPhases().size()) {
			return 0;
		}

		this.currentBattlePhase = getUnitProfile().getStarUnit().getPhaseById(phase);
		int formChange = changeMode(this.currentBattlePhase.getDefaultModeId()) % 10;

		this.battlePokemon.resetStatus();

		return 10 + formChange;
	}

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	public StarBattleMode getCurrentBattleMode() {
		return currentBattleMode;
	}

	public int changeMode(int mode) {
		if (mode < 0 || mode > this.currentBattlePhase.getAllModes().size() || mode == this.currentBattleMode.getModeId()) {
			return 0;
		}

		this.currentBattleMode = this.currentBattlePhase.getModeById(mode);

		int formChange = this.battlePokemon.changeForm(this.currentBattleMode.getPokemonForm());

		this.battlePokemon.getStatTracker().switchBaseStat(this.currentBattleMode.getUnitBaseStat());

		rebuildMoveset();

		return 10 + formChange;
	}

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	private void rebuildMoveset() {
		List<Move> combined = new ArrayList<>(this.currentBattleMode.getMoveset());
		combined.addAll(this.baseMoveset);

		this.moveset = Collections.unmodifiableList(combined);
	}

	public List<Move> getMoveset() {
		return moveset;
	}

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	public boolean useTacticMove(TacticMove tacticMove) {
		if (battleTrainer == null || isFainted()) {
			return false;
		}

		return battleTrainer.performMove(tacticMove);
	}

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	public ActionInterval getActionInterval() {
		return actionInterval;
	}


	/*------------------------------------------------------------------------------------------------------------------
																														*/

	public int getStardust() {
		return stardust;
	}

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	public int getSyncEther() {
		return syncEther;
	}

	public int getMaxSyncEther() {
		return maxSyncEther;
	}

	public int consumeSyncEther(int syncEther) {
		if (syncEther <= 0 || this.syncEther < syncEther) {
			return 0;
		}

		int currentSyncEther = this.syncEther;
		this.syncEther = currentSyncEther - syncEther;

		return syncEther;
	}

	public int restoreSyncEther(int syncEther) {
		if (syncEther <= 0 || this.syncEther == this.maxSyncEther) {
			return 0;
		}

		int currentSyncEther = this.syncEther;
		this.syncEther = Math.min(maxSyncEther, currentSyncEther + syncEther);

		return this.syncEther - currentSyncEther;
	}
}
