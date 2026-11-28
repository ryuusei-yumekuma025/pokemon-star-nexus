package com.gameplay.battle.entity.unit;

import com.gameplay.battle.entity.unit.action.ActionClock;
import com.gameplay.battle.entity.unit.profile.UnitProfile;
import com.gameplay.data.masterdata.data.config.EnergyConfig;
import com.gameplay.data.masterdata.template.move.Move;
import com.gameplay.data.masterdata.template.unit.phase.StarBattlePhase;
import com.gameplay.data.masterdata.template.unit.phase.StarBattleMode;
import com.gameplay.data.play.collection.ownedstarpartner.OwnedStarPartner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BattleUnit {

	/*------------------------------------------------------------------------------------------------------------------
	.	Fields																											*/

	private final String battleId;
	private final UnitProfile unitProfile;
	private final boolean isAlly;
	private final BattleTrainer partnerTrainer;
	private final BattlePokemon partnerPokemon;

	private StarBattlePhase currentBattlePhase;
	private StarBattleMode currentBattleMode;

	private final ActionClock actionClock;

	private int syncEther;
	private int maxSyncEther;

	private final List<Move> baseMoveset;
	private List<Move> moveset;

	public BattleUnit(int slot, boolean isAlly, UnitProfile unitProfile, List<Move> baseMoveset) {
		this.battleId = ((isAlly) ? "AS_" : "OS_") + String.format("%02d", slot);
		this.unitProfile = unitProfile;
		this.isAlly = isAlly;
		this.baseMoveset = (baseMoveset == null) ? List.of() : List.copyOf(baseMoveset);

		this.partnerTrainer = (unitProfile.getStarUnit().getPartnerTrainer() == null) ? null :
				new BattleTrainer(this, unitProfile.getStarUnit().getPartnerTrainer(),
										unitProfile.getStarUnit().getAllTacticMoves().stream().toList(),
										this.baseMoveset);
		this.currentBattlePhase = unitProfile.getStarUnit().getPhase();
		this.currentBattleMode = this.currentBattlePhase.getDefaultMode();
		this.partnerPokemon = new BattlePokemon(this, unitProfile.getStarUnit().getPartnerPokemon(), this.currentBattleMode);

		this.actionClock = new ActionClock();

		this.maxSyncEther = EnergyConfig.BASE_MAX_SYNC_ETHER;
		this.syncEther = this.maxSyncEther;

		rebuildMoveset();
	}

	public BattleUnit(int slot, UnitProfile unitProfile) {
		boolean category = unitProfile instanceof OwnedStarPartner<?> ownedStarPartner;
		this(slot, category, unitProfile, (category) ? ((OwnedStarPartner<?>) unitProfile).getMoveset() : List.of());
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Getters																											*/

	public String getBattleId() {
		return battleId;
	}

	public UnitProfile getUnitProfile() {
		return unitProfile;
	}

	public boolean isAlly() {
		return isAlly;
	}

	public BattleTrainer getPartnerTrainer() {
		return partnerTrainer;
	}

	public BattlePokemon getPartnerPokemon() {
		return partnerPokemon;
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Action Handler																									*/

	public ActionClock getActionTick() {
		return actionClock;
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Mode Controller																								*/

	public StarBattleMode getCurrentBattleMode() {
		return currentBattleMode;
	}

	public int changeMode(int mode) {
		if (mode < 0 || mode > this.currentBattlePhase.getAllModes().size() || mode == this.currentBattleMode.getModeId()) {
			return 0;
		}

		this.currentBattleMode = this.currentBattlePhase.getModeById(mode);

		int formChange = this.partnerPokemon.changeForm(this.currentBattleMode.getPokemonForm());

		this.partnerPokemon.getStatTracker().switchBaseStat(this.currentBattleMode.getUnitBaseStat());

		rebuildMoveset();

		return 10 + formChange;
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Phase Controller																								*/

	public StarBattlePhase getCurrentBattlePhase() {
		return currentBattlePhase;
	}

	public int changePhase(int phase) {
		if (phase <= 0 || phase == currentBattlePhase.getPhaseOrdinal() || phase > getUnitProfile().getStarUnit().getAllPhases().size()) {
			return 0;
		}

		this.currentBattlePhase = getUnitProfile().getStarUnit().getPhaseById(phase);
		int formChange = changeMode(this.currentBattlePhase.getDefaultModeId()) % 10;

		this.partnerPokemon.resetState();

		return 10 + formChange;
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Moveset																											*/

	public List<Move> getMoveset() {
		return moveset;
	}

	private void rebuildMoveset() {
		List<Move> combined = new ArrayList<>(this.currentBattleMode.getMoveset());
		combined.addAll(this.baseMoveset);

		this.moveset = Collections.unmodifiableList(combined);
	}

	/*------------------------------------------------------------------------------------------------------------------
	.	Sync Ether Tracker																								*/

	public int getSyncEther() {
		return syncEther;
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

	public int getMaxSyncEther() {
		return maxSyncEther;
	}

	public boolean setNewMaxSyncEther(int newMax) {
		if (newMax < 0) {
			return false;
		}
		this.maxSyncEther = newMax;
		return true;
	}

	public boolean modifyMaxSyncEther(int deltaMax) {
		if (deltaMax <= 0) {
			return false;
		}
		this.maxSyncEther += deltaMax;
		return true;
	}
}
