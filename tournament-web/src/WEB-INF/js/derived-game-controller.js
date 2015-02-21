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

	var derivedGamesCache = [];
	var counter =1;
	
	/**
	 * Displays the associated view.
	 */
	de.htw.tournament.DerivedGameController.prototype.display = function () {
		SUPER.prototype.display.call(this);
		this.displayStatus(200, "OK");
		

		
//		var viewElement = document.querySelector("horizontal");
		
		var main_element = document.querySelector("main");
		
		var competitions_slider = document.querySelectorAll("div.image-slider")[1];
		
		var selected_competition = competitions_slider.querySelector("a.selected");
		
		var divisions;
		var gameIdentities = [];
		var self = this;
		
		if(selected_competition != undefined){
			var competition = selected_competition.title;
			
			var resource = "/services/competitions/"+competition+"/divisions/derived-games";
			de.htw.tournament.AJAX.invoke(resource, "GET", {"Accept": "application/json"}, null, this.sessionContext, function (request) {
				self.displayStatus(request.status, request.statusText);

				if (request.status === 200) {
					var responseBody = JSON.parse(request.responseText).games;
					var games = responseBody == "" ? [] : (Array.isArray(responseBody.game) ? responseBody.game : [responseBody.game]);
					
					var derived_games_template = document.querySelector("#derived-games-template").content.cloneNode(true);
					derived_games_template.querySelector("output").innerHTML = "1/8 Final";
					derived_games_template.querySelector(".derived-games").id = "finale"+counter;
					main_element.appendChild(derived_games_template);
					
					games.forEach( function (game) {
							
							
						gameIdentities.push(game);

					});
					
					self.printDerivedGames(gameIdentities,"finale"+counter,"");
					counter++;
				}
			});
			
			
		}
	}
	
	de.htw.tournament.DerivedGameController.prototype.getDerivedGames = function (main_element,element,path) {
		var self = this;
		
		de.htw.tournament.AJAX.invoke(path, "GET", {"Accept": "application/json"}, null, this.sessionContext, function (request) {
			var derivedGamesTemp = {};
			if (request.status === 200) {
				var responseBody = JSON.parse(request.responseText).games;
				var games = responseBody == "" ? [] : (Array.isArray(responseBody.game) ? responseBody.game : [responseBody.game]);
				var i = 1;
				games.forEach( function (game) {
					derivedGamesTemp[game.identity] = game;
				});
				var keys = [];
			    for (var key in derivedGamesCache) {
			      if (derivedGamesCache.hasOwnProperty(key)) {
			        keys.push(key);
			      }
			    }
			    keys.sort ();
			    for (i in keys) {
			      var key = keys[i];
			      var value = things[key];
			      derivedGamesCache[i]=value;
			    }
			    
			    var path = "/services/divisions/"+division.identity+"/derived-games";
				
				self.refreshScoreSheet(main_element,"division"+division.identity,path);
			}
		});
	}
	
	de.htw.tournament.DerivedGameController.prototype.printDerivedGames = function (games,element,path) {
		var self = this;
		
		games.forEach( function (game) {

			var derived_game_template = document.querySelector("#derived-game-template").content.cloneNode(true);
			var outer = derived_game_template.querySelectorAll(".derived-game > div");
			
			outer[0].querySelector("img").src = "/services/competitors/" + game.leftCompetitor.identity + "/logo";
			outer[2].querySelector("img").src = "/services/competitors/" + game.rightCompetitor.identity + "/logo";
			
			outer[0].querySelector("output").innerHTML = game.leftCompetitor.alias;
			outer[2].querySelector("output").innerHTML = game.rightCompetitor.alias;
			
			outer[0].querySelector("input").value = game.leftScore;
			outer[2].querySelector("input").value = game.rightScore;
			
			var dom_element = document.querySelector("#"+element);
			dom_element.appendChild(derived_game_template);
		});
		
//		var keys = [];
//	    for (var key in derivedGamesTemp) {
//	      if (derivedGamesTemp.hasOwnProperty(key)) {
//	        keys.push(key);
//	      }
//	    }
//	    keys.sort ();
//	    for (i in keys) {
//	      var key = keys[i];
//	      var value = derivedGamesTemp[key];
//	      derivedGamesCache[i]=value;
//	    }
//	    for (var key in derivedGamesTemp) {
//		  if (derivedGamesTemp.hasOwnProperty(key)) {
//		    keys.push(key);
//		  }
//		}
//	    games.forEach( function (game) {
//			derivedGamesTemp[game.identity] = game;
//		});
	}
} ());