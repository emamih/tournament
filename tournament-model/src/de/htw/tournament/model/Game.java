package de.htw.tournament.model;
// default package
// Generated 09.02.2015 16:09:37 by Hibernate Tools 4.3.1

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
  */
@SuppressWarnings("rawtypes")
@XmlRootElement
@Entity
@Table(schema = "tournament")
@PrimaryKeyJoinColumn(name = "gameIdentity")
@XmlAccessorType(XmlAccessType.NONE)
//TODO änderung in db übernehemen
public class Game  extends Rankableentity implements java.io.Serializable, Comparable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rootReference")
	private Division root;
	
//	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rightRankableEntityReference", nullable = false)
	private Rankableentity rightRankableEntity;
	
//	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "leftRankableEntityReference", nullable = false)
	private Rankableentity leftRankableEntity;
	
//	@XmlElement
	@Column(name = "leftOrdinal", nullable = false)
	private Short leftOrdinal;
	
//	@XmlElement
	@Column(name = "leftScore")
	private Short leftScore;
	
//	@XmlElement
	@Column(name = "rightOrdinal", nullable = false)
	private Short rightOrdinal;
	
//	@XmlElement
	@Column(name = "rightScore")
	private Short rightScore;
	
	
	//TODO private Division root
	public Game() {
	}

	public Game(Rankableentity rightRankableEntity,
			Rankableentity leftRankableEntity,
			Rankableentity rankableentityByIdentity, Short leftOrdinal,
			Short rightOrdinal) {
		this.rightRankableEntity = rightRankableEntity;
		this.leftRankableEntity = leftRankableEntity;
		this.leftOrdinal = leftOrdinal;
		this.rightOrdinal = rightOrdinal;
	}

	public Game(Division division,
			Rankableentity rankableentityByRightRankableEntityReference,
			Rankableentity rankableentityByLeftRankableEntityReference,
			Rankableentity rankableentityByIdentity, Short leftOrdinal,
			Short leftScore, Short rightOrdinal, Short rightScore) {
		this.root = division;
		this.rightRankableEntity = rankableentityByRightRankableEntityReference;
		this.leftRankableEntity = rankableentityByLeftRankableEntityReference;
		this.leftOrdinal = leftOrdinal;
		this.leftScore = leftScore;
		this.rightOrdinal = rightOrdinal;
		this.rightScore = rightScore;
	}

	
	public Division getRoot() {
		return this.root;
	}

	public void setRoot(Division division) {
		this.root = division;
	}

	
	public Rankableentity getRankableentityByRightRankableEntityReference() {
		return this.rightRankableEntity;
	}

	public void setRankableentityByRightRankableEntityReference(
			Rankableentity rankableentityByRightRankableEntityReference) {
		this.rightRankableEntity = rankableentityByRightRankableEntityReference;
	}

	
	public Rankableentity getRankableentityByLeftRankableEntityReference() {
		return this.leftRankableEntity;
	}

	public void setRankableentityByLeftRankableEntityReference(
			Rankableentity rankableentityByLeftRankableEntityReference) {
		this.leftRankableEntity = rankableentityByLeftRankableEntityReference;
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
	
//	public Competitor getLeftCompetitor() {
//		try {
//			 leftRankableEntity.getScoreSheet();
//		} catch (Exception e) {
//		}
//		return (leftRankableEntity instanceof Competitor) 
//				? (Competitor) leftRankableEntity : null;
//	}
//	
//	public Competitor getRightCompetitor(){
//		return (rightRankableEntity instanceof Competitor) 
//				? (Competitor) rightRankableEntity : null;
//	}
	@XmlElement
	public Competitor getRightCompetitor () {
		   final Collection<ScoreSheetEntry> scoreSheet = this.rightRankableEntity.getScoreSheet();
		   if (this.rightOrdinal >= scoreSheet.size()) return null;
		   return ((ScoreSheetEntry)scoreSheet.toArray()[this.rightOrdinal]).getCompetitor();
	}
	
	@XmlElement
	public Competitor getLeftCompetitor () {
		   final Collection<ScoreSheetEntry> scoreSheet = this.leftRankableEntity.getScoreSheet();
		   if (this.leftOrdinal >= scoreSheet.size()) return null;
		   return ((ScoreSheetEntry)scoreSheet.toArray()[this.leftOrdinal]).getCompetitor();
	}

	@Override
	public Collection<ScoreSheetEntry> getScoreSheet() {
		// TODO Auto-generated method stub
		
//		final String CRITERIA_QUERY_JPQL = "select "
//				+ " c, d, ds.score, ds.opponentScore, ds.points "
//				+ "from Divisionscoresheetentry ds, Division d, Competitor c "
//				+ "where "
//				+ " ds.rootReference = d.identity and ds.competitorReference = c.identity and "
//				+ "(:rootReference is null or ds.rootReference = :rootReference)";
//		
//		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();

//		try {
			
//			final TypedQuery<Object[]> query = entityManager.createQuery(CRITERIA_QUERY_JPQL, Object[].class);
//			query.setParameter("rootReference", this.root.getIdentity());
			
			final SortedSet<ScoreSheetEntry> scoresheets = new TreeSet<>();
			Collection<Object[]> results = new ArrayList<Object[]>(); 
			Object[] results_left = new Object[]{this.getLeftCompetitor(),null,this.getLeftScore(),this.getRightScore(),0};
			Object[] results_right = new Object[]{this.getRightCompetitor(),null,this.getRightScore(),this.getLeftScore(),0};
			
			if(this.getLeftScore()>this.getRightScore()){
				results.add(results_left);
				results.add(results_right);
			} else {
				results.add(results_right);
				results.add(results_left);
			}
			
			for (Object[] result : results) {
			   Competitor competitor = (Competitor) result[0];
			   Division root = (Division) result[1];
			   Integer score = ((Short) result[2]).intValue();
			   Integer opponentScore = ((Short) result[3]).intValue();
			   Double points = ((Integer) result[4]).doubleValue();
			   ScoreSheetEntry sce = new ScoreSheetEntry(competitor, root, score, opponentScore, points);
			   scoresheets.add(sce);
			}
			
			return scoresheets;

//		} finally {
//			try { entityManager.close(); } catch (final Exception exception) {}
//		}
		
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof Game){
			Game game =(Game) o;
			
			if(this.getIdentity() > game.getIdentity()){
				return 1;
			} else if(this.getIdentity() < game.getIdentity()) {
				return -1;
			} else return 0;
		}
		
		return +1;
	}

}
