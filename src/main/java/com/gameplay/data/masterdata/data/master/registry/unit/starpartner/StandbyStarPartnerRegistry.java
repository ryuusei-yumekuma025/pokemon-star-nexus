package com.gameplay.data.masterdata.data.master.registry.unit.starpartner;

import com.gameplay.data.masterdata.data.master.registry.Registry;
import com.gameplay.data.masterdata.template.unit.type.playable.standby.StandbyStarPartner;
import com.utils.StringUtils;

import java.util.Collection;

public final class StandbyStarPartnerRegistry {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Data Registry																									*/

	private static final Registry<String, StandbyStarPartner> STANDBY_STAR_PARTNER_REGISTRY = new Registry<>(
			"StandbyStarPartnerRegistry", "StandbyStarPartner",
			StandbyStarPartner::getStarUnitId, StandbyStarPartner::getStarUnitCode
	);

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private StandbyStarPartnerRegistry() {}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Data Registry State																								*/

	public static synchronized void freeze() {
		STANDBY_STAR_PARTNER_REGISTRY.freeze();
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Register																										*/

	public static synchronized void register(StandbyStarPartner standbyStarPartner) {
		if (standbyStarPartner == null) {
			return;
		}

		STANDBY_STAR_PARTNER_REGISTRY.register(standbyStarPartner);
	}

	/*------------------------------------------------------------------------------------------------------------------
	5.	Search and Getters																								*/

	public static StandbyStarPartner getStarPartnerById(String id) {
		id = StringUtils.requireUpperSnakeCase(id, "Standby Star Partner Id");
		if (!id.startsWith("SSP_")) {
			id = "SSP_" + id;
		}
		return STANDBY_STAR_PARTNER_REGISTRY.getById(id);
	}

	public static StandbyStarPartner getStarPartnerByCode(String code) {
		return STANDBY_STAR_PARTNER_REGISTRY.getByCode(code);
	}

	public static Collection<StandbyStarPartner> getAllStandbyStarPartners() {
		return STANDBY_STAR_PARTNER_REGISTRY.getAll();
	}

	public static int getNumberOfStandbyStarPartners() {
		return STANDBY_STAR_PARTNER_REGISTRY.size();
	}

	/*------------------------------------------------------------------------------------------------------------------
	6.	Clear and Reset																									*/

	public static synchronized void clear() {
		STANDBY_STAR_PARTNER_REGISTRY.clear();
	}

	public static synchronized void reset() {
		STANDBY_STAR_PARTNER_REGISTRY.reset();
	}
}
