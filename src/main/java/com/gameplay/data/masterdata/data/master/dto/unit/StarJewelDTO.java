package com.gameplay.data.masterdata.data.master.dto.unit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.data.masterdata.template.unit.starjewel.StarJewel;
import com.gameplay.data.masterdata.template.unit.starjewel.StarJewelEmblem;
import com.gameplay.data.masterdata.template.unit.starjewel.StarJewelFragment;
import com.utils.StringUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StarJewelDTO(
		@JsonProperty("star_type")
		String starType,
		@JsonProperty("starsoul")
		String starsoul
) {
	public StarJewelDTO {
		starType = StringUtils.requireUpperSnakeCase(starType, "Star Type");
		starsoul = (starsoul != null) ? starsoul : "VOID";
	}

	public StarJewel create(boolean refinedJewelEmblem) {
		return refinedJewelEmblem ? createEmblem() : createFragment();
	}

	public StarJewelEmblem createEmblem() {
		return new StarJewelEmblem(starType, starsoul);
	}

	public StarJewelFragment createFragment() {
		return new StarJewelFragment(starType);
	}
}
