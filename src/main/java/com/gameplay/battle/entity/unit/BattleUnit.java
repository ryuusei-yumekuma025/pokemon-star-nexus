package com.gameplay.battle.entity.unit;

import com.gameplay.battle.entity.unit.instance.UnitInstance;
import com.gameplay.data.masterdata.content.EnemyInstance;
import com.gameplay.data.masterdata.data.config.EnergyConfig;
import com.gameplay.data.masterdata.template.move.Move;
import com.gameplay.data.masterdata.template.unit.phase.StarBattlePhase;
import com.gameplay.data.masterdata.template.unit.phase.StarBattleState;
import com.gameplay.data.play.collection.ownedstarpartner.OwnedStarPartner;

import java.util.ArrayList;
import java.util.List;

public class BattleUnit {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final String battleId;
	private final String instanceId;
	private final UnitInstance unitInstance;
	private final boolean isAlly;
	private final BattleTrainer battleTrainer;
	private final BattlePokemon battlePokemon;

	private StarBattlePhase currentBattlePhase;
	private StarBattleState currentBattleState;

	private int stardust;

	private int syncEther;
	private int maxSyncEther;

	private final List<Move> starMapMoveset;
	private List<Move> moveset;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	public BattleUnit(int slot, boolean isAlly, UnitInstance unitInstance, List<Move> moveset) {
		this.battleId = ((isAlly) ? "AS_" : "OS_") + String.format("%02d", slot);
		this.instanceId = unitInstance.getInstanceId();
		this.unitInstance = unitInstance;
		this.isAlly = isAlly;
		this.battleTrainer = (unitInstance.getStarUnit().getPartnerTrainer() == null) ? null :
				new BattleTrainer(this, unitInstance.getStarUnit().getPartnerTrainer(),
						unitInstance.getStarUnit().getAllTacticMoves().stream().toList(),
						moveset);
		this.currentBattlePhase = unitInstance.getStarUnit().getPhase();
		this.currentBattleState = this.currentBattlePhase.getDefaultState();
		this.battlePokemon = new BattlePokemon(this, unitInstance.getStarUnit().getPartnerPokemon(), this.currentBattleState);

		this.stardust = 0;

		this.maxSyncEther = EnergyConfig.BASE_MAX_SYNC_ETHER;
		this.syncEther = this.maxSyncEther;

		this.moveset = new ArrayList<>();
		this.moveset.addAll(this.currentBattleState.getMoveset());
		this.starMapMoveset = List.copyOf(moveset);
	}

	public BattleUnit(int slot, boolean isAlly, OwnedStarPartner<?> ownedStarPartner) {
		this(slot, isAlly, ownedStarPartner, ownedStarPartner.getMoveset());
	}

	public BattleUnit(int slot, EnemyInstance enemyInstance) {
		this(slot, false, enemyInstance, List.of());
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

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
	4.	Phase Management																								*/

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
	5.	State Management																								*/

	public StarBattleState getCurrentBattleState() {
		return currentBattleState;
	}

	public int changeState(int state) {
		if (state < 0 || state > this.currentBattlePhase.getAllStates().size() || state == this.currentBattleState.getStateId()) {
			return 0;
		}

		this.currentBattleState = this.currentBattlePhase.getStateById(state);

		int formChange = this.battlePokemon.changeForm(this.currentBattleState.getPokemonForm());

		if (unitInstance instanceof OwnedStarPartner<?>) {
			int moveSlot = 0;
			for (Move move : this.currentBattleState.getMoveset()) {
				this.moveset.add(moveSlot, move);
				moveSlot++;
			}
		} else {
			this.moveset.clear();
			this.moveset.addAll(this.currentBattleState.getMoveset());
		}

		return 10 + formChange;
	}

	/*------------------------------------------------------------------------------------------------------------------
	6.	Stardust Management																								*/

	public int getStardust() {
		return stardust;
	}

	/*------------------------------------------------------------------------------------------------------------------
	7.	Sync Ether Management																							*/

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
