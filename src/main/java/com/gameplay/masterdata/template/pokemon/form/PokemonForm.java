package com.gameplay.masterdata.template.pokemon.form;

import com.gameplay.masterdata.attribute.pokemon.appearance.BodyColor;
import com.gameplay.masterdata.attribute.pokemon.appearance.BodyShape;
import com.gameplay.masterdata.attribute.pokemon.breeding.EggGroup;
import com.gameplay.masterdata.attribute.type.PokemonType;
import com.gameplay.masterdata.data.master.registry.pokemon.PokemonRegistry;
import com.gameplay.masterdata.template.pokemon.PokemonSpecies;
import com.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PokemonForm {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private final int pokemonSpecies;
	private final int pokemonFormId;
	private final String pokemonFormCode;
	private final String pokemonCategory;
	private final List<PokemonType> pokemonTypeList;
	private final List<EggGroup> eggGroups;
	private final BodyColor color;
	private final BodyShape shape;
	private final double height;
	private final double weight;
	private final boolean genderAppearanceDifference;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	protected PokemonForm(Builder builder) {
		this.pokemonSpecies = builder.pokemonSpecies;
		this.pokemonFormId = builder.pokemonFormId;
		this.pokemonFormCode = builder.pokemonFormCode;
		this.pokemonCategory = builder.pokemonCategory;
		this.pokemonTypeList = List.copyOf(builder.pokemonTypeList);
		this.eggGroups = List.copyOf(builder.eggGroups);
		this.color = builder.color;
		this.shape = builder.shape;
		this.height = builder.height;
		this.weight = builder.weight;
		this.genderAppearanceDifference = builder.genderAppearanceDifference;
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public PokemonSpecies getPokemonSpecies() {
		return PokemonRegistry.getPokemonSpeciesByPokedexNumber(pokemonSpecies);
	}

	public int getPokemonFormId() {
		return pokemonFormId;
	}

	public String getPokemonFormCode() {
		return pokemonFormCode;
	}

	public String getPokemonFormFullCode() {
		if ("BASE".equalsIgnoreCase(pokemonFormCode)) {
			return getPokemonSpecies().getSpeciesCode();
		}
		return getPokemonSpecies().getSpeciesCode() + "_" + pokemonFormCode;
	}

	public String getPokemonCategory() {
		return pokemonCategory;
	}

	public List<PokemonType> getPokemonTypeList() {
		return pokemonTypeList;
	}

	public boolean hasDualType() {
		return pokemonTypeList.size() == 2;
	}

	public PokemonType getPrimaryType() {
		return pokemonTypeList.getFirst();
	}

	public PokemonType getSecondaryType() {
		if (hasDualType()) {
			return pokemonTypeList.get(1);
		}
		return PokemonType.TYPELESS;
	}

	public List<EggGroup> getEggGroups() {
		return eggGroups;
	}

	public EggGroup getEggGroup(int ordinal) {
		if (ordinal <= 0 || ordinal > eggGroups.size()) {
			throw new IllegalArgumentException("Id of Egg Group is out of range.");
		}
		return eggGroups.get(ordinal - 1);
	}

	public BodyColor getColor() {
		return color;
	}

	public BodyShape getShape() {
		return shape;
	}

	public double getHeight() {
		return height;
	}

	public double getWeight() {
		return weight;
	}

	public boolean hasGenderAppearanceDifference() {
		return genderAppearanceDifference;
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public static class Builder {
		private final int pokemonSpecies;
		private final int pokemonFormId;
		private final String pokemonFormCode;
		private String pokemonCategory;
		private final List<PokemonType> pokemonTypeList = new ArrayList<>();
		private final List<EggGroup> eggGroups = new ArrayList<>();
		private BodyColor color;
		private BodyShape shape;
		private double height = 0.0;
		private double weight = 0.0;
		private boolean genderAppearanceDifference = false;

		public Builder(int pokemonSpecies, int pokemonFormId, String pokemonFormCode) {
			this.pokemonSpecies = pokemonSpecies;
			this.pokemonFormId = pokemonFormId;
			this.pokemonFormCode = StringUtils.upperSnakeCase(pokemonFormCode);
		}

		public Builder setCategory(String pokemonCategory) {
			this.pokemonCategory = StringUtils.upperSnakeCase(pokemonCategory);
			return this;
		}

		public Builder setType(List<String> pokemonTypeList) {
			this.pokemonTypeList.clear();
			if (pokemonTypeList != null) {
				if (pokemonTypeList.isEmpty() || pokemonTypeList.size() > 2) {
					throw new IllegalArgumentException("Number of Types is out of range.");
				}

				for (String pokemonType : pokemonTypeList) {
					PokemonType type = PokemonType.fromCode(pokemonType);
					if (type != null && !this.pokemonTypeList.contains(type)) {
						this.pokemonTypeList.add(type);
					}
				}
			}
			return this;
		}

		public Builder setEggGroups(List<String> eggGroupList) {
			this.eggGroups.clear();
			if (eggGroupList != null) {
				if (eggGroupList.isEmpty() || eggGroupList.size() > 2) {
					throw new IllegalArgumentException("Number of Egg Groups is out of range.");
				}

				for (String eggGroupCode : eggGroupList) {
					EggGroup eggGroup = EggGroup.fromCode(eggGroupCode);
					if (eggGroup != null && !this.eggGroups.contains(eggGroup)) {
						this.eggGroups.add(eggGroup);
					}
				}
			}
			return this;
		}

		public Builder setAppearance(String color, String shape, boolean genderAppearanceDifference) {
			BodyColor bodyColor = BodyColor.fromCode(color);
			BodyShape bodyShape = BodyShape.fromCode(shape);

			if (bodyColor != null && bodyShape != null) {
				this.color = bodyColor;
				this.shape = bodyShape;
				this.genderAppearanceDifference = genderAppearanceDifference;
			}

			return this;
		}

		public Builder setDimension(double height, double weight) {
			this.height = height;
			this.weight = weight;
			return this;
		}

		public PokemonForm build() {
			return new PokemonForm(this);
		}
	}
}
