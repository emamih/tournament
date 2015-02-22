package de.htw.tournament.rest;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public interface PersistenceConsumer {
	static final EntityManagerFactory MESSENGER_ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("tournament");
}