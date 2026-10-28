package com.gameplay.masterdata.data.master.registry.starpartner;

import com.gameplay.masterdata.template.starpartner.StarPartner;
import com.gameplay.masterdata.template.trainer.TrainerIdentity;
import com.utils.StringUtils;

import java.util.*;

public class StarPartnerRegistry {

	//==================================================================================================================
	//	Data Storage
	//==================================================================================================================

	private static final Map<String, StarPartner> STAR_PARTNER_BY_ID_MAP = new LinkedHashMap<>();
	private static final Map<String, StarPartner> STAR_PARTNER_BY_CODE_MAP = new HashMap<>();

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private StarPartnerRegistry() {}

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

	public static synchronized void register(StarPartner starPartner) {
		if (starPartner == null) {
			return;
		}

		if (frozen) {
			throw new IllegalStateException("StarPartnerRegistry is frozen. Failed Register : " + starPartner.getStarPartnerCode());
		}

		String starPartnerId = starPartner.getStarPartnerId();
		String starPartnerCode = starPartner.getStarPartnerCode();

		if (!StringUtils.isNullOrBlank(starPartnerId, starPartnerCode)) {
			STAR_PARTNER_BY_ID_MAP.put(starPartnerId, starPartner);
			STAR_PARTNER_BY_CODE_MAP.put(starPartnerCode, starPartner);
		}
	}

	//==================================================================================================================
	//	Search Star Partner
	//==================================================================================================================

	public static StarPartner getStarPartnerById(String starPartnerId) {
		if (StringUtils.isNullOrBlank(starPartnerId)) {
			throw new IllegalArgumentException("Missing Star Partner Id.");
		}

		StarPartner starPartner = STAR_PARTNER_BY_ID_MAP.get(StringUtils.upperSnakeCase(starPartnerId));
		if (starPartner == null) {
			throw new IllegalArgumentException("Failed Star Partner Search : " + starPartnerId);
		}

		return starPartner;
	}

	public static StarPartner getStarPartnerByCode(String starPartnerCode) {
		if (StringUtils.isNullOrBlank(starPartnerCode)) {
			throw new IllegalArgumentException("Missing Star Partner Code.");
		}

		StarPartner starPartner = STAR_PARTNER_BY_CODE_MAP.get(StringUtils.upperSnakeCase(starPartnerCode));
		if (starPartner == null) {
			throw new IllegalArgumentException("Failed Star Partner Search : " + starPartnerCode);
		}

		return starPartner;
	}

	public static Collection<StarPartner> getAllStarPartners() {
		return Collections.unmodifiableCollection(STAR_PARTNER_BY_ID_MAP.values());
	}

	public static int getNumberOfStarPartners() {
		return STAR_PARTNER_BY_ID_MAP.size();
	}

	//==================================================================================================================
	//	Clear and Reset
	//==================================================================================================================

	public static synchronized void clear() {
		if (frozen) {
			throw new IllegalStateException("StarPartnerRegistry is frozen. Cannot Clear Registry.");
		}

		STAR_PARTNER_BY_ID_MAP.clear();
		STAR_PARTNER_BY_CODE_MAP.clear();
	}

	public static synchronized void reset() {
		frozen = false;
		clear();
	}
}
