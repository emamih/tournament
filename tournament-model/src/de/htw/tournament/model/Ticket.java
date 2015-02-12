package de.htw.tournament.model;
// default package
// Generated 09.02.2015 16:09:37 by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 */
@Entity
@Table(name = "ticket", catalog = "tournament", uniqueConstraints = @UniqueConstraint(columnNames = "valueHash"))
public class Ticket<T> implements Comparable<T>, java.io.Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "identity", unique = true, nullable = false)
	private Long identity;
	
	@Column(name = "valueHash", unique = true, nullable = false)
	private byte[] valueHash;
	
	@Column(name = "invalidationTimestamp")
	private Long invalidationTimestamp;

	public Ticket() {
	}

	public Ticket(byte[] valueHash) {
		this.valueHash = valueHash;
	}

	public Ticket(byte[] valueHash, Long invalidationTimestamp) {
		this.valueHash = valueHash;
		this.invalidationTimestamp = invalidationTimestamp;
	}

	
	public Long getIdentity() {
		return this.identity;
	}

	public void setIdentity(Long identity) {
		this.identity = identity;
	}

	
	public byte[] getValueHash() {
		return this.valueHash;
	}

	public void setValueHash(byte[] valueHash) {
		this.valueHash = valueHash;
	}

	
	public Long getInvalidationTimestamp() {
		return this.invalidationTimestamp;
	}

	public void setInvalidationTimestamp(Long invalidationTimestamp) {
		this.invalidationTimestamp = invalidationTimestamp;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
