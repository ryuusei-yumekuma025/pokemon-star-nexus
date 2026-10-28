package com.gameplay.masterdata.data.master;

import com.gameplay.masterdata.data.master.loader.move.item.ItemMoveLoader;
import com.gameplay.masterdata.data.master.loader.move.pokemon.PokemonMoveLoader;
import com.gameplay.masterdata.data.master.loader.pokemon.PokemonLoader;
import com.gameplay.masterdata.data.master.loader.starpartner.StarPartnerLoader;
import com.gameplay.masterdata.data.master.loader.trainer.TrainerLoader;
import com.gameplay.masterdata.data.master.registry.move.battle.PokemonMoveRegistry;
import com.gameplay.masterdata.data.master.registry.move.tactic.ItemMoveRegistry;
import com.gameplay.masterdata.data.master.registry.move.tactic.TrainerMoveRegistry;
import com.gameplay.masterdata.data.master.registry.pokemon.PokemonRegistry;
import com.gameplay.masterdata.data.master.registry.trainer.TrainerRegistry;

public class MasterDataBootstrap {

	//==================================================================================================================
	//	Initialized State
	//==================================================================================================================

	private static volatile boolean initialized = false;

	public static boolean isInitialized() {
		return initialized;
	}

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private MasterDataBootstrap() {}

	//==================================================================================================================
	//	Initialization
	//==================================================================================================================

	public static synchronized void init() {
		if (initialized) {
			return;
		}

		try {

			PokemonLoader.loadAll();

			PokemonMoveLoader.loadAll();
			ItemMoveLoader.loadAll();

			TrainerLoader.loadAll();

			StarPartnerLoader.loadAll();

		} catch (RuntimeException e) {
			throw new IllegalStateException("Master Data Load Failed.", e);
		}

		PokemonRegistry.freeze();

		PokemonMoveRegistry.freeze();
		ItemMoveRegistry.freeze();
		TrainerMoveRegistry.freeze();

		TrainerRegistry.freeze();

		initialized = true;
	}
}
