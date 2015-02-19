package de.htw.tournament.model;
// default package
// Generated 09.02.2015 16:09:37 by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import de.htw.tournament.rest.ServiceProvider;

@XmlRootElement
@Entity
@Table(schema = "tournament")
@PrimaryKeyJoinColumn(name = "competitorIdentity")
public class Competitor extends Rankableentity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "logoReference", nullable = false)
	private Document document;
	
	@Column(name = "alias", unique = true, nullable = false, length = 64)
	private String alias;

	public Competitor() {
	}

	public Competitor(Document document, Rankableentity rankableentity,
			String alias) {
		this.document = document;
		this.alias = alias;
	}
	public Document getDocument() {
		return this.document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	@Override
	public Collection<ScoreSheetEntry> getScoreSheet() {
		// TODO Auto-generated method stub
		
		final String CRITERIA_QUERY_JPQL = "select "
				+ " c, d, ds.score, ds.opponentScore, ds.points "
				+ "from Divisionscoresheetentry ds, Division d, Competitor c "
				+ "where "
				+ " ds.rootReference = d.identity and ds.competitorReference = c.identity and "
				+ "(c.identity = :rootReference)";
		
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
		
	}

}
