package com.gameplay.masterdata.data.master.dto.starpartner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.masterdata.template.starpartner.MoveSlot;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveSlotDTO(
		@JsonProperty("slot")
		int moveSlot,
		@JsonProperty("class")
		String moveClass,
		@JsonProperty("code")
		String moveCode
) {
	public MoveSlotDTO {
		Objects.requireNonNull(moveClass, "Missing Move Class.");
		Objects.requireNonNull(moveCode, "Missing Move Code.");
	}

	public MoveSlot create() {
		return new MoveSlot(moveSlot, moveClass, moveCode);
	}
}
