/**
 * de.sb.broker.APPLICATION: tournament application singleton.
 * Copyright (c) 2015-2015 Sascha Baumeister
 */
"use strict";

this.de = this.de || {};
this.de.htw = this.de.htw || {};
this.de.htw.tournament = this.de.htw.tournament || {};
(function () {

	/**
	 * The tournament application singleton maintaining the view controllers.
	 */
	de.htw.tournament.APPLICATION = {
		competitionIdentity: null,
		navigationController: "NavigationController" in de.htw.tournament
			? new de.htw.tournament.NavigationController()
			: new de.htw.tournament.Controller(-1),
		rootGameController: "RootGameController" in de.htw.tournament
			? new de.htw.tournament.RootGameController()
			: new de.htw.tournament.Controller(0),
		scoreSheetController: "ScoreSheetController" in de.htw.tournament
			? new de.htw.tournament.ScoreSheetController()
			: new de.htw.tournament.Controller(1),
		derivedGameController: "DerivedGameController" in de.htw.tournament
			? new de.htw.tournament.DerivedGameController()
			: new de.htw.tournament.Controller(2),

	}
	var APPLICATION = de.htw.tournament.APPLICATION;


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