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
	de.htw.tournament.ClosedAuctionsController = function (sessionContext) {
		SUPER.call(this, 2, sessionContext);
	}
	de.htw.tournament.ClosedAuctionsController.prototype = Object.create(SUPER.prototype);
	de.htw.tournament.ClosedAuctionsController.prototype.constructor = de.htw.tournament.ClosedAuctionsController;


	/**
	 * Displays the associated view.
	 */
	de.htw.tournament.ClosedAuctionsController.prototype.display = function () {
}