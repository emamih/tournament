package de.htw.tournament.rest;

import java.util.Collection;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.htw.tournament.model.Competition;
import de.htw.tournament.model.Division;
import de.htw.tournament.model.Document;
import de.htw.tournament.model.Game;

@Path("competitions")
public class CompetitionService {

	
	@GET
	@Path("{identity}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response queryIdentity (@PathParam("identity") final long competitorIdentity) {
		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();

		try {
			final Competition competiton = entityManager.find(Competition.class, competitorIdentity);
			return competiton == null
				? Response.status(Status.NOT_FOUND).build()
				: Response.ok(competiton).build();
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
	}
	
	@GET
	@Path("{identity}/logo")
	@Produces(MediaType.WILDCARD)
	public Response queryLogo (@PathParam("identity") final long competitorIdentity) {
		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();

		try {
			final Competition competiton = entityManager.find(Competition.class, competitorIdentity);
			if (competiton == null) return Response.status(Status.NOT_FOUND).build();

			final Document logo = competiton.getLogo();
			return Response.ok(logo.getBody(), logo.getType()).build();
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
	}
	
	@GET
	@Path("{identity}/divisions")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response queryDivisions (@PathParam("identity") final long competitorIdentity) {
		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();

		try {

			Competition competiton = entityManager.find(Competition.class, competitorIdentity);
			if (competiton == null) return Response.status(Status.NOT_FOUND).build();
			
			// Sort by ascending identity, implicitly avoiding lazy initialization during marshaling!
			final Collection<Division> divisions = new TreeSet<>(competiton.getDivisions());

			final GenericEntity<?> genericEntity = new GenericEntity<Collection<Division>>(divisions) {};
			return Response.ok(genericEntity).build();
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
	}
	
	@GET
	@Path("{identity}/divisions/derived-games")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response queryDivisionsDerivedGames (@PathParam("identity") final long competitorIdentity) {
		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();

		try {

			Competition competiton = entityManager.find(Competition.class, competitorIdentity);
			if (competiton == null) return Response.status(Status.NOT_FOUND).build();
			
			// Sort by ascending identity, implicitly avoiding lazy initialization during marshaling!
			final Collection<Division> divisions = new TreeSet<>(competiton.getDivisions());
			final Collection<Game> derived_games = new TreeSet<Game>();
			
			for(Division division : divisions){
				derived_games.addAll(division.getDerivedGames());
			}
			
			for(Game game : derived_games){
				game.getDerivedGames();
			}
			
			final GenericEntity<?> genericEntity = new GenericEntity<Collection<Game>>(derived_games) {};
			return Response.ok(genericEntity).build();
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
	}
	
//	GET competitions/{identity}: Returns the competition matching the given identity. 
//	• GET competitions/{identity}/divisions: Returns the divisions of the
//	competition matching the given identity. 
//	• GET competitions/{identity}/divisions/derived-games: Returns the
//	union of all the games derived from the divisions of the competition matching the given
//	identity. 
//	• GET competitions/{identity}/logo
	
}
