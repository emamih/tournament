package de.htw.tournament.model;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import de.sb.javase.TypeMetadata;
import de.sb.javase.util.MessageDigests;


/**
 * This facade encapsulates ticket value creation and conversion operations.
 */
@TypeMetadata(copyright = "2015-2015 Sascha Baumeister, all rights reserved", version = "1.0.0", authors = "Sascha Baumeister")
public final class TicketValues {
	static public final int TICKET_VALUE_LENGTH = 16;
	static private final Random RANDOMIZER = new SecureRandom();


	/**
	 * Prevents instantiation
	 */
	private TicketValues () {}


	/**
	 * Returns a new ticket value.
	 * @return the secure random 16-byte ticket value
	 */
	static public byte[] newTicketValue () {
		final byte[] value = new byte[TICKET_VALUE_LENGTH];
		RANDOMIZER.nextBytes(value);
		return value;
	}


	/**
	 * Returns the SHA-256 hash of the given ticket value.
	 * @param ticketValue the secure random 16-byte ticket value
	 * @return the SHA-256 hash of the given ticket value
	 * @throws NullPointerException if the given argument is {@code null}
	 * @throws IllegalArgumentException if the given argument is not 16 bytes long
	 */
	static public byte[] ticketValueToHash (final byte[] ticketValue) {
		if (ticketValue.length != 16) throw new IllegalArgumentException();

		return MessageDigests.newDigest("SHA-256").digest(ticketValue);
	}


	/**
	 * Returns the big-endian hexadecimal representation of the given ticket value.
	 * @param ticketValue the secure random 16-byte ticket value
	 * @return big-endian hexadecimal representation of the given ticket value
	 * @throws NullPointerException if the given argument is {@code null}
	 * @throws IllegalArgumentException if the given argument is not 16 bytes long
	 */
	static public String ticketValueToHex (byte[] ticketValue) {
		if (ticketValue.length != 16) throw new IllegalArgumentException();

		return new BigInteger(1, ticketValue).toString(16);
	}


	/**
	 * Returns the ticket value for the given big-endian hexadecimal representation.
	 * @param hex the big-endian hexadecimal representation
	 * @return the secure random 16-byte ticket value
	 * @throws NullPointerException if the given argument is {@code null}
	 * @throws IllegalArgumentException if the given argument contains illegal characters, or is too
	 *         long to represent a ticket value
	 */
	static public byte[] ticketHexToValue (String hex) {
		final byte[] bytes = new BigInteger(hex, 16).toByteArray();
		if (bytes.length == 16) return bytes;
		if (bytes.length > 16) throw new IllegalArgumentException();

		final byte[] ticketValue = new byte[16];
		System.arraycopy(bytes, 0, ticketValue, ticketValue.length - bytes.length, bytes.length);
		return ticketValue;
	}
}