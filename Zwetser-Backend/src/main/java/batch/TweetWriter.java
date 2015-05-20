package batch;

import java.util.List;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;
import javax.persistence.EntityManager;

import domain.Tweet;
import javax.persistence.PersistenceContext;

@Named
public class TweetWriter extends AbstractItemWriter {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void writeItems(List<Object> items) throws Exception {
		@SuppressWarnings("unchecked")
		List<Tweet> tweets = (List<Tweet>) (List<?>) items;
		for (Tweet tweet : tweets) {
			em.persist(tweet);
		}
	}
}