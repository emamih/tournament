package de.htw.tournament.rest;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.htw.tournament.model.Competitor;
import de.htw.tournament.model.Division;
import de.htw.tournament.model.Game;
import de.htw.tournament.model.ScoreSheetEntry;
import de.htw.tournament.model.Ticket;
import de.sb.javase.io.HttpAuthenticationCodec;

@Path("games")
public class GameService {

	static private String AUTHENTICATED_AND_AUTORIZED = "thou mayest pass! (I authenticated you as %s, and as such you're also authorized to proceed)";
	static private String AUTHENTICATED_BUT_NOT_AUTORIZED = "thou shalt not pass! (I authenticated you as %s, but you are not authorized to proceed)";
	
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

//	@POST
//	@Path("{identity}")
//	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
//	@Produces(MediaType.TEXT_PLAIN)
//	public Response modifyScore (@PathParam("identity") final long gameIdentity, @FormParam("leftScore") final short leftScore, @FormParam("rightScore") final short rightScore) {
//		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();
//
//		entityManager.getTransaction().begin();
//		try {
//			final Game game = entityManager.getReference(Game.class, gameIdentity);
//
//			game.setLeftScore(leftScore);
//			game.setRightScore(rightScore);
//
//			entityManager.getTransaction().commit();
//			entityManager.persist(game);
//			return Response.ok(game.getIdentity(), MediaType.TEXT_PLAIN_TYPE).build();
//		} catch (final NullPointerException | IllegalArgumentException exception) {
//			return Response.status(Status.BAD_REQUEST).build();
//		} finally {
//			try { entityManager.getTransaction().rollback(); } catch (final Exception exception) {}
//			try { entityManager.close(); } catch (final Exception exception) {}
//		}
//	}




	@POST
	@Path("{identity}")
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	@Produces(MediaType.TEXT_PLAIN)
	public Response modifyScoreAuthenticated (@Context final HttpHeaders headers, @PathParam("identity") final long gameIdentity, @FormParam("leftScore") final short leftScore, @FormParam("rightScore") final short rightScore) {
		EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();
		final Map<String,String> credentials;
		try {
			final List<String> headerValues = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
			if (headerValues == null) return Response.status(Status.UNAUTHORIZED).header("WWW-Authenticate", "Basic").build();
			if (headerValues.size() != 1) throw new IllegalArgumentException();
			credentials = HttpAuthenticationCodec.decode(headerValues.get(0));
		} catch (final IllegalArgumentException exception) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		// Perform authentication
		final String userAlias = credentials.get("username");
		final String userPassword = credentials.get("password");

		MessageDigest shaCreator = null;
		byte[] sha256 = null;
		
		//Initialize hash generator
		
		try {
			shaCreator = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		try {
			sha256 = shaCreator.digest(userPassword.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		final String CRITERIA_QUERY_JPQL = "select t from Ticket t where (t.invalidationTimestamp is null or ((:invalid - t.invalidationTimestamp)/1000) < (60*5)) and t.valueHash=:hash";
		Ticket ticket = null;
		
		try {

			final TypedQuery<Ticket> query = entityManager.createQuery(CRITERIA_QUERY_JPQL, Ticket.class);
			query.setParameter("invalid", System.currentTimeMillis());
			query.setParameter("hash", sha256);

			try{
				ticket = (Ticket) query.getSingleResult();
			}catch(NoResultException e){
				
			}

		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
		
		if (ticket==null) {
			// simulate failed user lookup
			return Response.status(Status.UNAUTHORIZED).header("WWW-Authenticate", "Basic").build();
		} else {
			entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();
			entityManager.getTransaction().begin();
			try {
				final Game game = entityManager.getReference(Game.class, gameIdentity);

				game.setLeftScore(leftScore);
				game.setRightScore(rightScore);

				entityManager.getTransaction().commit();
				entityManager.persist(game);
				return Response.ok(game.getIdentity(), MediaType.TEXT_PLAIN_TYPE).build();
			} catch (final NullPointerException | IllegalArgumentException exception) {
				return Response.status(Status.BAD_REQUEST).build();
			} finally {
				try { entityManager.getTransaction().rollback(); } catch (final Exception exception) {}
				try { entityManager.close(); } catch (final Exception exception) {}
			}
		}

		
	}


}
