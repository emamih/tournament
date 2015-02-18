/**
 * de.sb.broker.Controller: abstract controller.
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
	 * @param sessionContext {de.sb.broker.SessionContext} a session context
	 */
	de.htw.tournament.ScoreSheetConstroller = function (sessionContext) {
		SUPER.call(this, 2, sessionContext);
	}
	de.htw.tournament.ScoreSheetConstroller.prototype = Object.create(SUPER.prototype);
	de.htw.tournament.ScoreSheetConstroller.prototype.constructor =de.htw.tournament.ScoreSheetConstroller;


	/**
	 * Displays the associated view.
	 */
	de.htw.tournament.ScoreSheetConstroller.prototype.display = function () {
		
	}
	
}