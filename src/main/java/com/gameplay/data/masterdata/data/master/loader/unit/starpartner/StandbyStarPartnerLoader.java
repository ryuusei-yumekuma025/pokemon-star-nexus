package com.gameplay.data.masterdata.data.master.loader.unit.starpartner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameplay.data.masterdata.attribute.region.WorldRegion;
import com.gameplay.data.masterdata.data.master.dto.unit.type.StandbyStarPartnerDTO;
import com.gameplay.data.masterdata.data.master.registry.unit.starpartner.StandbyStarPartnerRegistry;
import com.gameplay.data.masterdata.template.unit.type.playable.standby.StandbyStarPartner;
import com.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

public final class StandbyStarPartnerLoader {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final String PATH = "data/unit/standbystarpartner/";
	private static final String DEX = PATH + "standby-star-partner-dex.json";

	private StandbyStarPartnerLoader() {}

	private static List<List<String>> loadStandbyStarPartnerDex() {
		try (InputStream inputStream = StandbyStarPartnerLoader.class.getClassLoader().getResourceAsStream(DEX)) {
			if (inputStream == null) {
				throw new IllegalArgumentException("Load Standby Star Partner Dex Failed : " + DEX);
			}

			return MAPPER.readValue(inputStream, new TypeReference<List<List<String>>>() {});
		} catch (IOException e) {
			throw new UncheckedIOException("Error Standby Star Partner Dex : " + DEX, e);
		}
	}

	private static void loadStandbyStarPartner(int region, String standbyStarPartnerPath) {
		try (InputStream inputStream = StandbyStarPartnerLoader.class.getClassLoader().getResourceAsStream(standbyStarPartnerPath)) {
			if (inputStream == null) {
				return;
			}

			StandbyStarPartnerDTO standbyStarPartnerDTO = MAPPER.readValue(inputStream, StandbyStarPartnerDTO.class);
			StandbyStarPartner standbyStarPartner = standbyStarPartnerDTO.create();
			StandbyStarPartnerRegistry.register(standbyStarPartner);
		} catch (IOException e) {
			throw new UncheckedIOException("Error : " + standbyStarPartnerPath, e);
		}
	}

	private static void loadByRegion(int region, List<String> standbyStarPartnerDexByRegion) {
		if (region < 0 || standbyStarPartnerDexByRegion == null) {
			return;
		}

		WorldRegion worldRegion = WorldRegion.fromId(region);
		if (worldRegion == null) {
			return;
		}
		String standbyStarPartnerPath = PATH + region + "_" + StringUtils.lowerPartedCase(worldRegion.toString()) + "/";

		for (String standbyStarPartner : standbyStarPartnerDexByRegion) {
			loadStandbyStarPartner(region, standbyStarPartnerPath + standbyStarPartner + ".json");
		}
	}

	public static void loadAll() {
		int region = 0;

		for (List<String> standbyStarPartnerDexByRegion : loadStandbyStarPartnerDex()) {
			loadByRegion(region, standbyStarPartnerDexByRegion);
			region++;
		}
	}
}
