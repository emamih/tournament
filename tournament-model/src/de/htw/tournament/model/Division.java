package de.htw.tournament.model;
// default package
// Generated 09.02.2015 16:09:37 by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import antlr.collections.List;
import de.htw.tournament.rest.ServiceProvider;

@XmlRootElement
@Entity
@Table(schema = "tournament")
@XmlAccessorType(XmlAccessType.FIELD)
@PrimaryKeyJoinColumn(name = "divisionIdentity")
public class Division extends Rankableentity implements java.io.Serializable, Comparable {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "competitionReference", nullable = false)
	private Competition competition;
	
	@XmlElement
	@Column(name = "alias", nullable = false, length = 20)
	private String alias;
	
	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "root")
	private Set<Game> rootGames = new HashSet<Game>(0);

	public Division() {
	}

	public Division(Competition competition, Rankableentity rankableentity,
			String alias) {
		this.competition = competition;
		this.alias = alias;
	}

	public Division(Competition competition, Rankableentity rankableentity,
			String alias, Set<Game> games) {
		this.competition = competition;
		this.alias = alias;
		this.rootGames = games;
	}

	
	public Competition getCompetition() {
		return this.competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	
	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	
	public Set<Game> getRootGames() {
		return this.rootGames;
	}

	public void setRootGames(Set<Game> rootGames) {
		this.rootGames = rootGames;
	}
	
	public Set<Competitor> getCompetitors(){
		TreeSet<Competitor> competitors = new TreeSet<Competitor>();
		for(Game game : this.rootGames){
			competitors.add(game.getLeftCompetitor());
			competitors.add(game.getRightCompetitor());
		}
		return competitors;
	}

	@Override
	public Collection<ScoreSheetEntry> getScoreSheet() {
		// TODO Auto-generated method stub
		
		final String CRITERIA_QUERY_JPQL = "select "
				+ " c, d, ds.score, ds.opponentScore, ds.points "
				+ "from Divisionscoresheetentry ds, Division d, Competitor c "
				+ "where "
				+ " ds.rootReference = d.identity and ds.competitorReference = c.identity and "
				+ "(ds.rootReference = :rootReference)";
		
		final EntityManager entityManager = ServiceProvider.TOURNAMENT_FACTORY.createEntityManager();

		try {
			
			final TypedQuery<Object[]> query = entityManager.createQuery(CRITERIA_QUERY_JPQL, Object[].class);
			query.setParameter("rootReference", this.getIdentity());
			
			final SortedSet<ScoreSheetEntry> scoresheets = new TreeSet<>();
			Collection<Object[]> results = query.getResultList();
			for (Object[] result : results) {
			   Competitor competitor = (Competitor) result[0];
			   Division root = (Division) result[1];
			   Integer score = ((BigDecimal) result[2]).intValue();
			   Integer opponentScore = ((BigDecimal) result[3]).intValue();
			   Double points = (Double) result[4];
			   ScoreSheetEntry sce = new ScoreSheetEntry(competitor, root, score, opponentScore, points);
			   scoresheets.add(sce);
			}
			
			return scoresheets;

		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
		
//		ArrayList<ScoreSheetEntry> ret = new ArrayList<ScoreSheetEntry>();
//		
//		for(Game game : rootGames){
//			if(!ret.contains(game.getLeftCompetitor())){
//				
//			} else {
//				
//			}
//			if(!ret.contains(game.getRightCompetitor())){
//				
//			} else {
//				
//			}
//		}
//		
//		return null;
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof Division){
			Division temp = (Division) o;
			return this.getAlias().compareTo(temp.getAlias());
		}
		return +1;
	}

}
