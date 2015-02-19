package de.htw.tournament.model;
// default package
// Generated 09.02.2015 16:09:37 by Hibernate Tools 4.3.1

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

//@XmlRootElement
//@Entity
//@Table(schema = "tournament", name = "divisionscoresheetentry")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("rawtypes")
public class ScoreSheetEntry implements java.io.Serializable, Comparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Competitor competitor;
	
	private Division root;
	
	private Integer score;
	
	private Integer opponentScore;
	
	private Double points;

	public ScoreSheetEntry() {
	}

	public ScoreSheetEntry(Competitor competitorReference) {
		this.competitor = competitorReference;
	}

	public ScoreSheetEntry(Competitor competitor,
			Division root, Integer score, Integer opponentScore,
			Double points) {
		this.competitor = competitor;
		this.root = root;
		this.score = score;
		this.opponentScore = opponentScore;
		this.points = points;
	}

	
	public Competitor getCompetitor() {
		return this.competitor;
	}

	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}

	
	public Division getRoot() {
		return this.root;
	}

	public void setRoot(Division root) {
		this.root = root;
	}

	
	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	
	public Integer getOpponentScore() {
		return this.opponentScore;
	}

	public void setOpponentScore(Integer opponentScore) {
		this.opponentScore = opponentScore;
	}

	
	public Double getPoints() {
		return this.points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ScoreSheetEntry))
			return false;
		ScoreSheetEntry castOther = (ScoreSheetEntry) other;

		return (this.getCompetitor() == castOther
				.getCompetitor())
				&& ((this.getRoot() == castOther.getRoot()) || (this
						.getRoot() != null
						&& castOther.getRoot() != null && this
						.getRoot()
						.equals(castOther.getRoot())))
				&& ((this.getScore() == castOther.getScore()) || (this
						.getScore() != null && castOther.getScore() != null && this
						.getScore().equals(castOther.getScore())))
				&& ((this.getOpponentScore() == castOther.getOpponentScore()) || (this
						.getOpponentScore() != null
						&& castOther.getOpponentScore() != null && this
						.getOpponentScore()
						.equals(castOther.getOpponentScore())))
				&& ((this.getPoints() == castOther.getPoints()) || (this
						.getPoints() != null && castOther.getPoints() != null && this
						.getPoints().equals(castOther.getPoints())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCompetitor().getIdentity().intValue();
		result = 37
				* result
				+ (getRoot() == null ? 0 : this.getRoot()
						.hashCode());
		result = 37 * result
				+ (getScore() == null ? 0 : this.getScore().hashCode());
		result = 37
				* result
				+ (getOpponentScore() == null ? 0 : this.getOpponentScore()
						.hashCode());
		result = 37 * result
				+ (getPoints() == null ? 0 : this.getPoints().hashCode());
		return result;
	}

	@Override
	public int compareTo(Object o) {

		if(o instanceof ScoreSheetEntry){
			ScoreSheetEntry sce =(ScoreSheetEntry) o;
			
			if(this.competitor.getIdentity()==sce.competitor.getIdentity()){
				return 0;
			}
			
			if(this.points>sce.getPoints()){
				return -1;
			} else if (this.points == sce.getPoints()) {
				int myDiff = (this.score-this.opponentScore);
				int otherDiff = (sce.getScore()-sce.getOpponentScore());
				if(myDiff>otherDiff){
					return -1;
				} else if(myDiff==otherDiff) {
					if(this.score>sce.getScore()){
						return -1;
					} else if(this.score==sce.getScore()) {
						if(this.competitor.getIdentity()>sce.competitor.getIdentity()){
							return -1;
						} else {
							return +1;
						}
					} else {
						return +1;
					}
				} else {
					return +1;
				}
			} else {
				return +1;
			}
		}
		
		return +1;
	}

}
