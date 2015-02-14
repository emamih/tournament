package de.htw.tournament.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;



@XmlRootElement
@Entity
@Table(schema = "tournament")
@PrimaryKeyJoinColumn(name = "identity")
@SuppressWarnings("rawtypes")
public class Document implements Comparable{
	static private final byte[] DEFAULT_CONTENT = new byte[0];
	static private final byte[] DEFAULT_CONTENT_HASH = { -29, -80, -60, 66, -104, -4, 28, 20, -102, -5, -12, -56, -103, 111, -71, 36, 39, -82, 65, -28, 100, -101, -109, 76, -92, -107, -103, 27, 120, 82, -72, 85 };
	static private final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

	@XmlAttribute
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private final long identity;
	
	@XmlAttribute
	@Column(nullable = false, updatable = true, length = 64)
	private volatile String type;

	@XmlAttribute
	@Column(nullable = false, updatable = true, length = 32, unique = true)
	private volatile byte[] hash;

	@XmlTransient
	@Lob  // Large Object 
	@Basic(fetch = FetchType.LAZY)
	@Column(nullable = false, updatable = true, length = 16777215)
	private volatile byte[] body;


	/**
	 * Returns the SHA256 hash of the given content.
	 * @param content the content
	 * @return the corresponding SHA256 hash
	 * @throws NullPointerException if the given content is {@code null}
	 */
	static public byte[] sha256Hash (final byte[] content) {
		try {
			return MessageDigest.getInstance("SHA-256").digest(content);
		} catch (final NoSuchAlgorithmException exception) {
			throw new AssertionError();
		}
	}

	/**
	 * Creates a new instance.
	 */
	public Document () {
		super();

		this.identity = 0;
		this.type = DEFAULT_CONTENT_TYPE;
		this.hash = DEFAULT_CONTENT_HASH;
		this.body = DEFAULT_CONTENT;
	}


	/**
	 * Returns the content type.
	 * @return the content type
	 */
	public String getType () {
		return this.type;
	}


	/**
	 * Sets the content type.
	 * @param contentType the content type
	 * @throws NullPointerException if the given content type is {@code null}
	 */
	public void setType (final String contentType) {
		if (contentType == null) throw new NullPointerException();

		this.type = contentType;
	}


	/**
	 * Returns the content hash.
	 * @return the content' SHA-256 hash
	 */
	public byte[] getHash () {
		return this.hash;
	}


	/**
	 * Returns the content.
	 * @return the content
	 */
	public byte[] getBody () {
		return this.body;
	}


	/**
	 * Sets the content, and recalculates the associated content hash.
	 * @param content the content
	 * @throws NullPointerException if the given content is {@code null}
	 */
	public void setBody (final byte[] content) {
		this.hash = sha256Hash(content);
		this.body = content;
	}

	public long getIdentity() {
		return identity;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}