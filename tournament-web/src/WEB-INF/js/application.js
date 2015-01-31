/**
 * de.sb.broker.APPLICATION: tournament application singleton.
 * Copyright (c) 2015-2015 Sascha Baumeister
 */
"use strict";

this.de = this.de || {};
this.de.sb = this.de.sb || {};
this.de.sb.tournament = this.de.sb.tournament || {};
(function () {

	/**
	 * The tournament application singleton maintaining the view controllers.
	 */
	de.sb.tournament.APPLICATION = {
		competitionIdentity: null,
		navigationController: "NavigationController" in de.sb.tournament
			? new de.sb.tournament.NavigationController()
			: new de.sb.tournament.Controller(-1),
		rootGameController: "RootGameController" in de.sb.tournament
			? new de.sb.tournament.RootGameController()
			: new de.sb.tournament.Controller(0),
		scoreSheetController: "ScoreSheetController" in de.sb.tournament
			? new de.sb.tournament.ScoreSheetController()
			: new de.sb.tournament.Controller(1),
		derivedGameController: "DerivedGameController" in de.sb.tournament
			? new de.sb.tournament.DerivedGameController()
			: new de.sb.tournament.Controller(2),

	}
	var APPLICATION = de.sb.tournament.APPLICATION;


	/**
	 * Register DOM menu callbacks, and display welcome view.
	 */
	window.addEventListener("load", function () {
		var menuAnchors = document.querySelectorAll("header > nav.horizontal:last-of-type a");
		menuAnchors[0].addEventListener("click", APPLICATION.rootGameController.display.bind(APPLICATION.rootGameController));
		menuAnchors[1].addEventListener("click", APPLICATION.scoreSheetController.display.bind(APPLICATION.scoreSheetController));
		menuAnchors[2].addEventListener("click", APPLICATION.derivedGameController.display.bind(APPLICATION.derivedGameController));
		APPLICATION.navigationController.display();
	});
} ());