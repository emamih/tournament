package de.htw.tournament.rest;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import de.sb.javase.TypeMetadata;


public interface PersistenceConsumer {
	static final EntityManagerFactory MESSENGER_ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("tournament");
}