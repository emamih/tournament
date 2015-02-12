package de.htw.tournament.model;

import java.util.Collections;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import de.sb.javase.TypeMetadata;



/**
 * This class models person entities.
 */
@XmlRootElement
@Entity
@Table(schema = "tournament", indexes=@Index(columnList = "logoReference"))
@PrimaryKeyJoinColumn(name = "identity")
@TypeMetadata(copyright = "2013-2014 Sascha Baumeister, all rights reserved", version = "1.0.0", authors = "Sascha Baumeister")
public class Tournament implements Comparable {

	@XmlAttribute
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private final long identity;
	
	@XmlAttribute
	@Column(nullable = false, updatable = false, length = 15, unique = true)
	private volatile String association;
	
	@XmlAttribute
	@Column(nullable = false, updatable = false, length = 15, unique = true)
	private volatile String alias;
	
	@XmlAttribute
	@Column(nullable = true, updatable = false, length = 15, unique = true)
	private volatile String qualifier;

	@XmlAttribute(name = "logoReference")
	@XmlJavaTypeAdapter(EntityIdentityAdapter.class)
	@ManyToOne
	@JoinColumn(name = "logoReference", nullable = true, updatable = true)
	private volatile Document logo;

	@XmlTransient
	@OneToMany(mappedBy = "tournamentReference", cascade = CascadeType.REMOVE)
	private final Set<Competition> competitions;


	/**
	 * Protected default constructor for JPA and JAX-B engines.
	 */
	protected Tournament () {
		super();

		this.identity = 0;
		this.competitions = Collections.emptySet();
	}


	/**
	 * Creates a new instance with a pseudo-random password.
	 * @param alias the alias
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	public Tournament (final String alias, final String association, final String qualifier) {
		super();
		if (alias == null) throw new NullPointerException();

		
		this.identity = 0;
		this.alias = alias;
		this.association = association;
		this.qualifier = qualifier;
		
		this.logo = null;
		this.competitions = Collections.emptySet();
	}


	public String getAssociation() {
		return association;
	}


	public void setAssociation(String association) {
		this.association = association;
	}


	public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		this.alias = alias;
	}


	public String getQualifier() {
		return qualifier;
	}


	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}


	public Document getLogo() {
		return logo;
	}


	public void setLogo(Document logo) {
		if (logo != null && !logo.getType().startsWith("image/")) throw new IllegalArgumentException();
		
		this.logo = logo;
	}


	public long getIdentity() {
		return identity;
	}


	public Set<Competition> getCompetitions() {
		return competitions;
	}


	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}