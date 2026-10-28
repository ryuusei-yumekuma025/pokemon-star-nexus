package com.gameplay.masterdata.data.master.registry.move.tactic;

import com.gameplay.masterdata.template.move.tactic.item.ItemMove;
import com.utils.StringUtils;

import java.util.*;

public class ItemMoveRegistry {

	//==================================================================================================================
	//	Data Storage
	//==================================================================================================================

	private static final Map<Integer, ItemMove> ITEM_MOVE_BY_ID_MAP = new LinkedHashMap<>();
	private static final Map<String, ItemMove> ITEM_MOVE_BY_CODE_MAP = new HashMap<>();

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private ItemMoveRegistry() {}

	//==================================================================================================================
	//	Frozen State Management
	//==================================================================================================================

	private static volatile boolean frozen = false;

	public static synchronized void freeze() {
		frozen = true;
	}

	//==================================================================================================================
	//	Register
	//==================================================================================================================

	public static synchronized void register(ItemMove itemMove) {
		if (itemMove == null) {
			return;
		}

		if (frozen) {
			throw new IllegalStateException("ItemMoveRegistry is frozen. Failed Register : " + itemMove.getMoveCode());
		}

		int moveId = itemMove.getMoveIdNo();
		String moveCode = StringUtils.upperSnakeCase(itemMove.getMoveCode());

		if (!StringUtils.isNullOrBlank(moveCode)) {
			ITEM_MOVE_BY_ID_MAP.put(moveId, itemMove);
			ITEM_MOVE_BY_CODE_MAP.put(moveCode, itemMove);
		}
	}

	//==================================================================================================================
	//	Search
	//==================================================================================================================

	public static ItemMove getItemMoveById(int moveId) {
		ItemMove itemMove = ITEM_MOVE_BY_ID_MAP.get(moveId);
		if (itemMove == null) {
			throw new IllegalArgumentException("Failed Item Move Search : " + moveId);
		}

		return itemMove;
	}

	public static ItemMove getItemMoveByCode(String moveCode) {
		if (StringUtils.isNullOrBlank(moveCode)) {
			throw new IllegalArgumentException("Missing Move Code.");
		}

		ItemMove itemMove = ITEM_MOVE_BY_CODE_MAP.get(StringUtils.upperSnakeCase(moveCode));
		if (itemMove == null) {
			throw new IllegalArgumentException("Failed Item Move Search : " + moveCode);
		}

		return itemMove;
	}

	public static Collection<ItemMove> getAllItemMoves() {
		return Collections.unmodifiableCollection(ITEM_MOVE_BY_ID_MAP.values());
	}

	public static int getNumberOfItemMoves() {
		return ITEM_MOVE_BY_ID_MAP.size();
	}

	//==================================================================================================================
	//	Clear and Reset
	//==================================================================================================================

	public static synchronized void clear() {
		if (frozen) {
			throw new IllegalStateException("ItemMoveRegistry is frozen. Cannot Clear Registry.");
		}

		ITEM_MOVE_BY_ID_MAP.clear();
		ITEM_MOVE_BY_CODE_MAP.clear();
	}

	public static synchronized void reset() {
		frozen = false;
		clear();
	}
}
