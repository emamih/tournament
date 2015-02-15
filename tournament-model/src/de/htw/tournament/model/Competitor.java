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
 * Competitor generated by hbm2java
 */
@XmlRootElement
@Entity
@Table(schema = "tournament")
@PrimaryKeyJoinColumn(name = "competitorIdentity")
public class Competitor extends Rankableentity implements java.io.Serializable {

	/**
	 * 
	 */
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
	public ScoreSheetEntry getScoreSheet() {
		// TODO Auto-generated method stub
		return null;
	}

}
