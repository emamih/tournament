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
		SUPER.call(this, 2, sessionContext);
	}
	de.htw.tournament.NavigationController.prototype = Object.create(SUPER.prototype);
	de.htw.tournament.NavigationController.prototype.constructor = de.htw.tournament.NavigationController;
	}
	
	/**
	 * Displays the associated view.
	 */
	de.htw.tournament.NavigationController.prototype.display = function () {
		SUPER.prototype.display.call(this);
		this.displayStatus(200, "OK");
		

		
		var viewElement = document.querySelector("horizontal");

		var logoSliderElements = viewElement.querySelectorAll("div.image-slider");
		
		var self = this;
		var resource = "/services/tournaments");
		de.sb.util.AJAX.invoke(resource, "GET", {"Accept": "application/json"}, null, this.sessionContext, function (request) {
			self.displayStatus(request.status, request.statusText);

			var tournamentsIdentities = [];
			if (request.status === 200) {
				var responseBody = JSON.parse(request.responseText).tournaments;
				var tournaments = responseBody == "" ? [] : (Array.isArray(responseBody.tournaments) ? responseBody.tournaments : [responseBody.tournaments]);
	
				tournaments.forEach( function (tournaments) {
					self.entityCache.put(tournaments);
					if (tournaments.identity !== self.sessionContext.tournaments.identity) {
						tournamentsIdentities.push(tournaments.identity);
					}
				});
			}
	
			var avar.avatar-slider");
			self.refreshAvatarSlider(avatarSliderElements[2], personIdentities, self.toggleMonitorTarget);
		this.refreshCompetitionSlider(logoSliderElements[0], this.sessionContext.user.monitorSourceIdentities, this.toggleMonitorTarget);
		
		this.refreshCompititionSlider(logoSliderElements[1], this.sessionContext.user.monitorTargetIdentities, this.toggleMonitorTarget);
	}

	
}