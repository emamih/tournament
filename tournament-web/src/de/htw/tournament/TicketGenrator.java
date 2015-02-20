package de.htw.tournament;

import java.util.*;

public class TicketGenrator {

	public static void main(String[] args) {
		

		System.out.println("Tickets\n");
		for (int r = 0; r <= 24; r++) {

			final int STRING_LENGTH = 16;
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < STRING_LENGTH; i++) {
				sb.append((char) ((int) (Math.random() * 26) + 97));
			}
			System.out.println(sb.toString());
		}




	}

}
