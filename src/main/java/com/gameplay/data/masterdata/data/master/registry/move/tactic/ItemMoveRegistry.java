package com.gameplay.data.masterdata.data.master.registry.move.tactic;

import com.gameplay.data.masterdata.data.master.registry.move.MoveRegistry;
import com.gameplay.data.masterdata.template.move.tactic.item.ItemMove;

import java.util.Collection;

public final class ItemMoveRegistry extends MoveRegistry<Integer, ItemMove> {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Constructor																										*/

	private static final ItemMoveRegistry INSTANCE = new ItemMoveRegistry();

	private ItemMoveRegistry() {
		super("ItemMoveRegistry", "ItemMove", "IM", ItemMove::getMoveIdNo, ItemMove::getMoveCode);
	}

	/*------------------------------------------------------------------------------------------------------------------
	2.	Data Registry Mode																								*/

	public static void freeze() {
		INSTANCE.freezeMoveStorage();
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Register																										*/

	public static void register(ItemMove itemMove) {
		INSTANCE.registerMove(itemMove);
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Search and Getters																								*/

	public static ItemMove getById(int moveId) {
		return INSTANCE.getMoveById(moveId);
	}

	public static ItemMove getByCode(String moveCode) {
		return INSTANCE.getMoveByCode(moveCode);
	}

	public static Collection<ItemMove> getAllItemMoves() {
		return INSTANCE.getAllMoves();
	}

	public static int size() {
		return INSTANCE.getMovesSize();
	}

	/*------------------------------------------------------------------------------------------------------------------
	5.	Clear and Reset																											*/

	public static void clear() {
		INSTANCE.clearMoves();
	}

	public static void reset() {
		INSTANCE.resetMoves();
	}
}
