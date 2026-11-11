package com.gameplay.masterdata.data.master.dto.starpartner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.masterdata.template.starpartner.StarJewel;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StarJewelDTO(
		@JsonProperty("sync_type")
		String syncType,
		@JsonProperty("starsoul")
		String starsoul
) {
	public StarJewelDTO {
		Objects.requireNonNull(syncType, "Missing Sync Type.");
		Objects.requireNonNull(starsoul, "Missing Starsoul");
	}

	public StarJewel create() {
		StarJewel.Builder builder = new StarJewel.Builder(syncType, starsoul);

		return builder.build();
	}
}
