package de.htw.tournament.model;
// default package
// Generated 09.02.2015 16:09:37 by Hibernate Tools 4.3.1

import java.math.BigDecimal;

/**
 * DivisionscoresheetentryId generated by hbm2java
 */
//@XmlRootElement
//@Entity
//@Table(schema = "tournament", name = "divisionscoresheetentry")
public class ScoreSheetEntry implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long competitorReference;
	
	private Long rootReference;
	
	private BigDecimal score;
	
	private BigDecimal opponentScore;
	
	private Double points;

	public ScoreSheetEntry() {
	}

	public ScoreSheetEntry(long competitorReference) {
		this.competitorReference = competitorReference;
	}

	public ScoreSheetEntry(long competitorReference,
			Long rootReference, BigDecimal score, BigDecimal opponentScore,
			Double points) {
		this.competitorReference = competitorReference;
		this.rootReference = rootReference;
		this.score = score;
		this.opponentScore = opponentScore;
		this.points = points;
	}

	
	public long getCompetitorReference() {
		return this.competitorReference;
	}

	public void setCompetitorReference(long competitorReference) {
		this.competitorReference = competitorReference;
	}

	
	public Long getRootReference() {
		return this.rootReference;
	}

	public void setRootReference(Long rootReference) {
		this.rootReference = rootReference;
	}

	
	public BigDecimal getScore() {
		return this.score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	
	public BigDecimal getOpponentScore() {
		return this.opponentScore;
	}

	public void setOpponentScore(BigDecimal opponentScore) {
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

		return (this.getCompetitorReference() == castOther
				.getCompetitorReference())
				&& ((this.getRootReference() == castOther.getRootReference()) || (this
						.getRootReference() != null
						&& castOther.getRootReference() != null && this
						.getRootReference()
						.equals(castOther.getRootReference())))
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

		result = 37 * result + (int) this.getCompetitorReference();
		result = 37
				* result
				+ (getRootReference() == null ? 0 : this.getRootReference()
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

}
