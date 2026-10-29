package com.gameplay.masterdata.template.starpartner;

import com.gameplay.masterdata.data.master.dto.starpartner.MoveSlotDTO;
import com.gameplay.masterdata.data.master.registry.move.starnexus.StarNexusMoveRegistry;
import com.gameplay.masterdata.data.master.registry.pokemon.PokemonRegistry;
import com.gameplay.masterdata.template.move.Move;
import com.gameplay.masterdata.template.move.starnexus.StarNexusMove;
import com.gameplay.masterdata.template.pokemon.form.PokemonForm;
import com.utils.StringUtils;

import java.util.*;

public class PartnerState {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private static final int MAX_BASE_MOVE_SLOT = 4;

	private final int stateId;
	private final String stateCode;
	private final PokemonForm partnerPokemonForm;
	private final StarNexusMove starNexusMove;
	private final Map<Integer, Move> moveSlotMap;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private PartnerState(Builder builder) {
		this.stateId = builder.stateId;
		this.stateCode = builder.stateCode;
		this.partnerPokemonForm = builder.partnerPokemonForm;
		this.starNexusMove = builder.starNexusMove;
		this.moveSlotMap = Collections.unmodifiableMap(new TreeMap<>(builder.moveSlotMap));
	}

	//==================================================================================================================
	//	Getters
	//==================================================================================================================

	public int getStateId() {
		return stateId;
	}

	public String getStateCode() {
		return stateCode;
	}

	public PokemonForm getPartnerPokemonForm() {
		return partnerPokemonForm;
	}

	public StarNexusMove getStarNexusMove() {
		return starNexusMove;
	}

	public Collection<Move> getMoveSlots() {
		return Collections.unmodifiableCollection(moveSlotMap.values());
	}

	public Move getMove(int moveSlot) {
		return moveSlotMap.get(moveSlot);
	}

	public <M extends Move> M getMove(int moveSlot, Class<M> moveClass) {
		return moveClass.cast(getMove(moveSlot));
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public static class Builder {
		private final int stateId;
		private final String stateCode;
		private PokemonForm partnerPokemonForm;
		private StarNexusMove starNexusMove;
		private final Map<Integer, Move> moveSlotMap = new TreeMap<>();

		public Builder(int stateId, String stateCode) {
			this.stateId = stateId;
			this.stateCode = StringUtils.upperSnakeCase(stateCode);
		}

		public Builder setPartnerPokemonForm(String pokemonForm) {
			this.partnerPokemonForm = PokemonRegistry.getPokemonFormByCode(pokemonForm);
			return this;
		}

		public Builder setStarNexusMove(String starNexusMove) {
			this.starNexusMove = StarNexusMoveRegistry.getStarNexusMoveByCode(starNexusMove);
			return this;
		}

		public void addMoveSlot(int moveSlot, MoveSlotDTO moveSlotDTO) {
			if (moveSlotDTO == null) {
				return;
			}

			if (moveSlot <= 0 || moveSlot > MAX_BASE_MOVE_SLOT) {
				throw new IllegalArgumentException("Move Slot is out of range : " + moveSlot);
			}

			Move move = moveSlotDTO.create();
			if (move == null) {
				return;
			}

			this.moveSlotMap.put(moveSlot, move);
		}

		public PartnerState build() {
			return new PartnerState(this);
		}
	}
}
