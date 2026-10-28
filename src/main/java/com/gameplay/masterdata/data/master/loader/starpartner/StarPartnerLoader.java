package com.gameplay.masterdata.data.master.loader.starpartner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameplay.masterdata.attribute.region.WorldRegion;
import com.gameplay.masterdata.data.master.dto.starpartner.StarPartnerDTO;
import com.gameplay.masterdata.data.master.registry.starpartner.StarPartnerRegistry;
import com.gameplay.masterdata.template.starpartner.StarPartner;
import com.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

public class StarPartnerLoader {

	//==================================================================================================================
	//	Link Data
	//==================================================================================================================

	public static final List<List<String>> STAR_PARTNER_LIST = new ArrayList<>();

	public static synchronized void addStarPartnerDex(int series, String starPartner) {
		while (STAR_PARTNER_LIST.size() < series) {
			STAR_PARTNER_LIST.add(new ArrayList<>());
		}

		STAR_PARTNER_LIST.get(series - 1).add(starPartner);
	}

	//==================================================================================================================
	//	Configuration
	//==================================================================================================================

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final String PATH = "data/starpartner/";

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private StarPartnerLoader() {}

	//==================================================================================================================
	//	Loaders
	//==================================================================================================================

	private static void loadStarPartner(String starPartnerPath) {
		try (InputStream inputStream = StarPartnerLoader.class.getClassLoader().getResourceAsStream(starPartnerPath)) {
			if (inputStream == null) {
				return;
			}

			StarPartnerDTO starPartnerDTO = MAPPER.readValue(inputStream, StarPartnerDTO.class);
			StarPartner starPartner = starPartnerDTO.create();
			StarPartnerRegistry.register(starPartner);
		} catch (IOException e) {
			throw new UncheckedIOException("Error : " + starPartnerPath, e);
		}
	}

	private static void loadByRegion(int region, List<String> starPartnerDex) {
		if (starPartnerDex == null || region <= 0) {
			return;
		}

		WorldRegion worldRegion = WorldRegion.fromId(region);
		if (worldRegion == null) {
			return;
		}

		String basePath = PATH + region + "_" + StringUtils.lowerSnakeCase(worldRegion.toString()) + "/";

		for (String starPartner : starPartnerDex) {
			if (!StringUtils.isNullOrBlank(starPartner)) {
				loadStarPartner(basePath + StringUtils.lowerSnakeCase(starPartner) + ".json");
			}
		}
	}

	public static void loadAll() {
		int region = 1;

		for (List<String> starPartnerDex : STAR_PARTNER_LIST) {
			loadByRegion(region++, starPartnerDex);
		}
	}
}
