package de.htw.tournament.model;
// default package
// Generated 09.02.2015 16:09:37 by Hibernate Tools 4.3.1

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD) 
@Entity
@Table(schema = "tournament")
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING, length = 11)
//TODO änderung in db übernehemen
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Rankableentity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// TODO: Ist das i.O. dass hier alle abgeleiteten Klassen von Rankable Entity verfügbar sind ?
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "identity", unique = true, nullable = false)
	private Long identity;
	
	@XmlElement
	@Column(name = "discriminator", nullable = false, length = 11)
	private String discriminator;
	
	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rightRankableEntity")
	private Set<Game> rightDerivedGames = new HashSet<Game>(0);
	
//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "rankableentity")
//	private Division division;
	
	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "leftRankableEntity")
	private Set<Game> leftDerivedGames = new HashSet<Game>(0);
	
//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "rankableentity")
//	private Competitor competitor;
	
//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "rankableentityByIdentity")
//	private Game gameByIdentity;

	
	public Rankableentity() {
	}

	public Rankableentity(String discriminator) {
		this.discriminator = discriminator;
	}

	public abstract Collection<ScoreSheetEntry> getScoreSheet();
	
	public Long getIdentity() {
		return this.identity;
	}

	public void setIdentity(Long identity) {
		this.identity = identity;
	}

	
	public String getDiscriminator() {
		return this.discriminator;
	}

	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}

	public Set<Game> getDerivedGames() {
		HashSet<Game> temp = new HashSet<Game>();
		temp.addAll(this.leftDerivedGames);
		temp.addAll(this.rightDerivedGames);
		return temp;
	}
	
	public boolean entityEquals(Rankableentity other){
		if(other==null) return false;
		return other.getIdentity()==this.getIdentity();
	}

	public Set<Game> getRightDerivedGames() {
		return rightDerivedGames;
	}

	public void setRightDerivedGames(Set<Game> rightDerivedGames) {
		this.rightDerivedGames = rightDerivedGames;
	}

	public Set<Game> getLeftDerivedGames() {
		return leftDerivedGames;
	}

	public void setLeftDerivedGames(Set<Game> leftDerivedGames) {
		this.leftDerivedGames = leftDerivedGames;
	}
	
	
	
	
//	public Set<Game> getGamesForRightRankableEntityReference() {
//		return this.gamesForRightRankableEntityReference;
//	}
//
//	public void setGamesForRightRankableEntityReference(
//			Set<Game> gamesForRightRankableEntityReference) {
//		this.gamesForRightRankableEntityReference = gamesForRightRankableEntityReference;
//	}
//
//	
//	public Division getDivision() {
//		return this.division;
//	}
//
//	public void setDivision(Division division) {
//		this.division = division;
//	}
//
//	
//	public Set<Game> getGamesForLeftRankableEntityReference() {
//		return this.gamesForLeftRankableEntityReference;
//	}
//
//	public void setGamesForLeftRankableEntityReference(
//			Set<Game> gamesForLeftRankableEntityReference) {
//		this.gamesForLeftRankableEntityReference = gamesForLeftRankableEntityReference;
//	}
//
//	
//	public Competitor getCompetitor() {
//		return this.competitor;
//	}
//
//	public void setCompetitor(Competitor competitor) {
//		this.competitor = competitor;
//	}
//
//	
//	public Game getGameByIdentity() {
//		return this.gameByIdentity;
//	}
//
//	public void setGameByIdentity(Game gameByIdentity) {
//		this.gameByIdentity = gameByIdentity;
//	}

}
