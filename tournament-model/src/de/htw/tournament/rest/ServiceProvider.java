package de.htw.tournament.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import de.sb.javase.TypeMetadata;

/**
 * JAX-RS service provider for exception mapping and HTTP Basic authentication.
 */
@Provider
@TypeMetadata(copyright = "2013-2015 Sascha Baumeister, all rights reserved", version = "1.0.0", authors = "Sascha Baumeister")
public class ServiceProvider implements ExceptionMapper<Throwable> {
	static public final EntityManagerFactory TOURNAMENT_FACTORY = Persistence.createEntityManagerFactory("broker");


	/**
	 * Maps the given exception to a HTTP response. In case of a WebApplicationException instance,
	 * it's associated response is returned. Otherwise, a generic HTTP 500 response is returned. In
	 * all cases the exception is logged if it indicates a server side problem.
	 * @param exception the exception to be mapped
	 * @return the mapped response
	 */
	public Response toResponse (final Throwable exception) {
		final Response response = exception instanceof WebApplicationException
			? ((WebApplicationException) exception).getResponse()
			: Response.status(Status.INTERNAL_SERVER_ERROR).build();

		if (response.getStatus() >= 500) {
			Logger.getGlobal().log(Level.WARNING, exception.getMessage(), exception);
		}

		return response;
	}

}