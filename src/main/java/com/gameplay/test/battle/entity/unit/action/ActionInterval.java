package com.gameplay.test.battle.entity.unit.action;

import com.gameplay.battle.calculator.ActionIntervalCalculator;

public class ActionInterval {

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	private double baseActionInterval;
	private double remainingAction;
	private double actionSprint;
	private double actionAcceleration;

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	public ActionInterval(double baseActionSpeed) {
		this.baseActionInterval = baseActionInterval;
		this.remainingAction = this.baseActionInterval;
		this.actionSprint = 0.0;
		this.actionAcceleration = 0.0;
	}

	public ActionInterval(int speed) {
		this.baseActionInterval = ActionIntervalCalculator.speedToActionSpeed(speed);
		this.remainingAction = this.baseActionInterval;
		this.actionSprint = 0.0;
		this.actionAcceleration = 0.0;
	}

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	public double getBaseActionInterval() {
		return baseActionInterval;
	}

	public double getRemainingAction() {
		return remainingAction;
	}

	public double getActionSprint() {
		return actionSprint;
	}

	public double getActionAcceleration() {
		return actionAcceleration;
	}

	public double getAcceleration() {
		return actionSprint + this.baseActionInterval * actionAcceleration;
	}

	public double getCurrentActionInterval() {
		return Math.max(0, this.remainingAction - getAcceleration());
	}

	public boolean readyForAction() {
		return getCurrentActionInterval() <= 0;
	}

	public void setBaseActionInterval(double baseActionInterval) {
		this.baseActionInterval = baseActionInterval;
	}

	public void setBaseActionInterval(int speed) {
		this.baseActionInterval = ActionIntervalCalculator.speedToActionSpeed(speed);
	}

	/*------------------------------------------------------------------------------------------------------------------
																														*/

	void reset(double remainingAction) {
		this.remainingAction = Math.max(0, remainingAction);
		this.actionSprint = 0.0;
		this.actionAcceleration = 0.0;
	}

	public void elapse(double elapsedTime) {
		this.remainingAction = Math.max(0, this.remainingAction - elapsedTime);
	}

	public void actionSprint(double actionSprint) {
		this.actionSprint += actionSprint;
	}

	public void actionImpair(double actionSprint) {
		this.actionSprint -= actionSprint;
	}

	public void actionAcceleration(double actionAcceleration) {
		this.actionAcceleration += actionAcceleration;
	}

	public void actionDelay(double actionAcceleration) {
		this.actionAcceleration -= actionAcceleration;
	}
}
