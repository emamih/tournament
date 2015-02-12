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

import de.htw.tournament.model.Competitor;
import de.htw.tournament.model.Division;
import de.htw.tournament.model.Document;
import de.htw.tournament.model.Game;

@Path("competitors")
public class CompetitorService {

	static private final Charset UTF8 = Charset.forName("UTF-8");
	
	@GET
	@Path("{identity}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response queryIdentity (@PathParam("identity") final long competitorIdentity) {
		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();

		try {
			final Competitor competitor = entityManager.find(Competitor.class, competitorIdentity);
			return competitor == null
				? Response.status(Status.NOT_FOUND).build()
				: Response.ok(competitor).build();
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
			final Competitor competitor = entityManager.find(Competitor.class, competitorIdentity);
			if (competitor == null) return Response.status(Status.NOT_FOUND).build();

			final Document avatar = competitor.getDocument();
			return Response.ok(avatar.getBody(), avatar.getType()).build();
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
	}
}
