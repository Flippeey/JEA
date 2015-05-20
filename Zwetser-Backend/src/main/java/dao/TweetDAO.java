/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Tweet;
import java.util.List;
import javax.enterprise.event.Observes;

/**
 *
 * @author Flippey
 */
public interface TweetDAO {

    List<Tweet> getAll();

    List<Tweet> getTweetsByUser(long userId);
    
    List<Tweet> getTimeLine(long userId);

    Tweet getTweet(long id);

    void addTweet(@Observes Tweet tweet);

    void editTweet(Tweet tweet);

    void removeTweet(Tweet tweet);
    
    int countUserTweets(long id);
    
}
