package com.gameplay.masterdata.template.starpartner;

import com.gameplay.masterdata.data.master.registry.move.starnexus.StarNexusMoveRegistry;
import com.gameplay.masterdata.data.master.registry.pokemon.PokemonRegistry;
import com.gameplay.masterdata.template.move.starnexus.StarNexusMove;
import com.gameplay.masterdata.template.pokemon.form.PokemonForm;
import com.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PartnerState {

	//==================================================================================================================
	//	Field and Properties
	//==================================================================================================================

	private static final int MAX_BASE_MOVE_SLOT = 4;

	private final int stateId;
	private final String stateCode;
	private final PokemonForm partnerPokemonForm;
	private final StarNexusMove starNexusMove;
	private final List<MoveSlot> moveSlotList;

	//==================================================================================================================
	//	Constructor
	//==================================================================================================================

	private PartnerState(Builder builder) {
		this.stateId = builder.stateId;
		this.stateCode = builder.stateCode;
		this.partnerPokemonForm = builder.partnerPokemonForm;
		this.starNexusMove = builder.starNexusMove;
		this.moveSlotList = List.copyOf(builder.moveSlotList);
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

	public List<MoveSlot> getMoveSlotList() {
		return moveSlotList;
	}

	public Optional<MoveSlot> getMoveSlot(int moveSlot) {
		for (MoveSlot slot : moveSlotList) {
			if (slot.getMoveSlot() == moveSlot) {
				return Optional.of(slot);
			}
		}
		return Optional.empty();
	}

	//==================================================================================================================
	//	Builder
	//==================================================================================================================

	public static class Builder {
		private final int stateId;
		private final String stateCode;
		private PokemonForm partnerPokemonForm;
		private StarNexusMove starNexusMove;
		private final List<MoveSlot> moveSlotList = new ArrayList<>();

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

		public void addMoveSlot(MoveSlot moveSlot) {
			if (moveSlot == null || moveSlot.getMove() == null) {
				return;
			}

			int slot = moveSlot.getMoveSlot();

			if (slot < 1 || slot > MAX_BASE_MOVE_SLOT) {
				throw new IllegalArgumentException("An Unit has only 4 base Move. Slot Number " + slot + " is out of range.");
			}

			for (int count = 0; count < moveSlotList.size(); ++count) {
				if (moveSlotList.get(count).getMoveSlot() == slot) {
					moveSlotList.set(count, moveSlot);
					return;
				}
			}

			int moveSlotPosition = 0;
			while (moveSlotPosition < moveSlotList.size() && moveSlotList.get(moveSlotPosition).getMoveSlot() < slot) {
				moveSlotPosition++;
			}

			moveSlotList.add(moveSlotPosition, moveSlot);
		}

		public PartnerState build() {
			return new PartnerState(this);
		}
	}
}
