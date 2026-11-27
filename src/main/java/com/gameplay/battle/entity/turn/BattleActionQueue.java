package com.gameplay.battle.entity.turn;

import com.gameplay.battle.entity.unit.BattleUnit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BattleActionQueue {

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	public static final int UPCOMING_ACTION = 20;

	public record ActionForecastSlot(BattleUnit battleUnit, double actionInterval) {}

	// Xử lý Khoảng Hành Động, nếu bằng sẽ xét theo order
	private static final Comparator<Entry> ORDER =
			Comparator.<Entry>comparingDouble(e -> currentActionInterval(e.battleUnit))
					.thenComparingInt(e -> e.order);

	private static final class Entry {
		private final BattleUnit battleUnit;
		private int order;

		private Entry(BattleUnit battleUnit, int order) {
			this.battleUnit = battleUnit;
			this.order = order;
		}
	}

	// Simulation để thử nghiệm, tiên đoán hành động tiếp, dùng cho UI/UX

	private static final class Sim {
		private final BattleUnit battleUnit;
		private double actionInterval;
		private int order;

		private Sim(BattleUnit battleUnit, double actionInterval, int order) {
			this.battleUnit = battleUnit;
			this.actionInterval = actionInterval;
			this.order = order;
		}
	}

	private static final Comparator<Sim> SIM_ORDER =
			Comparator.<Sim>comparingDouble(s -> s.actionInterval).thenComparingInt(s -> s.order);

	private final List<Entry> entryList = new ArrayList<>();
	//	Thời gian hành động đã qua
	private double elapsed;
	private int orderCounter;
	//	Đc đẩy sau thì có ưu thế được lên đầu
	private int priorityOrderCounter;
	private int previewSize = UPCOMING_ACTION;
	private List<ActionForecastSlot> upcoming = List.of();

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	public BattleUnit peekNext() {
		Entry entry = findNext();
		return entry == null ? null : entry.battleUnit;
	}

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	private static double currentActionInterval(BattleUnit battleUnit) {
		return battleUnit.getActionInterval().getCurrentActionInterval();
	}

	private void elapse(double amount) {
		if (amount <= 0) {
			return;
		}

		for (Entry entry : entryList) {
			entry.battleUnit.getActionInterval().elapse(amount);
		}

		elapsed += amount;
	}

	private double minCurrentActionInterval() {
		return entryList.stream().mapToDouble(e -> currentActionInterval(e.battleUnit)).min().orElse(0);
	}

	private Entry find(BattleUnit battleUnit) {
		for (Entry entry : entryList) {
			if (entry.battleUnit == battleUnit) {
				return entry;
			}
		}

		return null;
	}

	private Entry findNext() {
		trimFainted();
		return entryList.stream().min(ORDER).orElse(null);
	}

	private void trimFainted() {
		entryList.removeIf(e -> e.battleUnit.isFainted());
	}

	//	Cắt các unit fainted trừ unit hiện hành để xử lý
	private void trimFainted(BattleUnit battleUnit) {
		entryList.removeIf(e -> e.battleUnit != battleUnit && e.battleUnit.isFainted());
	}
}
