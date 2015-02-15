package de.htw.tournament.model;
// default package
// Generated 09.02.2015 16:09:37 by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
  */
@XmlRootElement
@Entity
@Table(schema = "tournament")
@PrimaryKeyJoinColumn(name = "gameIdentity")
//TODO änderung in db übernehemen
public class Game  extends Rankableentity implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rootReference")
	private Division division;
	
	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rightRankableEntityReference", nullable = false)
	private Rankableentity rankableentityByRightRankableEntityReference;
	
	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "leftRankableEntityReference", nullable = false)
	private Rankableentity rankableentityByLeftRankableEntityReference;
	
	@Column(name = "leftOrdinal", nullable = false)
	private Short leftOrdinal;
	
	@Column(name = "leftScore")
	private Short leftScore;
	
	@Column(name = "rightOrdinal", nullable = false)
	private Short rightOrdinal;
	
	@Column(name = "rightScore")
	private Short rightScore;

	public Game() {
	}

	public Game(Rankableentity rankableentityByRightRankableEntityReference,
			Rankableentity rankableentityByLeftRankableEntityReference,
			Rankableentity rankableentityByIdentity, Short leftOrdinal,
			Short rightOrdinal) {
		this.rankableentityByRightRankableEntityReference = rankableentityByRightRankableEntityReference;
		this.rankableentityByLeftRankableEntityReference = rankableentityByLeftRankableEntityReference;
		this.leftOrdinal = leftOrdinal;
		this.rightOrdinal = rightOrdinal;
	}

	public Game(Division division,
			Rankableentity rankableentityByRightRankableEntityReference,
			Rankableentity rankableentityByLeftRankableEntityReference,
			Rankableentity rankableentityByIdentity, Short leftOrdinal,
			Short leftScore, Short rightOrdinal, Short rightScore) {
		this.division = division;
		this.rankableentityByRightRankableEntityReference = rankableentityByRightRankableEntityReference;
		this.rankableentityByLeftRankableEntityReference = rankableentityByLeftRankableEntityReference;
		this.leftOrdinal = leftOrdinal;
		this.leftScore = leftScore;
		this.rightOrdinal = rightOrdinal;
		this.rightScore = rightScore;
	}

	
	public Division getDivision() {
		return this.division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	
	public Rankableentity getRankableentityByRightRankableEntityReference() {
		return this.rankableentityByRightRankableEntityReference;
	}

	public void setRankableentityByRightRankableEntityReference(
			Rankableentity rankableentityByRightRankableEntityReference) {
		this.rankableentityByRightRankableEntityReference = rankableentityByRightRankableEntityReference;
	}

	
	public Rankableentity getRankableentityByLeftRankableEntityReference() {
		return this.rankableentityByLeftRankableEntityReference;
	}

	public void setRankableentityByLeftRankableEntityReference(
			Rankableentity rankableentityByLeftRankableEntityReference) {
		this.rankableentityByLeftRankableEntityReference = rankableentityByLeftRankableEntityReference;
	}

	
	public Short getLeftOrdinal() {
		return this.leftOrdinal;
	}

	public void setLeftOrdinal(Short leftOrdinal) {
		this.leftOrdinal = leftOrdinal;
	}

	@XmlElement
	public Short getLeftScore() {
		return this.leftScore;
	}

	public void setLeftScore(Short leftScore) {
		this.leftScore = leftScore;
	}

	
	public Short getRightOrdinal() {
		return this.rightOrdinal;
	}

	public void setRightOrdinal(Short rightOrdinal) {
		this.rightOrdinal = rightOrdinal;
	}

	@XmlElement
	public Short getRightScore() {
		return this.rightScore;
	}

	public void setRightScore(Short rightScore) {
		this.rightScore = rightScore;
	}
	
	@XmlElement
	public Competitor getLeftCompetitor() {
		return (rankableentityByLeftRankableEntityReference instanceof Competitor) 
				? (Competitor) rankableentityByLeftRankableEntityReference : null;
	}
	@XmlElement
	public Competitor getRightCompetitor(){
		return (rankableentityByRightRankableEntityReference instanceof Competitor) 
				? (Competitor) rankableentityByRightRankableEntityReference : null;
	}

	@Override
	public ScoreSheetEntry getScoreSheet() {
		// TODO Auto-generated method stub
		return null;
	}

}
