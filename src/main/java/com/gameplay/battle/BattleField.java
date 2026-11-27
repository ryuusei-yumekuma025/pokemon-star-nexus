package com.gameplay.battle;

import com.gameplay.battle.entity.turn.BattleActionQueue;
import com.gameplay.battle.entity.turn.BattleTurn;
import com.gameplay.battle.entity.unit.BattleListener;
import com.gameplay.battle.entity.unit.BattleUnit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BattleField {

	public enum BattleResult {
		ONGOING,
		WIN,
		LOSE,
		DRAW
	}

	private final List<BattleUnit> allies = new ArrayList<>();
	private final List<BattleUnit> opponents = new ArrayList<>();
	private final BattleActionQueue battleActionQueue = new BattleActionQueue();

	private final List<BattleListener> listeners = new ArrayList<>();
	private final Set<BattleUnit> faintNotified = new HashSet<>();
	private boolean battleResultNotified;

	private BattleTurn currentTurn;
	private int turnCount;

	public boolean unitEntrance(BattleUnit unit) {
		if (unit == null || allies.contains(unit) || opponents.contains(unit)) {
			return false;
		}

		(unit.isAlly() ? allies : opponents).add(unit);

		this.battleActionQueue.peekNext();

		return true;
	}
}
