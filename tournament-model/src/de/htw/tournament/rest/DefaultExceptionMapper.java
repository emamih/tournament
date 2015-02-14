package de.htw.tournament.rest;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


/**
 * JAX-RS Exception mapper that additionally logs any kinds of marshaling errors.
 */
@Provider // Exceptions loggen, die man sonst nicht sieht (Marshalling Fehler)
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {

	/**
	 * {@inheritDoc}
	 */
	public Response toResponse (final Throwable exception) {
		Logger.getGlobal().log(Level.WARNING, exception.getMessage(), exception);

		return Response.status(500).build();
	}
}