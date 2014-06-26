package test.builder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import aleph.AbstractBuilder;
import aleph.PersistenceProvider;

public class JpaPersistenceProvider implements PersistenceProvider {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(AbstractBuilder<?> builder) {
		builder.build();
		em.persist(builder.get());

	}

	@Override
	public void close() {
		em.flush();
		em.clear();
		em.close();

	}

	@Override
	public void clear() {

	}

}
