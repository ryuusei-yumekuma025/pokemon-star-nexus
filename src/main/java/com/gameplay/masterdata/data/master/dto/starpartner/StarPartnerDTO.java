package com.gameplay.masterdata.data.master.dto.starpartner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.masterdata.data.master.dto.move.battle.ResonanceMoveDTO;
import com.gameplay.masterdata.data.master.dto.move.starnexus.StarNexusMoveDTO;
import com.gameplay.masterdata.data.master.registry.move.battle.ResonanceMoveRegistry;
import com.gameplay.masterdata.data.master.registry.move.starnexus.StarNexusMoveRegistry;
import com.gameplay.masterdata.template.move.battle.resonance.ResonanceMove;
import com.gameplay.masterdata.template.move.starnexus.StarNexusMove;
import com.gameplay.masterdata.template.starpartner.PartnerState;
import com.gameplay.masterdata.template.starpartner.StarPartner;
import com.utils.StringUtils;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StarPartnerDTO(
		@JsonProperty("id")
		int starPartnerId,
		@JsonProperty("code")
		String starPartnerCode,
		@JsonProperty("trainer")
		String trainerAlternate,
		@JsonProperty("pokemon")
		PartnerPokemonDTO partnerPokemon,
		@JsonProperty("star_jewel")
		StarJewelDTO starJewel,
		@JsonProperty("resonance_moves")
		List<ResonanceMoveDTO> resonanceMoveDTOList,
		@JsonProperty("star_nexus_moves")
		List<StarNexusMoveDTO> starNexusMoveDTOList,
		@JsonProperty("default_state")
		int defaultPartnerState,
		@JsonProperty("states")
		List<PartnerStateDTO> partnerStateDTOList
) {
	public StarPartnerDTO {
		Objects.requireNonNull(starPartnerCode, "Missing Star Partner Code.");
		starPartnerCode = StringUtils.upperSnakeCase(starPartnerCode);
		Objects.requireNonNull(trainerAlternate, "Missing Trainer.");
		resonanceMoveDTOList = (resonanceMoveDTOList != null) ? List.copyOf(resonanceMoveDTOList) : List.of();
		starNexusMoveDTOList = (starNexusMoveDTOList != null) ? List.copyOf(starNexusMoveDTOList) : List.of();
		partnerStateDTOList = (partnerStateDTOList != null) ? List.copyOf(partnerStateDTOList) : List.of();
	}

	public StarPartner create() {
		StarPartner.Builder builder = new StarPartner.Builder(starPartnerId, starPartnerCode, trainerAlternate)
				.setPartnerPokemon(partnerPokemon.create())
				.setStarJewel(starJewel.create())
				.setDefaultPartnerState(defaultPartnerState);

		for (ResonanceMoveDTO resonanceMoveDTO : resonanceMoveDTOList) {
			ResonanceMove resonanceMove = resonanceMoveDTO.create(builder.getStarPartnerId());

			if (resonanceMove != null) {
				builder.addResonanceMove(resonanceMove);
				ResonanceMoveRegistry.register(resonanceMove);
			}
		}

		for (StarNexusMoveDTO starNexusMoveDTO : starNexusMoveDTOList) {
			StarNexusMove starNexusMove = starNexusMoveDTO.create(builder.getStarPartnerId());

			if (starNexusMove != null) {
				builder.addStarNexusMove(starNexusMove);
				StarNexusMoveRegistry.register(starNexusMove);
			}
		}

		for (PartnerStateDTO partnerStateDTO : partnerStateDTOList) {
			PartnerState partnerState = partnerStateDTO.create();

			if (partnerState != null) {
				builder.addStarPartnerState(partnerState);
			}
		}

		return builder.build();
	}
}
