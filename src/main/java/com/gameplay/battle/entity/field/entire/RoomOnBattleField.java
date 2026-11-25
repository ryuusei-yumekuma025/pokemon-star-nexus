package com.gameplay.battle.entity.field.entire;

import com.gameplay.data.masterdata.attribute.battle.field.entire.Room;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class RoomOnBattleField {

	private final Map<Room, Integer> roomDurationMap;

	public RoomOnBattleField() {
		this.roomDurationMap = new LinkedHashMap<>();
	}

	public Set<Room> getActivatedRoomSet() {
		return new LinkedHashSet<>(roomDurationMap.keySet());
	}

	public int getDuration(Room room) {
		return roomDurationMap.getOrDefault(room, 0);
	}

	public boolean isActivated(Room room) {
		return roomDurationMap.containsKey(room);
	}

	public boolean applyRoomFieldEffect(Room room, int duration) {
		if (room == null || duration <= 0) {
			return false;
		}

		this.roomDurationMap.put(room, duration);
		return true;
	}

	public boolean removeRoomFieldEffect(Room room) {
		return roomDurationMap.remove(room) != null;
	}
}
