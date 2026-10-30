package com.gameplay.masterdata.data.master.loader.trainer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameplay.masterdata.attribute.region.WorldRegion;
import com.gameplay.masterdata.data.master.dto.trainer.TrainerIdentityDTO;
import com.gameplay.masterdata.data.master.loader.starpartner.StarPartnerLoader;
import com.gameplay.masterdata.data.master.registry.trainer.TrainerRegistry;
import com.gameplay.masterdata.template.pokemon.PokemonSpecies;
import com.gameplay.masterdata.template.trainer.TrainerAlternate;
import com.gameplay.masterdata.template.trainer.TrainerIdentity;
import com.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

public class TrainerLoader {

	//==================================================================================================================
	//	Configuration
	//==================================================================================================================

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final String PATH = "data/trainer/";
	private static final String TRAINER_DEX = PATH + "trainer-dex.json";

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private TrainerLoader() {}

	//==================================================================================================================
	//	Loaders
	//==================================================================================================================

	private static void loadTrainer(int series, String trainerPath) {
		try (InputStream inputStream = TrainerLoader.class.getClassLoader().getResourceAsStream(trainerPath)) {
			if (inputStream == null) {
				return;
			}

			TrainerIdentityDTO trainerIdentityDTO = MAPPER.readValue(inputStream, TrainerIdentityDTO.class);
			TrainerIdentity trainerIdentity = trainerIdentityDTO.create();
			TrainerRegistry.register(trainerIdentity);

			for (TrainerAlternate trainerAlternate : trainerIdentity.getAllAlternates()) {
				String file = StringUtils.lowerSnakeCase(trainerAlternate.getTrainerAlternateFullCode());
				for (PokemonSpecies partner : trainerAlternate.getPartnerPokemonList()) {
					StarPartnerLoader.addStarPartnerDex(series, file + "_and_" + StringUtils.lowerSnakeCase(partner.getSpeciesCode()));
				}
			}
		} catch (IOException e) {
			throw new UncheckedIOException("Error : " + trainerPath, e);
		}
	}

	private static void loadByRegion(int region, List<String> trainerDex) {
		if (trainerDex == null || region <= 0) {
			return;
		}

		WorldRegion worldRegion = WorldRegion.fromId(region);
		if (worldRegion == null) {
			return;
		}

		String basePath = PATH + region + "_" + StringUtils.lowerSnakeCase(worldRegion.toString()) + "/";

		for (String trainer : trainerDex) {
			if (!StringUtils.isNullOrBlank(trainer)) {
				loadTrainer(region, basePath + StringUtils.lowerSnakeCase(trainer) + ".json");
			}
		}
	}

	public static void loadAll() {
		try (InputStream inputStream = TrainerLoader.class.getClassLoader().getResourceAsStream(TRAINER_DEX)) {
			if (inputStream == null) {
				return;
			}

			List<List<String>> trainerDexByRegionList = MAPPER.readValue(inputStream, new TypeReference<>() {});

			int region = 0;

			for (List<String> trainerDexByRegion : trainerDexByRegionList) {
				loadByRegion(region++, trainerDexByRegion);
			}
		} catch (IOException e) {
			throw new UncheckedIOException("Error : " + TRAINER_DEX, e);
		}
	}
}
