package de.htw.tournament.rest;

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
import de.htw.tournament.model.Game;


@Path("divisions")
public class DivisionService {
	
	

//	static private final Charset UTF8 = Charset.forName("UTF-8");
//	static private final String CRITERIA_QUERY_JPQL = "select p from Division as p where "
//		+ "(:competition is null or p.competition = :competition) and "
//		+ "(:alias is null or p.alias = :alias) and "
//		+ "(:identity is null or p.identity = :identity) and "
//		+ "(:discriminator is null or p.discriminator = :discriminator)";
//	
//	@GET
//	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
//	public Collection<Division> queryCriteria (
//		@QueryParam("competition") final boolean competition,
//		@QueryParam("alias") final boolean alias,
//		@QueryParam("identity") final boolean identity,
//		@QueryParam("discriminator") final boolean discriminator,
//		@QueryParam("resultOffset") final int resultOffset,
//		@QueryParam("resultLength") final int resultLength
//	) {
//		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();
//
//		try {
//			final TypedQuery<Division> query = entityManager.createQuery(CRITERIA_QUERY_JPQL, Division.class);
//			if (resultOffset >= 0) query.setFirstResult(resultOffset);
//			if (resultLength >= 0) query.setMaxResults(resultLength);
//			query.setParameter("competition", competition);
//			query.setParameter("alias", alias);
//			query.setParameter("identity", identity);
//			query.setParameter("discriminator", discriminator);
//			
//			return query.getResultList();
//
//		} finally {
//			try { entityManager.close(); } catch (final Exception exception) {}
//		}
//	}
	
	@GET
	@Path("{identity}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response queryIdentity (@PathParam("identity") final long divisionIdentity) {
		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();

		try {
			final Division division = entityManager.find(Division.class, divisionIdentity);
			return division == null
				? Response.status(Status.NOT_FOUND).build()
				: Response.ok(division).build();
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
	}
	
	//TODO: Games was sagt aus, dass ein Spiel ein Root Game oder ko runde game ist ?
	
	@GET
	@Path("{identity}/root-games")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response queryRootGames (@PathParam("identity") final long divisionIdentity) {
		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();

		try {

			Division division = entityManager.find(Division.class, divisionIdentity);
			if (division == null) return Response.status(Status.NOT_FOUND).build();
			
			// Sort by ascending identity, implicitly avoiding lazy initialization during marshaling!
			final Collection<Game> games = new TreeSet<>(division.getRootGames());
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
	
	@GET
	@Path("{identity}/derived-games")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response queryDerivedGames (@PathParam("identity") final long divisionIdentity) {
		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();

		try {

			Division division = entityManager.find(Division.class, divisionIdentity);
			if (division == null) return Response.status(Status.NOT_FOUND).build();
			
			// Sort by ascending identity, implicitly avoiding lazy initialization during marshaling!
			final Collection<Game> games = new TreeSet<>(division.getGames());
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
	
	@GET
	@Path("{identity}/score-sheet")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response queryScoreSheet (@PathParam("identity") final long divisionIdentity) {
		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();

		try {

			Division division = entityManager.find(Division.class, divisionIdentity);
			if (division == null) return Response.status(Status.NOT_FOUND).build();
			
			// Sort by ascending identity, implicitly avoiding lazy initialization during marshaling!
			final Collection<Game> games = new TreeSet<>(division.getRootGames());
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

}
