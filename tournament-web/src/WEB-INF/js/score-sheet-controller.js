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
	 * @param sessionContext {de.htw.broker.SessionContext} a session context
	 */
	de.htw.tournament.ScoreSheetController = function (sessionContext) {
		SUPER.call(this, 1);
	}
	de.htw.tournament.ScoreSheetController.prototype = Object.create(SUPER.prototype);
	de.htw.tournament.ScoreSheetController.prototype.constructor =de.htw.tournament.ScoreSheetController;


	/**
	 * Displays the associated view.
	 */
	de.htw.tournament.ScoreSheetController.prototype.display = function () {
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
			
			var resource = "/services/competitions/"+competition+"/divisions";
			de.htw.tournament.AJAX.invoke(resource, "GET", {"Accept": "application/json"}, null, this.sessionContext, function (request) {
				self.displayStatus(request.status, request.statusText);

				if (request.status === 200) {
					var responseBody = JSON.parse(request.responseText).divisions;
					divisions = responseBody == "" ? [] : (Array.isArray(responseBody.division) ? responseBody.division : [responseBody.division]);
		
					divisions.forEach( function (division) {
							var score_sheet_template = document.querySelector("#score-sheet-template").content.cloneNode(true);
							score_sheet_template.querySelector("output").innerHTML = division.alias;
							score_sheet_template.querySelector("tbody").id = "division"+division.identity;
							main_element.appendChild(score_sheet_template);
							
							var path = "/services/divisions/"+division.identity+"/score-sheet";
							
							self.refreshScoreSheet(main_element,"division"+division.identity,path);
							
							
					});
					
				}
			});
			
			
		}
	}
	
	de.htw.tournament.ScoreSheetController.prototype.refreshScoreSheet = function (main_element,element,path) {
		var self = this;
		
		de.htw.tournament.AJAX.invoke(path, "GET", {"Accept": "application/json"}, null, this.sessionContext, function (request) {
	
			var scoresheetIdentities = [];
			if (request.status === 200) {
				var responseBody = JSON.parse(request.responseText).scoreSheetEntries;
				var scoresheets = responseBody == "" ? [] : (Array.isArray(responseBody.scoreSheetEntry) ? responseBody.scoreSheetEntry : [responseBody.scoreSheetEntry]);
				var i = 1;
				scoresheets.forEach( function (scoresheet) {
					//Logos
					var score_sheet_template = document.querySelector("#score-sheet-entry-template").content.cloneNode(true);
					var outer = score_sheet_template.querySelectorAll("tr")[0];
					var temp = outer.querySelectorAll("td");
					
					temp[0].querySelector("output").innerHTML = i+".";
					temp[1].querySelector("img").src = "/services/competitors/" + scoresheet.competitor.identity + "/logo";
					temp[1].querySelector("output").src = scoresheet.competitor.alias;
					temp[2].querySelector("output").innerHTML = scoresheet.score;
					temp[3].querySelector("output").innerHTML = scoresheet.opponentScore;
					temp[4].querySelector("output").innerHTML = scoresheet.score-scoresheet.opponentScore;
					temp[5].querySelector("output").innerHTML = scoresheet.points;
					
					var dom_element = main_element.querySelector("#"+element);
					dom_element.appendChild(score_sheet_template);
					i++;
				});
			}
		});
	}
	
} ());