package de.htw.tournament.rest;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.htw.tournament.model.Game;

@Path("games")
public class GameService {
	
	@GET
	@Path("{identity}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response queryIdentity (@PathParam("identity") final long gameIdentity) {
		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();

		try {
			final Game game = entityManager.find(Game.class, gameIdentity);
			return game == null
				? Response.status(Status.NOT_FOUND).build()
				: Response.ok(game).build();
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
	}
	
	
	@GET
	@Path("{identity}/derived-games")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response queryDerivedGames (@PathParam("identity") final long gameIdentity) {
		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();

		try {

			Game game = entityManager.find(Game.class, gameIdentity);
			if (game == null) return Response.status(Status.NOT_FOUND).build();
			
			// Sort by ascending identity, implicitly avoiding lazy initialization during marshaling!
			final Collection<Game> games = new HashSet<>(game.getDerivedGames());
			for(Game temp : games){
				temp.getLeftCompetitor();
				temp.getRightCompetitor();
			}
			final GenericEntity<?> genericEntity = new GenericEntity<Collection<Game>>(games) {};
			return Response.ok(genericEntity).build();
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
	}
	
	@POST
	@Path("{identity}")
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	@Produces(MediaType.TEXT_PLAIN)
	public Response modifyBid (@PathParam("identity") final long gameIdentity, @FormParam("leftScore") final short leftScore, @FormParam("leftScore") final short rightScore) {
		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();

		entityManager.getTransaction().begin();
		try {
			final Game game = entityManager.getReference(Game.class, gameIdentity);

			game.setLeftScore(leftScore);
			game.setRightScore(rightScore);

			entityManager.getTransaction().commit();
			return Response.ok(game.getIdentity(), MediaType.TEXT_PLAIN_TYPE).build();
		} catch (final NullPointerException | IllegalArgumentException exception) {
			return Response.status(Status.BAD_REQUEST).build();
		} finally {
			try { entityManager.getTransaction().rollback(); } catch (final Exception exception) {}
			try { entityManager.close(); } catch (final Exception exception) {}
		}
	}
}
