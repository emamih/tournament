package de.htw.tournament;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.jettison.JettisonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.sun.net.httpserver.HttpServer;

import de.htw.tournament.rest.PersistenceConsumer;
import de.sb.javase.io.HttpModuleHandler;


public class ApplicationContainer {

	/**
	 * Application entry point. The given argument is expected to be a service port.
	 * @param args the runtime arguments
	 * @throws NumberFormatException if the given port is not a number
	 * @throws IOException if there is an I/O related problem
	 */
	static public void main (final String[] args) throws NumberFormatException, IOException {
		final int servicePort = args.length == 0 ? 80 : Integer.parseInt(args[0]);
		final URI serviceURI;
		try {
			serviceURI = new URI("http", null, "127.0.0.1", servicePort, "/services", null, null);
		} catch (final URISyntaxException exception) {
			throw new AssertionError();
		}

		// Note that server-startup is only required in Java-SE, as any Java-EE engine must ship a built-in HTTP server
		// implementation and XML-based configuration. The Factory-Class used is Jersey-specific, while the HTTP server
		// type used is Oracle/OpenJDK-specific. Other HTTP server types more suitable for production environments are
		// available, such as Apache Tomcat, Grizzly, Simple, etc.
		final ResourceConfig configuration = new ResourceConfig();
		configuration.packages(PersistenceConsumer.class.getPackage().toString());
		configuration.register(JettisonFeature.class);

		final HttpServer server = JdkHttpServerFactory.createHttpServer(serviceURI, configuration);
		final HttpModuleHandler resourceHandler = new HttpModuleHandler("/resources");
		server.createContext(resourceHandler.getContextPath(), resourceHandler);
		try {
			System.out.format("HTTP server running on service address %s:%s, enter \"quit\" to stop.\n", serviceURI.getHost(), serviceURI.getPort());
			System.out.format("Service path \"%s\" is configured for REST service access.\n", serviceURI.getPath());
			System.out.format("Service path \"%s\" is configured for JAR resource access.\n", resourceHandler.getContextPath());
			final BufferedReader charSource = new BufferedReader(new InputStreamReader(System.in));
			while (!"quit".equals(charSource.readLine()));
		} finally {
			server.stop(0);
		}
	}
}