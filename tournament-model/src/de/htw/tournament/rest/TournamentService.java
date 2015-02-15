package de.htw.tournament.rest;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.htw.tournament.model.Competition;
import de.htw.tournament.model.Division;
import de.htw.tournament.model.Document;
import de.htw.tournament.model.Game;
import de.htw.tournament.model.Tournament;

@Path("tournaments")
public class TournamentService {

//	static private final Charset UTF8 = Charset.forName("UTF-8");
	static private final String CRITERIA_QUERY_JPQL = "select p from Division as p where "
		+ "(:association is null or p.association = :association) and "
		+ "(:alias is null or p.alias = :alias) and "
		+ "(:identity is null or p.identity = :identity) and "
		+ "(:qualifier is null or p.qualifier = :qualifier)";
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Collection<Tournament> queryCriteria (
		@QueryParam("association") final boolean association,
		@QueryParam("alias") final boolean alias,
		@QueryParam("identity") final boolean identity,
		@QueryParam("qualifier") final boolean qualifier,
		@QueryParam("resultOffset") final int resultOffset,
		@QueryParam("resultLength") final int resultLength
	) {
		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();

		try {
			final TypedQuery<Tournament> query = entityManager.createQuery(CRITERIA_QUERY_JPQL, Tournament.class);
			if (resultOffset >= 0) query.setFirstResult(resultOffset);
			if (resultLength >= 0) query.setMaxResults(resultLength);
			query.setParameter("competition", association);
			query.setParameter("alias", alias);
			query.setParameter("identity", identity);
			query.setParameter("discriminator", qualifier);
			
			return query.getResultList();

		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
	}
	
	@GET
	@Path("{identity}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response queryIdentity (@PathParam("identity") final long tournamentIdentity) {
		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();

		try {
			final Tournament tournament = entityManager.find(Tournament.class, tournamentIdentity);
			return tournament == null
				? Response.status(Status.NOT_FOUND).build()
				: Response.ok(tournament).build();
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
	}
	
	@GET
	@Path("{identity}/logo")
	@Produces(MediaType.WILDCARD)
	public Response queryLogo (@PathParam("identity") final long tournamentIdentity) {
		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();

		try {
			final Tournament tournament = entityManager.find(Tournament.class, tournamentIdentity);
			if (tournament == null) return Response.status(Status.NOT_FOUND).build();

			final Document logo = tournament.getLogo();
			return Response.ok(logo.getBody(), logo.getType()).build();
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
	}
	
	
	@GET
	@Path("{identity}/competitions")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response queryCompetitions (@PathParam("identity") final long tournamentIdentity) {
		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();

		try {

			Tournament tournament = entityManager.find(Tournament.class, tournamentIdentity);
			if (tournament == null) return Response.status(Status.NOT_FOUND).build();
			
			// Sort by ascending identity, implicitly avoiding lazy initialization during marshaling!
			final Collection<Competition> competitions = new TreeSet<>(tournament.getCompetitions());

			final GenericEntity<?> genericEntity = new GenericEntity<Collection<Competition>>(competitions) {};
			return Response.ok(genericEntity).build();
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
	}
}
