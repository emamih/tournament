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
		
		var divisions;
		var divisionsIdentities = [];
		var self = this;
		
		if(selected_competition != undefined){
			var competition = selected_competition.title;
			var root_games_template;
			
			var resource = "/services/competitions/"+competition+"/divisions";
			de.htw.tournament.AJAX.invoke(resource, "GET", {"Accept": "application/json"}, null, this.sessionContext, function (request) {
				self.displayStatus(request.status, request.statusText);

				if (request.status === 200) {
					var responseBody = JSON.parse(request.responseText).divisions;
					divisions = responseBody == "" ? [] : (Array.isArray(responseBody.division) ? responseBody.division : [responseBody.division]);
		
					divisions.forEach( function (division) {
							root_games_template = document.querySelector("#root-games-template").content.cloneNode(true);
							root_games_template.querySelector("output").innerHTML = division.alias;
							var path = "/services/divisions/"+division.identity+"/root-games";
							root_games_template.querySelector("div").id = "division"+division.identity;
							main_element.appendChild(root_games_template);
							
							self.refreshDivision(main_element,"division"+division.identity,path);
							
							
					});
					
				}
			});
			
			
		}
	}
	
	de.htw.tournament.RootGameController.prototype.refreshDivision = function (main_element,element,path) {
		var self = this;
		
		de.htw.tournament.AJAX.invoke(path, "GET", {"Accept": "application/json"}, null, this.sessionContext, function (request) {
	
			var gameIdentities = [];
			if (request.status === 200) {
				var responseBody = JSON.parse(request.responseText).games;
				var games = responseBody == "" ? [] : (Array.isArray(responseBody.game) ? responseBody.game : [responseBody.game]);
	
				games.forEach( function (game) {
					//Logos
					var root_game_template = document.querySelector("#root-game-template").content.cloneNode(true);
					var outer = root_game_template.querySelectorAll("div.root-game > span")[0];
					var temp = outer.querySelectorAll("img")[0];
					temp.src = "/services/competitors/" + game.leftCompetitor.identity + "/logo";
					temp = outer.querySelectorAll("img")[1];
					temp.src = "/services/competitors/" + game.rightCompetitor.identity + "/logo";
					//Namen
					temp = outer.querySelectorAll("output")[0];
					temp.innerHTML = game.leftCompetitor.alias;
					temp = outer.querySelectorAll("output")[1];
					temp.innerHTML = game.rightCompetitor.alias;
					//Score
					outer = root_game_template.querySelectorAll("div.root-game > span")[1];
					temp = outer.querySelectorAll("input")[0];
					temp.value = game.leftScore;
					temp = outer.querySelectorAll("input")[1];
					temp.value = game.rightScore;
					
					var dom_element = main_element.querySelector("#"+element);
					dom_element.appendChild(root_game_template);
				});
			}
		});
	}
	
	
} ());