package com.gameplay.data.masterdata.data.master.dto.unit.starpartner.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.data.masterdata.data.master.dto.unit.starpartner.PartnerPokemonDTO;
import com.gameplay.data.masterdata.data.master.dto.unit.starpartner.SpecialMovesDTO;
import com.gameplay.data.masterdata.data.master.dto.unit.starpartner.StarBattleStateDTO;
import com.gameplay.data.masterdata.data.master.dto.unit.starpartner.StarJewelDTO;
import com.gameplay.data.masterdata.template.unit.type.playable.standby.StandbyStarPartner;
import com.utils.StringUtils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StandbyStarPartnerDTO(
		@JsonProperty("id")
		int id,
		@JsonProperty("code")
		String code,
		@JsonProperty("partner_trainer")
		String partnerTrainer,
		@JsonProperty("partner_pokemon")
		PartnerPokemonDTO partnerPokemon,
		@JsonProperty("star_jewel_emblem")
		StarJewelDTO starJewelEmblem,
		@JsonProperty("special_moves")
		SpecialMovesDTO specialMovesDTO,
		@JsonProperty("states")
		List<StarBattleStateDTO> starBattleStateDTOList
) {
	public StandbyStarPartnerDTO {
		code = StringUtils.requireUpperSnakeCase(code, "Standby Star Partner Code");
	}

	public StandbyStarPartner create() {
		StandbyStarPartner.Builder builder = new StandbyStarPartner.Builder(id, code, partnerTrainer)
				.setPartnerPokemon(partnerPokemon.create())
				.setStarJewel(starJewelEmblem.createEmblem());

		specialMovesDTO.parse(builder.getStarUnitId());

		if (starBattleStateDTOList != null) {
			for (StarBattleStateDTO starBattleStateDTO : starBattleStateDTOList) {
				builder.addState(starBattleStateDTO.create());
			}
		}

		return builder.build();
	}
}
