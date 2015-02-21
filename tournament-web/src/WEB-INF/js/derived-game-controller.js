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
	var layerId = 8;

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

					var counter =1;
					var derived_games_template = document.querySelector("#derived-games-template").content.cloneNode(true);
					derived_games_template.querySelector("output").innerHTML = "1/"+layerId+" Final";
					derived_games_template.querySelector(".derived-games").id = "finale"+counter;
					main_element.appendChild(derived_games_template);

					derived_games_template = document.querySelector("#derived-games-template").content.cloneNode(true);
					derived_games_template.querySelector("output").innerHTML = "1/"+layerId/2+" Final";
					derived_games_template.querySelector(".derived-games").id = "finale"+(counter+1);
					main_element.appendChild(derived_games_template);

					derived_games_template = document.querySelector("#derived-games-template").content.cloneNode(true);
					derived_games_template.querySelector("output").innerHTML = "1/"+layerId/4+" Final";
					derived_games_template.querySelector(".derived-games").id = "finale"+(counter+2);
					main_element.appendChild(derived_games_template);

					derived_games_template = document.querySelector("#derived-games-template").content.cloneNode(true);
					derived_games_template.querySelector("output").innerHTML = "Final";
					derived_games_template.querySelector(".derived-games").id = "finale"+(counter+3);
					main_element.appendChild(derived_games_template);

					games.forEach( function (game) {


						gameIdentities.push(game);

					});

					self.printDerivedGames(gameIdentities,"finale"+counter,"");
				}
			});


		}
	}

//	de.htw.tournament.DerivedGameController.prototype.getDerivedGames = function (main_element,element,path) {
//	var self = this;

//	de.htw.tournament.AJAX.invoke(path, "GET", {"Accept": "application/json"}, null, this.sessionContext, function (request) {
//	var derivedGamesTemp = {};
//	if (request.status === 200) {
//	var responseBody = JSON.parse(request.responseText).games;
//	var games = responseBody == "" ? [] : (Array.isArray(responseBody.game) ? responseBody.game : [responseBody.game]);
//	var i = 1;
//	games.forEach( function (game) {
//	derivedGamesTemp[game.identity] = game;
//	});
//	var keys = [];
//	for (var key in derivedGamesCache) {
//	if (derivedGamesCache.hasOwnProperty(key)) {
//	keys.push(key);
//	}
//	}
//	keys.sort ();
//	for (i in keys) {
//	var key = keys[i];
//	var value = things[key];
//	derivedGamesCache[i]=value;
//	}

//	var path = "/services/divisions/"+division.identity+"/derived-games";

//	self.refreshScoreSheet(main_element,"division"+division.identity,path);
//	}
//	});
//	}

	de.htw.tournament.DerivedGameController.prototype.printDerivedGames = function (games,element,path) {
		var self = this;

		games.forEach( function (game) {

			var derived_game_template = document.querySelector("#derived-game-template").content.cloneNode(true);
			var outer = derived_game_template.querySelectorAll(".derived-game > div");
			derived_game_template.querySelector(".derived-game").id = "derived-game_"+game.identity;

			outer[0].querySelector("img").src = "/services/competitors/" + game.leftCompetitor.identity + "/logo";
			outer[2].querySelector("img").src = "/services/competitors/" + game.rightCompetitor.identity + "/logo";

			outer[0].querySelector("output").innerHTML = game.leftCompetitor.alias;
			outer[2].querySelector("output").innerHTML = game.rightCompetitor.alias;

			outer[0].querySelector("input").value = game.leftScore;
			outer[2].querySelector("input").value = game.rightScore;

			outer[0].querySelector("input").onchange = function() {
				self.refreshGameScore(document.querySelectorAll("#derived-game_"+game.identity+" > div ")[0].querySelectorAll("input")[0].value,
						document.querySelectorAll("#derived-game_"+game.identity+" > div ")[2].querySelectorAll("input")[0].value,
						game.identity);
			}
			outer[2].querySelector("input").onchange = function() {
				self.refreshGameScore(document.querySelectorAll("#derived-game_"+game.identity+" > div ")[0].querySelectorAll("input")[0].value,
						document.querySelectorAll("#derived-game_"+game.identity+" > div ")[2].querySelectorAll("input")[0].value,
						game.identity);
			}
			
			var dom_element = document.querySelector("#"+element);
			dom_element.appendChild(derived_game_template);

			self.printDerivedGame(game.identity,2);
		});
	}

	de.htw.tournament.DerivedGameController.prototype.printDerivedGame = function (gameIdentity,layerCounter) {
		var self = this;

		var resource = "/services/games/"+gameIdentity+"/derived-games";
		de.htw.tournament.AJAX.invoke(resource, "GET", {"Accept": "application/json"}, null, this.sessionContext, function (request) {
			self.displayStatus(request.status, request.statusText);

			if (request.status === 200) {
				var responseBody = JSON.parse(request.responseText).games;
				var games = responseBody == "" ? [] : (Array.isArray(responseBody.game) ? responseBody.game : [responseBody.game]);

				games.forEach( function (game) {

					var currentLayer = document.querySelector("#finale"+layerCounter);
					var insert = true;
					var append = true;
					var insertElement;
					var gamesArr = currentLayer.querySelectorAll(".derived-game");

					for(var i =0; i<gamesArr.length; i++ ){
						var id_game = gamesArr[i].id.split("_")[1];
						if(id_game == game.identity){
							insert=false;
							break;
						} else if (id_game > game.identity){
							insertElement = gamesArr[i];
							append = false ;
							break;
						} else {
							insertElement = gamesArr[i];
							append = true ;
						}
					}


					if (insert) {	
						var derived_game_template = document.querySelector("#derived-game-template").content.cloneNode(true);
						var outer = derived_game_template.querySelectorAll(".derived-game > div");
						derived_game_template.querySelector(".derived-game").id = "derived-game_"+game.identity;

						outer[0].querySelector("img").src = "/services/competitors/" + game.leftCompetitor.identity + "/logo";
						outer[2].querySelector("img").src = "/services/competitors/" + game.rightCompetitor.identity + "/logo";

						outer[0].querySelector("output").innerHTML = game.leftCompetitor.alias;
						outer[2].querySelector("output").innerHTML = game.rightCompetitor.alias;

						outer[0].querySelector("input").value = game.leftScore;
						outer[2].querySelector("input").value = game.rightScore;

						
						
						outer[0].querySelector("input").onchange = function() {
							self.refreshGameScore(document.querySelectorAll("#derived-game_"+game.identity+" > div ")[0].querySelectorAll("input")[0].value,
									document.querySelectorAll("#derived-game_"+game.identity+" > div ")[2].querySelectorAll("input")[0].value,
									game.identity);
						}
						outer[2].querySelector("input").onchange = function() {
							self.refreshGameScore(document.querySelectorAll("#derived-game_"+game.identity+" > div ")[0].querySelectorAll("input")[0].value,
									document.querySelectorAll("#derived-game_"+game.identity+" > div ")[2].querySelectorAll("input")[0].value,
									game.identity);
						}
						
						

						var dom_element = document.querySelector("#finale"+layerCounter);
						if(append){
							if(insertElement!=undefined && insertElement.nextSibling!=undefined)dom_element.insertBefore(derived_game_template, insertElement.nextSibling);
							else dom_element.appendChild(derived_game_template);
						} else {
							dom_element.insertBefore(derived_game_template, insertElement);
						}
						self.printDerivedGame(game.identity,layerCounter+1);
					}

				});
			}
			
			
		});
	}
} ());