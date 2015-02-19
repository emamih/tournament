/**
 * de.htw.broker.Controller: abstract controller.
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
	de.htw.tournament.RootGameController = function (sessionContext) {
		SUPER.call(this, 0);
	}
	de.htw.tournament.RootGameController.prototype = Object.create(SUPER.prototype);
	de.htw.tournament.RootGameController.prototype.constructor = de.htw.tournament.ClosedAuctionsController;


	/**
	 * Displays the associated view.
	 */
	de.htw.tournament.RootGameController.prototype.display = function () {
		SUPER.prototype.display.call(this);
		this.displayStatus(200, "OK");
		

		
//		var viewElement = document.querySelector("horizontal");
		
		var main_element = document.querySelector("main");
		
		var competitions_slider = document.querySelectorAll("div.image-slider")[1];
		
		var selected_competition = competitions_slider.querySelector("a.selected");
		
		if(selected_competition != undefined){
			var competition = selected_competition.title;
			
			var self = this;
			var divisions;
			var resource = "/services/competitions/"+competition+"/divisions";
			de.htw.tournament.AJAX.invoke(resource, "GET", {"Accept": "application/json"}, null, this.sessionContext, function (request) {
				self.displayStatus(request.status, request.statusText);

				var divisionsIdentities = [];
				if (request.status === 200) {
					var responseBody = JSON.parse(request.responseText).divisions;
					divisions = responseBody == "" ? [] : (Array.isArray(responseBody.division) ? responseBody.division : [responseBody.division]);
		
					divisions.forEach( function (division) {
							var root_games_template = document.querySelector("#root-games-template").content.cloneNode(true);
							root_games_template.querySelector("output").innerHTML = division.alias;
							main_element.appendChild(root_games_template);
					});
					
				}
			});
		}
		
		
	}
	
} ());