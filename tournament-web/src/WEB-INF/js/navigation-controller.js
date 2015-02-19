this.de = this.de || {};
this.de.htw = this.de.htw || {};
this.de.htw.tournament = this.de.htw.tournament || {};
(function () {
	var SUPER = de.htw.tournament.Controller;

	/**
	 * Creates a new auctions controller that is derived from an abstract controller.
	 * @param sessionContext {de.htw.tournament.SessionContext} a session context
	 */
	de.htw.tournament.NavigationController = function (sessionContext) {
		SUPER.call(this, -1);
	}
	de.htw.tournament.NavigationController.prototype = Object.create(SUPER.prototype);
	de.htw.tournament.NavigationController.prototype.constructor = de.htw.tournament.NavigationController;
	
	/**
	 * Displays the associated view.
	 */
	de.htw.tournament.NavigationController.prototype.display = function () {
		SUPER.prototype.display.call(this);
		this.displayStatus(200, "OK");
		

		
//		var viewElement = document.querySelector("horizontal");

		var logoSliderElements = document.querySelectorAll("div.image-slider");
		
		var self = this;
		var tournaments;
		var resource = "/services/tournaments";
		de.htw.tournament.AJAX.invoke(resource, "GET", {"Accept": "application/json"}, null, this.sessionContext, function (request) {
			self.displayStatus(request.status, request.statusText);

			var tournamentsIdentities = [];
			if (request.status === 200) {
				var responseBody = JSON.parse(request.responseText).tournaments;
				tournaments = responseBody == "" ? [] : (Array.isArray(responseBody.tournament) ? responseBody.tournament : [responseBody.tournament]);
	
				tournaments.forEach( function (tournament) {
						tournamentsIdentities.push(tournament.identity);
				});
				self.refreshSlider(logoSliderElements[0], tournamentsIdentities, "tournaments");
				
			}
		});
		
		self.refreshCompetitionSlider("1");
		
		
	}
	
	de.htw.tournament.NavigationController.prototype.refreshSlider = function (sliderElement, identities, pathelEment) {
		while (sliderElement.lastChild) sliderElement.removeChild(sliderElement.lastChild);

		var self = this;
		identities.forEach( function (identity) {
				var imageElement = document.createElement("img");
				imageElement.src = "/services/"+pathelEment+"/" + identity + "/logo";

				var anchorElement = document.createElement("a");
				anchorElement.appendChild(imageElement);
				
//				anchorElement.appendChild(document.createTextNode(person.alias));
//				anchorElement.title = person.name.given + " " + person.name.family;
				if(pathelEment=="tournaments")anchorElement.onclick = self.refreshCompetitionSlider.bind(self, identity);
				else {
					anchorElement.title = identity;
					anchorElement.onclick = self.selectImage.bind(self, anchorElement);
				}
				sliderElement.appendChild(anchorElement);
		});
}
	
	de.htw.tournament.NavigationController.prototype.refreshCompetitionSlider = function (tournamentId) {
		var logoSliderElements = document.querySelectorAll("div.image-slider");
		var resource = "/services/tournaments/"+tournamentId+"/competitions";
		var self = this;
		de.htw.tournament.AJAX.invoke(resource, "GET", {"Accept": "application/json"}, null, this.sessionContext, function (request) {
	
			var competitionIdentities = [];
			if (request.status === 200) {
				var responseBody = JSON.parse(request.responseText).competitions;
				var competitions = responseBody == "" ? [] : (Array.isArray(responseBody.competition) ? responseBody.competition : [responseBody.competition]);
	
				competitions.forEach( function (competition) {
					competitionIdentities.push(competition.identity);
				});
				self.refreshSlider(logoSliderElements[1], competitionIdentities, "competitions");
			}
		});
	}
	
	de.htw.tournament.NavigationController.prototype.selectImage = function (anchorElement) {
		var competitionsSliderElements = document.querySelectorAll("div.image-slider")[1];
		var menuElements = competitionsSliderElements.querySelectorAll("a");

		for (var run = 0; run < menuElements.length; ++run) {
				menuElements[run].classList.remove("selected");
		}
		anchorElement.classList.add("selected");
	}

	
} ());