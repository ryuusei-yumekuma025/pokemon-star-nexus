package com.gameplay.data.masterdata.data.master.dto.unit.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.data.masterdata.data.master.dto.unit.PartnerPokemonDTO;
import com.gameplay.data.masterdata.data.master.dto.unit.StarBattlePhaseDTO;
import com.gameplay.data.masterdata.data.master.dto.unit.StarBattleStateDTO;
import com.gameplay.data.masterdata.data.master.dto.unit.StarJewelDTO;
import com.gameplay.data.masterdata.data.master.registry.trainer.TrainerRegistry;
import com.gameplay.data.masterdata.template.trainer.Trainer;
import com.gameplay.data.masterdata.template.unit.PartnerPokemon;
import com.gameplay.data.masterdata.template.unit.type.enemy.EnemyRank;
import com.gameplay.data.masterdata.template.unit.type.enemy.StarEnemy;
import com.utils.StringUtils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StarEnemyDTO(
		@JsonProperty("id")
		int id,
		@JsonProperty("code")
		String code,
		@JsonProperty("enemy_rank")
		String enemyRank,
		@JsonProperty("partner_trainer")
		String partnerTrainer,
		@JsonProperty("partner_pokemon")
		PartnerPokemonDTO partnerPokemonDTO,
		@JsonProperty("star_jewel")
		StarJewelDTO starJewelDTO,
		@JsonProperty("states")
		List<StarBattleStateDTO> starBattleStateDTOList,
		@JsonProperty("phases")
		List<StarBattlePhaseDTO> starBattlePhaseDTOList
) {
	public StarEnemyDTO {
		code = StringUtils.requireUpperSnakeCase(code, "Enemy Code");
		starBattleStateDTOList = (starBattleStateDTOList != null && starBattlePhaseDTOList() == null) ?
				List.copyOf(starBattleStateDTOList) : List.of();
		starBattlePhaseDTOList = (starBattlePhaseDTOList != null) ?
				List.copyOf(starBattlePhaseDTOList) : List.of();
	}

	public StarEnemy create() {
		Trainer trainer = null;
		if (partnerTrainer != null) {
			trainer = TrainerRegistry.getTrainerByCode(partnerTrainer);
		}

		PartnerPokemon partnerPokemon = partnerPokemonDTO.create();

		EnemyRank rank = EnemyRank.fromCode(enemyRank);
		rank = (rank == null) ? EnemyRank.RETAINER : rank;

		String idString = String.format("%d%d%s%06d%04d",
				rank.getId(),
				(trainer == null) ? 0 : 1,
				(trainer == null) ? String.format("%014d", 0) : trainer.getTrainerIdWithoutCategoryLabel(),
				partnerPokemon.getPokemonSpecies().getPokedexNumber(),
				id);

		StarEnemy.Builder builder = new StarEnemy.Builder(idString, code, trainer, rank)
				.setPartnerPokemon(partnerPokemon)
				.setStarJewel(starJewelDTO.create(partnerTrainer != null));

		if (starBattlePhaseDTOList != null && !starBattlePhaseDTOList.isEmpty()) {
			for (StarBattlePhaseDTO starBattlePhaseDTO : starBattlePhaseDTOList) {
				builder.addPhase(starBattlePhaseDTO.create());
			}
		} else if (starBattleStateDTOList != null && !starBattleStateDTOList.isEmpty()) {
			for (StarBattleStateDTO starBattleStateDTO : starBattleStateDTOList) {
				builder.addState(starBattleStateDTO.create());
			}
		}

		return builder.build();
	}
}
