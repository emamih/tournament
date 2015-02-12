package de.htw.tournament.rest;

import javax.ws.rs.Path;

import de.sb.javase.TypeMetadata;


/**
 * JAX-RS based REST service implementation for polymorphic entity resources. The following path and
 * method combinations are supported:
 * <ul>
 * <li>DELETE entities/{identity}: Deletes the entity matching the given identity.</li>
 * </ul>
 */
@Path("entities")
@TypeMetadata(copyright = "2013-2015 Sascha Baumeister, all rights reserved", version = "1.0.0", authors = "Sascha Baumeister")
public class EntityService {

	
}