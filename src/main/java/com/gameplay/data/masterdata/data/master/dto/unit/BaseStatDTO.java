package com.gameplay.data.masterdata.data.master.dto.unit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.data.masterdata.template.unit.BaseStat;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BaseStatDTO(
		@JsonProperty("stat")
		List<Integer> stat,
		@JsonProperty("evolution_bonus")
		List<Integer> evolutionBonus,
		@JsonProperty("bottom_growth")
		List<Double> bottomGrowth,
		@JsonProperty("top_growth")
		List<Double> topGrowth
) {
	public BaseStatDTO {
		if (stat == null || stat.size() != 6) {
			throw new IllegalArgumentException("Missing Stat.");
		}

		evolutionBonus = (evolutionBonus == null || evolutionBonus.isEmpty()) ? List.of(0, 0, 0, 0, 0, 0) : List.copyOf(evolutionBonus);

		if (bottomGrowth == null || topGrowth == null || bottomGrowth.size() != 6 || topGrowth.size() != 6) {
			throw new IllegalArgumentException("Missing Stat Growth.");
		}
	}

	public BaseStat create() {
		return new BaseStat.Builder()
				.setBaseStatAtLevel150(stat)
				.setNexusEvolutionBonus(evolutionBonus)
				.setStatGrowthLevelLowPoint(bottomGrowth)
				.setStatGrowthLevelHighPoint(topGrowth)
				.build();
	}
}
