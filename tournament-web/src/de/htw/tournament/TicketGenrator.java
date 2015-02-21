package de.htw.tournament;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.htw.tournament.model.Ticket;

public class TicketGenrator {

	public static void main(String[] args) {
		//Entitymanger erstellen über EntityMangerFactory
		EntityManager manager = Persistence.createEntityManagerFactory("tournament").createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		Query q = manager.createNamedQuery("invalidTickets");
		q.setParameter("invalid", System.currentTimeMillis());
		List<Ticket> tickets = q.getResultList();
		for(Ticket t : tickets){
			manager.remove(t);
		}
		transaction.commit();
		System.out.println("Tickets\n");
		for (int r = 0; r <= 24; r++) {
			Ticket t = new Ticket();
			//Todo: Passwort generieren und sha256 draus basteln
			t.setValueHash(("password"+r).getBytes());
			t.setInvalidationTimestamp(System.currentTimeMillis()+300);
			
			transaction = manager.getTransaction();
			transaction.begin();
			
			try{
			manager.persist(t);
			}catch(Throwable throwable){
				transaction.rollback();
				System.out.println("error bla doof");
				continue;//transaction allready closed
			}
			transaction.commit();
			//Todo: passwort ausgeben
			System.out.println("password"+r);
			
		}
		transaction.commit();
		
		manager.close();




	}

}