package de.htw.tournament;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import de.htw.tournament.model.Ticket;
import de.htw.tournament.rest.ServiceProvider;

public class TicketGenrator {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		//Entitymanger erstellen über EntityMangerFactory
		EntityManager manager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();
		try {
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
				String passwordClearText = "password" +r; 
				MessageDigest shaCreator = null;
				byte[] sha256 = null;
				
				//Initialize hash generator
				
				try {
					shaCreator = MessageDigest.getInstance("SHA-256");
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//Create hash from cleartext password
				
				try {
					sha256 = shaCreator.digest(passwordClearText.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//Set hash to ticket
				
				t.setValueHash(sha256);
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			manager.close();
		}

	}

}