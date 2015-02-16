package de.htw.tournament.model;
// default package
// Generated 09.02.2015 16:09:37 by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
@Entity
@Table(schema = "tournament")
@PrimaryKeyJoinColumn(name = "divisionIdentity")
public class Division extends Rankableentity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "competitionReference", nullable = false)
	private Competition competition;
	
	@Column(name = "alias", nullable = false, length = 1)
	private char alias;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "division")
	private Set<Game> games = new HashSet<Game>(0);

	public Division() {
	}

	public Division(Competition competition, Rankableentity rankableentity,
			char alias) {
		this.competition = competition;
		this.alias = alias;
	}

	public Division(Competition competition, Rankableentity rankableentity,
			char alias, Set<Game> games) {
		this.competition = competition;
		this.alias = alias;
		this.games = games;
	}

	
	public Competition getCompetition() {
		return this.competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	
	public char getAlias() {
		return this.alias;
	}

	public void setAlias(char alias) {
		this.alias = alias;
	}

	
	public Set<Game> getGames() {
		return this.games;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
	}
	
	
	public Set<Game> getRootGames() {
		return this.games;
	}
	
	public Set<Competitor> getCompetitors(){
		TreeSet<Competitor> competitors = new TreeSet<Competitor>();
		for(Game game : this.games){
			competitors.add(game.getLeftCompetitor());
			competitors.add(game.getRightCompetitor());
		}
		return competitors;
	}

	@Override
	public ScoreSheetEntry getScoreSheet() {
		// TODO Auto-generated method stub
		return null;
	}

}
