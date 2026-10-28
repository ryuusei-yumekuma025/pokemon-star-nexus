package com.gameplay.masterdata.data.master.dto.trainer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.masterdata.template.trainer.TrainerAlternate;
import com.gameplay.masterdata.template.trainer.TrainerIdentity;
import com.utils.StringUtils;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TrainerIdentityDTO(
		@JsonProperty("id")
		String identityId,
		@JsonProperty("code")
		String identityCode,
		@JsonProperty("alternate_variety")
		List<TrainerAlternateDTO> trainerAlternateDTOList
) {
	public TrainerIdentityDTO {
		Objects.requireNonNull(identityId, "Missing Identity Id.");
		identityId = StringUtils.upperSnakeCase(identityId);
		Objects.requireNonNull(identityCode, "Missing Identity Code.");
		identityCode = StringUtils.upperSnakeCase(identityCode);
		trainerAlternateDTOList = (trainerAlternateDTOList != null) ? List.copyOf(trainerAlternateDTOList) : List.of();
	}

	public TrainerIdentity create() {
		TrainerIdentity.Builder builder = new TrainerIdentity.Builder(identityId, identityCode);

		for (TrainerAlternateDTO trainerAlternateDTO : trainerAlternateDTOList) {
			TrainerAlternate trainerAlternate = trainerAlternateDTO.create(identityId);

			if (trainerAlternate != null) {
				builder.addTrainerAlternate(trainerAlternate);
			}
		}

		return builder.build();
	}
}
