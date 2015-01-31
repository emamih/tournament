package de.htw.tournament.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import de.sb.javase.TypeMetadata;


/**
 * Adapter class for the XML marshaling of entities to their identities. Note that the marshaling
 * is one-way, i.e. the resulting values cannot be mapped back into an entity as we lack access to
 * an active entity manager!
 */
@TypeMetadata(copyright = "2013-2014 Sascha Baumeister, all rights reserved", version = "0.1.0", authors = "Sascha Baumeister")
public class EntityIdentityAdapter extends XmlAdapter<Long,Document> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long marshal (final Document entity) {
		return entity == null ? null : entity.getIdentity();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document unmarshal (final Long identity) {
		return null;
	}
}