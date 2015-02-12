package de.htw.tournament.model;
// default package
// Generated 09.02.2015 16:09:37 by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 */
@Entity
@Table(name = "rankableentity", catalog = "tournament")
//TODO �nderung in db �bernehemen
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Rankableentity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "identity", unique = true, nullable = false)
	private Long identity;
	
	@Column(name = "discriminator", nullable = false, length = 11)
	private String discriminator;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rankableentityByRightRankableEntityReference")
	private Set<Game> gamesForRightRankableEntityReference = new HashSet<Game>(
			0);
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "rankableentity")
	private Division division;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rankableentityByLeftRankableEntityReference")
	private Set<Game> gamesForLeftRankableEntityReference = new HashSet<Game>(0);
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "rankableentity")
	private Competitor competitor;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "rankableentityByIdentity")
	private Game gameByIdentity;

	
	public Rankableentity() {
	}

	public Rankableentity(String discriminator) {
		this.discriminator = discriminator;
	}

	public Rankableentity(String discriminator,
			Set<Game> gamesForRightRankableEntityReference, Division division,
			Set<Game> gamesForLeftRankableEntityReference,
			Competitor competitor, Game gameByIdentity) {
		this.discriminator = discriminator;
		this.gamesForRightRankableEntityReference = gamesForRightRankableEntityReference;
		this.division = division;
		this.gamesForLeftRankableEntityReference = gamesForLeftRankableEntityReference;
		this.competitor = competitor;
		this.gameByIdentity = gameByIdentity;
	}

	public abstract ScoreSheetEntry getScoreSheet();
	
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

	
	public Set<Game> getGamesForRightRankableEntityReference() {
		return this.gamesForRightRankableEntityReference;
	}

	public void setGamesForRightRankableEntityReference(
			Set<Game> gamesForRightRankableEntityReference) {
		this.gamesForRightRankableEntityReference = gamesForRightRankableEntityReference;
	}

	
	public Division getDivision() {
		return this.division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	
	public Set<Game> getGamesForLeftRankableEntityReference() {
		return this.gamesForLeftRankableEntityReference;
	}

	public void setGamesForLeftRankableEntityReference(
			Set<Game> gamesForLeftRankableEntityReference) {
		this.gamesForLeftRankableEntityReference = gamesForLeftRankableEntityReference;
	}

	
	public Competitor getCompetitor() {
		return this.competitor;
	}

	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}

	
	public Game getGameByIdentity() {
		return this.gameByIdentity;
	}

	public void setGameByIdentity(Game gameByIdentity) {
		this.gameByIdentity = gameByIdentity;
	}

}