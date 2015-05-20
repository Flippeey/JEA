/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Tweet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import qualifiers.JPA;

/**
 *
 * @author Flippey
 */
@JPA
@Stateless
public class TweetDAO_JPAImpl implements TweetDAO {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    @Override
    public List<Tweet> getAll() {
        TypedQuery<Tweet> query = em.createNamedQuery("Tweet.findAll", Tweet.class);
        return query.getResultList();
    }

    @Override
    public List<Tweet> getTweetsByUser(long userId) {
        TypedQuery<Tweet> query = em.createNamedQuery("Tweet.findByUser", Tweet.class).setParameter("id", userId);
        return query.getResultList();
    }

    @Override
    public Tweet getTweet(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.        
    }

    @Override
    public void addTweet(@Observes @JPA Tweet tweet) {
        em.persist(tweet);
    }

    @Override
    public void editTweet(Tweet tweet) {
        em.merge(tweet);
    }

    @Override
    public void removeTweet(Tweet tweet) {
        Tweet remove = em.merge(tweet);
        em.remove(remove);
    }

    @Override
    public List<Tweet> getTimeLine(long userId) {
        TypedQuery<Tweet> query = em.createNamedQuery("Tweet.findTimeline", Tweet.class).setParameter("id", userId);
        return query.getResultList();
    }

    @Override
    public int countUserTweets(long id) {
        TypedQuery<Tweet> query = em.createNamedQuery("Tweet.countTweets", Tweet.class).setParameter("id", id);
        // return query.getSingleResult();
        return 0;
    }

}
