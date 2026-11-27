package com.gameplay.battle.entity.unit;

import com.gameplay.battle.entity.unit.action.ActionInterval;
import com.gameplay.battle.entity.unit.instance.UnitInstance;
import com.gameplay.data.masterdata.attribute.stat.StatType;
import com.gameplay.data.masterdata.content.EnemyInstance;
import com.gameplay.data.masterdata.data.config.EnergyConfig;
import com.gameplay.data.masterdata.template.move.Move;
import com.gameplay.data.masterdata.template.move.tactic.TacticMove;
import com.gameplay.data.masterdata.template.unit.phase.StarBattlePhase;
import com.gameplay.data.masterdata.template.unit.phase.StarBattleState;
import com.gameplay.data.play.collection.ownedstarpartner.OwnedStarPartner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BattleUnit {

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	private final String battleId;
	private final String instanceId;
	private final UnitInstance unitInstance;
	private final boolean isAlly;
	private final BattleTrainer battleTrainer;
	private final BattlePokemon battlePokemon;

	private StarBattlePhase currentBattlePhase;
	private StarBattleState currentBattleState;

	private final ActionInterval actionInterval;

	private int stardust;

	private int syncEther;
	private int maxSyncEther;

	private final List<Move> baseMoveset;
	private List<Move> moveset;

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	public BattleUnit(int slot, boolean isAlly, UnitInstance unitInstance, List<Move> moveset) {
		this.battleId = ((isAlly) ? "AS_" : "OS_") + String.format("%02d", slot);
		this.instanceId = unitInstance.getInstanceId();
		this.unitInstance = unitInstance;
		this.isAlly = isAlly;
		this.baseMoveset = (moveset == null) ? List.of() : List.copyOf(moveset);

		this.battleTrainer = (unitInstance.getStarUnit().getPartnerTrainer() == null) ? null :
				new BattleTrainer(this, unitInstance.getStarUnit().getPartnerTrainer(),
						unitInstance.getStarUnit().getAllTacticMoves().stream().toList(),
						this.baseMoveset);
		this.currentBattlePhase = unitInstance.getStarUnit().getPhase();
		this.currentBattleState = this.currentBattlePhase.getDefaultState();
		this.battlePokemon = new BattlePokemon(this, unitInstance.getStarUnit().getPartnerPokemon(), this.currentBattleState);

		this.actionInterval = new ActionInterval(this.battlePokemon.getStatTracker().getStat(StatType.SPEED));

		this.stardust = 0;

		this.maxSyncEther = EnergyConfig.BASE_MAX_SYNC_ETHER;
		this.syncEther = this.maxSyncEther;

		rebuildMoveset();
	}

	public BattleUnit(int slot, boolean isAlly, OwnedStarPartner<?> ownedStarPartner) {
		this(slot, isAlly, ownedStarPartner, ownedStarPartner.getMoveset());
	}

	public BattleUnit(int slot, EnemyInstance enemyInstance) {
		this(slot, false, enemyInstance, List.of());
	}

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	public String getBattleId() {
		return battleId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public UnitInstance getUnitInstance() {
		return unitInstance;
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
		if (phase <= 0 || phase == currentBattlePhase.getPhaseOrdinal() || phase > getUnitInstance().getStarUnit().getAllPhases().size()) {
			return 0;
		}

		this.currentBattlePhase = getUnitInstance().getStarUnit().getPhaseById(phase);
		int formChange = changeState(this.currentBattlePhase.getDefaultStateId()) % 10;

		this.battlePokemon.resetStatus();

		return 10 + formChange;
	}

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	public StarBattleState getCurrentBattleState() {
		return currentBattleState;
	}

	public int changeState(int state) {
		if (state < 0 || state > this.currentBattlePhase.getAllStates().size() || state == this.currentBattleState.getStateId()) {
			return 0;
		}

		this.currentBattleState = this.currentBattlePhase.getStateById(state);

		int formChange = this.battlePokemon.changeForm(this.currentBattleState.getPokemonForm());

		this.battlePokemon.getStatTracker().switchBaseStat(this.currentBattleState.getUnitBaseStat());

		rebuildMoveset();

		return 10 + formChange;
	}

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	private void rebuildMoveset() {
		List<Move> combined = new ArrayList<>(this.currentBattleState.getMoveset());
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
