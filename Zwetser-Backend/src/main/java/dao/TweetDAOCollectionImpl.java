/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Tweet;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import qualifiers.Collection;

/**
 *
 * @author Flippey
 */
@Stateless
@Collection
public class TweetDAOCollectionImpl implements TweetDAO {
    
    @Inject
    DataStorageBean dsb;
    
    @Override
    public List<Tweet> getAll() {
        return dsb.findAllTweets();
    }

    @Override
    public List<Tweet> getTweetsByUser(long userId) {        
        return dsb.findUserTweets(userId);
    }

    @Override
    public Tweet getTweet(long id) {
        return dsb.findTweet(id);
    }

    @Override
    public void addTweet(Tweet tweet) {
        dsb.addTweet(tweet);
    }

    @Override
    public void editTweet(Tweet tweet) {
        dsb.editTweet(tweet);
    }

    @Override
    public void removeTweet(Tweet tweet) {
        dsb.removeTweet(tweet);
    }

    @Override
    public List<Tweet> getTimeLine(long userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countUserTweets(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 

}
