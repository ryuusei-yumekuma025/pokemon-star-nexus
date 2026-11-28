package com.gameplay.data.masterdata.data.master.registry.trainer;

import com.gameplay.data.masterdata.attribute.trainer.NPCTrainerClass;
import com.gameplay.data.masterdata.data.master.registry.Registry;
import com.gameplay.data.masterdata.template.trainer.Trainer;
import com.gameplay.data.masterdata.template.trainer.instance.NPCTrainer;
import com.gameplay.data.masterdata.template.trainer.instance.TrainerAlternate;
import com.gameplay.data.masterdata.template.trainer.TrainerIdentity;
import com.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class TrainerRegistry {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Data Registry																									*/

	private static final Registry<String, TrainerIdentity> IDENTITY_REGISTRY = new Registry<>(
			"TrainerIdentityRegistry", "TrainerIdentity",
			TrainerIdentity::getTrainerIdentityId, TrainerIdentity::getTrainerIdentityCode);

	private static final Registry<String, TrainerAlternate> ALTERNATE_REGISTRY = new Registry<>(
			"TrainerAlternateRegistry", "TrainerAlternate",
			TrainerAlternate::getTrainerId, TrainerAlternate::getTrainerCode);

	private static final Registry<String, NPCTrainer> NPC_TRAINER_REGISTRY = new Registry<>(
			"NPCTrainerRegistry", "NPCTrainer",
			NPCTrainer::getTrainerId, NPCTrainer::getTrainerCode);

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private TrainerRegistry() {}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Data Registry Mode																								*/

	public static synchronized void freeze() {
		IDENTITY_REGISTRY.freeze();
		ALTERNATE_REGISTRY.freeze();
		NPC_TRAINER_REGISTRY.freeze();
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Register																										*/

	public static synchronized void register(TrainerIdentity trainerIdentity) {
		if (trainerIdentity == null) {
			return;
		}

		IDENTITY_REGISTRY.register(trainerIdentity);

		Collection<TrainerAlternate> trainerAlternateCollection = trainerIdentity.getAllAlternates();
		if (trainerAlternateCollection == null) {
			return;
		}

		for (TrainerAlternate trainerAlternate : trainerAlternateCollection) {
			ALTERNATE_REGISTRY.register(trainerAlternate);
		}
	}

	public static synchronized void register(NPCTrainer npcTrainer) {
		if (npcTrainer == null) {
			return;
		}

		NPC_TRAINER_REGISTRY.register(npcTrainer);
	}

	/*------------------------------------------------------------------------------------------------------------------
	5.	Identity Search and Getters																						*/

	public static TrainerIdentity getIdentityById(String id) {
		id = StringUtils.requireUpperSnakeCase(id, "Trainer Identity Id");
		if (!id.startsWith("TRI_")) {
			id = "TRI_" + id;
		}
		return IDENTITY_REGISTRY.getById(id);
	}

	public static TrainerIdentity getIdentityByCode(String code) {
		return IDENTITY_REGISTRY.getByCode(code);
	}

	public static Collection<TrainerIdentity> getAllTrainerIdentities() {
		return IDENTITY_REGISTRY.getAll();
	}

	public static int getNumberOfTrainerIdentities() {
		return IDENTITY_REGISTRY.size();
	}

	/*------------------------------------------------------------------------------------------------------------------
	6.	Alternate Search and Getters																					*/

	public static TrainerAlternate getAlternateById(String id) {
		id = StringUtils.requireUpperSnakeCase(id, "Trainer Alternate Id");
		if (!id.startsWith("TRA_")) {
			id = "TRA_" + id;
		}
		return ALTERNATE_REGISTRY.getById(id);
	}

	public static TrainerAlternate getAlternateByCode(String code) {
		return ALTERNATE_REGISTRY.getByCode(code);
	}

	public static Collection<TrainerAlternate> getAllTrainerAlternates() {
		return ALTERNATE_REGISTRY.getAll();
	}

	public static int getNumberOfTrainerAlternates() {
		return ALTERNATE_REGISTRY.size();
	}

	/*------------------------------------------------------------------------------------------------------------------
	7.	NPC Search and Getters																							*/

	public static NPCTrainer getNPCById(String id) {
		id = StringUtils.requireUpperSnakeCase(id, "NPC Id");
		if (!id.startsWith("NPC_")) {
			id = "NPC_" + id;
		}
		return NPC_TRAINER_REGISTRY.getById(id);
	}

	public static NPCTrainer getNPCByCode(String code) {
		return NPC_TRAINER_REGISTRY.getByCode(code);
	}

	public static NPCTrainer getNPCByTrainerClassAndName(String trainerClass, String name) {
		return NPC_TRAINER_REGISTRY.getByCode(trainerClass + "_" + name);
	}

	public static NPCTrainer getNPCByTrainerClassAndName(NPCTrainerClass trainerClass, String name) {
		if (trainerClass == null) {
			return null;
		}
		return getNPCByTrainerClassAndName(trainerClass.name(), name);
	}

	public static Collection<NPCTrainer> getAllNPCTrainers() {
		return NPC_TRAINER_REGISTRY.getAll();
	}

	public static int getNumberOfNPCTrainers() {
		return NPC_TRAINER_REGISTRY.size();
	}

	/*------------------------------------------------------------------------------------------------------------------
	8.	Trainer Search and Getters																						*/

	public static Trainer getTrainerById(String id) {
		id = StringUtils.requireUpperSnakeCase(id, "Trainer Id");
		if (id.startsWith("TRA_")) {
			return getAlternateById(id);
		} else if (id.startsWith("NPC_")) {
			return getNPCById(id);
		}

		Trainer trainer = ALTERNATE_REGISTRY.getById(id);
		try {
			return ALTERNATE_REGISTRY.getById("TRA_" + id);
		} catch (IllegalArgumentException e) {
			return NPC_TRAINER_REGISTRY.getById("NPC_" + id);
		}
	}

	public static Trainer getTrainerByCode(String code) {
		try {
			return ALTERNATE_REGISTRY.getByCode(code);
		} catch (IllegalArgumentException e) {
			return NPC_TRAINER_REGISTRY.getByCode(code);
		}
	}

	public static Collection<Trainer> getAllTrainers() {
		Collection<TrainerAlternate> alternates = ALTERNATE_REGISTRY.getAll();
		Collection<NPCTrainer> npcs = NPC_TRAINER_REGISTRY.getAll();

		List<Trainer> trainers = new ArrayList<>(alternates.size() + npcs.size());
		trainers.addAll(alternates);
		trainers.addAll(npcs);
		return Collections.unmodifiableList(trainers);
	}

	public static int getNumberOfTrainers() {
		return getNumberOfTrainerAlternates() + getNumberOfNPCTrainers();
	}

	/*------------------------------------------------------------------------------------------------------------------
	9.	Clear and Reset																									*/

	public static synchronized void clear() {
		IDENTITY_REGISTRY.clear();
		ALTERNATE_REGISTRY.clear();
		NPC_TRAINER_REGISTRY.clear();
	}

	public static synchronized void reset() {
		IDENTITY_REGISTRY.reset();
		ALTERNATE_REGISTRY.reset();
		NPC_TRAINER_REGISTRY.reset();
	}
}
