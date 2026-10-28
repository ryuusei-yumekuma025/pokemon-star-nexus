package com.gameplay.masterdata.data.master.loader.move.item;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameplay.masterdata.data.master.dto.move.tactic.ItemMoveDTO;
import com.gameplay.masterdata.data.master.registry.move.tactic.ItemMoveRegistry;
import com.gameplay.masterdata.template.move.tactic.item.ItemMove;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

public class ItemMoveLoader {

	//==================================================================================================================
	//	Configuration
	//==================================================================================================================

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final String DEX_PATH = "data/move/item/item-moves.json";

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private ItemMoveLoader() {}

	//==================================================================================================================
	//	Loaders
	//==================================================================================================================

	public static void loadAll() {
		try (InputStream inputStream = ItemMoveLoader.class.getClassLoader().getResourceAsStream(DEX_PATH)) {
			if (inputStream == null) {
				throw new IllegalArgumentException("Error : " + DEX_PATH);
			}

			List<ItemMoveDTO> itemMoveDTOList = MAPPER.readValue(inputStream, new TypeReference<List<ItemMoveDTO>>() {});

			for (ItemMoveDTO itemMoveDTO : itemMoveDTOList) {
				ItemMove itemMove = itemMoveDTO.create();
				ItemMoveRegistry.register(itemMove);
			}
		} catch (IOException e) {
			throw new UncheckedIOException("Error : " + DEX_PATH, e);
		}
	}
}
