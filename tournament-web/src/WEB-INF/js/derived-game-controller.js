/**
 * de.htw.tournament.Controller: abstract controller.
 * Copyright (c) 2013-2015 Sascha Baumeister
 */
"use strict";

this.de = this.de || {};
this.de.htw = this.de.htw || {};
this.de.htw.tournament = this.de.htw.tournament || {};
(function () {

	var SUPER = de.htw.tournament.Controller;

	/**
	 * Creates a new auctions controller that is derived from an abstract controller.
	 * @param sessionContext {de.htw.tournament.SessionContext} a session context
	 */
	de.htw.tournament.DerivedGameController = function (sessionContext) {
		SUPER.call(this, 2);
	}
	de.htw.tournament.DerivedGameController.prototype = Object.create(SUPER.prototype);
	de.htw.tournament.DerivedGameController.prototype.constructor = de.htw.tournament.ClosedAuctionsController;


	/**
	 * Displays the associated view.
	 */
	de.htw.tournament.DerivedGameController.prototype.display = function () {
		SUPER.prototype.display.call(this);
		this.displayStatus(200, "OK");
	
	}
} ());