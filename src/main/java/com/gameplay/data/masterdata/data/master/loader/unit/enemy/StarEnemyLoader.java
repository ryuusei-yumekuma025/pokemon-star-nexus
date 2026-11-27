package com.gameplay.data.masterdata.data.master.loader.unit.enemy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameplay.data.masterdata.attribute.region.WorldRegion;
import com.gameplay.data.masterdata.attribute.trainer.NPCTrainerClass;
import com.gameplay.data.masterdata.data.master.dto.unit.type.StarEnemyDTO;
import com.gameplay.data.masterdata.data.master.registry.unit.enemy.StarEnemyRegistry;
import com.gameplay.data.masterdata.template.unit.type.enemy.StarEnemy;
import com.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;

public final class StarEnemyLoader {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final String PATH = "data/unit/enemy/";
	private static final String DEX = PATH + "dex/";

	private static void loadStarEnemy(String starEnemyPath) {
		try (InputStream inputStream = StarEnemyLoader.class.getClassLoader().getResourceAsStream(starEnemyPath)) {
			if (inputStream == null) {
				return;
			}

			StarEnemyDTO starEnemyDTO = MAPPER.readValue(inputStream, StarEnemyDTO.class);
			StarEnemy starEnemy = starEnemyDTO.create();
			StarEnemyRegistry.register(starEnemy);
		} catch (IOException e) {
			throw new UncheckedIOException("Error : " + starEnemyPath, e);
		}
	}

	private static void loadEnemyPokemonDex() {
		String basePath = PATH + "pokemon/generation_";
		try (InputStream inputStream = StarEnemyLoader.class.getClassLoader().getResourceAsStream(DEX + "pokemon/enemy-pokemon-dex.json")) {
			if (inputStream == null) {
				return;
			}

			List<List<String>> enemyPokemonDex = MAPPER.readValue(inputStream, new TypeReference<List<List<String>>>() {});

			int generation = 1;

			for (List<String> enemyPokemonByGenerationDex : enemyPokemonDex) {
				String path = basePath + generation + "/";

				for (String enemyPokemon : enemyPokemonByGenerationDex) {
					loadStarEnemy(path + enemyPokemon + ".json");
				}

				generation++;
			}
		} catch (IOException e) {
			throw new UncheckedIOException("Error Enemy Pokemon Dex : " + DEX, e);
		}
	}

	private static void loadEnemyNPCStarPartner() {
		String basePath = PATH + "starpartner/npc_trainer/";
		try (InputStream inputStream = StarEnemyLoader.class.getClassLoader().getResourceAsStream(DEX + "starpartner/enemy-npc-star-partner-dex.json")) {
			if (inputStream == null) {
				return;
			}

			Map<String, List<String>> npcStarPartnerByNPCTrainerClass = MAPPER.readValue(inputStream, new TypeReference<Map<String, List<String>>>() {});

			for (NPCTrainerClass npcTrainerClass : NPCTrainerClass.values()) {
				String npcClass = StringUtils.lowerSnakeCase(npcTrainerClass.name());
				String path = basePath + npcClass + "/";

				for (String npcStarPartner : npcStarPartnerByNPCTrainerClass.get(npcClass)) {
					loadStarEnemy(path + npcStarPartner + ".json");
				}
			}
		} catch (IOException e) {
			throw new UncheckedIOException("Error Enemy NPC Star Partner Dex : " + DEX, e);
		}
	}

	private static void loadEnemyStarPartner() {
		String basePath = PATH + "starpartner/trainer/";
		try (InputStream inputStream = StarEnemyLoader.class.getClassLoader().getResourceAsStream(DEX + "starpartner/enemy-star-partner-dex.json")) {
			if (inputStream == null) {
				return;
			}

			List<List<String>> enemyStarPartnerDex = MAPPER.readValue(inputStream, new TypeReference<List<List<String>>>() {});

			int region = 0;

			for (List<String> enemyStarPartnerDexByRegion : enemyStarPartnerDex) {
				WorldRegion worldRegion = WorldRegion.fromId(region);
				if (worldRegion == null) {
					return;
				}

				String path = basePath + region + "_" + StringUtils.lowerPartedCase(worldRegion.toString()) + "/";

				for (String enemyStarPartner : enemyStarPartnerDexByRegion) {
					loadStarEnemy(path + enemyStarPartner + ".json");
				}

				region++;
			}
		} catch (IOException e) {
			throw new UncheckedIOException("Error Enemy NPC Star Partner Dex : " + DEX, e);
		}
	}

	public static void loadAll() {
		loadEnemyPokemonDex();
		loadEnemyNPCStarPartner();
		loadEnemyStarPartner();
	}
}
