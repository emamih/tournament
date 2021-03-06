/**
 * de.htw.tournament.Controller: abstract controller.
 * Copyright (c) 2015-2015 Sascha Baumeister
 */
"use strict";

this.de = this.de || {};
this.de.htw = this.de.htw || {};
this.de.htw.tournament = this.de.htw.tournament || {};
(function () {

	/**
	 * Creates an "abstract" controller.
	 * @param viewOrdinal {Number} the ordinal of the view associated with this controller
	 */
	de.htw.tournament.Controller = function (viewOrdinal) {
		this.viewOrdinal = viewOrdinal;
	}


	/**
	 * Displays the view associated with this controller by marking said
	 * view's menu item as selected, and removing the main element's
	 * children.
	 */
	de.htw.tournament.Controller.prototype.display = function () {
		var menuElements = document.querySelectorAll("nav:last-of-type li");

		for (var viewOrdinal = 0; viewOrdinal < menuElements.length; ++viewOrdinal) {
			if (viewOrdinal == this.viewOrdinal) {
				menuElements[viewOrdinal].classList.add("selected");
			} else {
				menuElements[viewOrdinal].classList.remove("selected");
			}
		}

		var mainElement = document.querySelector("main");
		while (mainElement.lastChild) {
			mainElement.removeChild(mainElement.lastChild);
		}
	}

	/**
	 * Displays the given HTTP status.
	 * @param code {Number} the status code
	 * @param message {String} the status message
	 */
	de.htw.tournament.Controller.prototype.displayStatus = function (code, message) {
		var outputElement = document.querySelector("body > footer output");
		while (outputElement.lastChild) {
			outputElement.removeChild(outputElement.lastChild);
		}
		outputElement.appendChild(document.createTextNode(code + " " + message));
		if (code <= 299) {
			outputElement.className = "success";
		} else if (code <= 399) {
			outputElement.className = "warning";
		} else {
			outputElement.className = "error";
		}
	}
	
	de.htw.tournament.Controller.prototype.refreshGameScore = function (leftScore,rightScore,identity) {
		var self = this;
		const
		resource = "/services/games/"+identity;
		const
		form = "leftScore=" + leftScore + "&rightScore=" + rightScore;
		de.htw.tournament.AJAX.invoke(resource, "POST", {"Content-type" : "application/x-www-form-urlencoded"}, form, this.sessionContext, function(request) {
			self.display();
		});
	}
} ());