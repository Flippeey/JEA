/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Follow;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import domain.Tweet;
import domain.ApplicationUser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class DataStorageBean {

    private List<ApplicationUser> users = new ArrayList();
    private List<Tweet> tweets = new ArrayList();
    private List<Follow> follows = new ArrayList();

    @PostConstruct
    private void initUsers() {
        System.out.println("init users");
        ApplicationUser u1 = new ApplicationUser(1, "Jason", "Flippey", "Http://www.google.nl", "I am groot", "jason@gmail.com", "qwerty", "src/1375565200261.jpg", "Nederland");
        ApplicationUser u2 = new ApplicationUser(2, "Berry", "Elusive", "Http://www.google.nl", "I am balloon", "berry@gmail.com", "qwerty", "src/1375115581068.jpg", "Nederland");
        ApplicationUser u3 = new ApplicationUser(3, "Alexander", "Schnitzel", "Http://www.google.nl", "I am fat hobbit", "alexander@gmail.com", "qwerty", "src/1375209930133.jpg", "Nederland");
        ApplicationUser u4 = new ApplicationUser(4, "Patrick", "PetMan", "Http://www.google.nl", "I am perfect lol jk", "patrick@gmail.com", "qwerty", "src/1389572315769.jpg", "Nederland");
        ApplicationUser u5 = new ApplicationUser(5, "Jeroen", "Sjeroen", "Http://www.google.nl", "I am sjors", "jeroen@gmail.com", "qwerty", "src/1379491540480.gif", "Nederland");

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDate = formatter.format(new Date());

        try {
            Date date = formatter.parse(formattedDate);
            Tweet t1 = new Tweet("Dit is mijn eerste zweet", date, "PC", u1.getId());
            Tweet t2 = new Tweet("Tweede Zweet", date, "PC", u1.getId());
            Tweet t3 = new Tweet("Dit maakt dri3", date, "PC", u1.getId());

            Tweet t4 = new Tweet("Deze zweet is van user 2", date, "PC", u2.getId());
            Tweet t5 = new Tweet("Deze zweet is van user 3", date, "PC", u3.getId());
            Tweet t6 = new Tweet("Deze zweet is van user 4", date, "PC", u4.getId());
            Tweet t7 = new Tweet("Deze zweet is van user 5", date, "PC", u5.getId());

            addTweet(t1);
            addTweet(t2);
            addTweet(t3);
            addTweet(t4);
            addTweet(t5);
            addTweet(t5);
            addTweet(t6);
            addTweet(t6);
            addTweet(t6);
            addTweet(t7);
        } catch (ParseException ex) {
            Logger.getLogger(DataStorageBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        addFollow(new Follow(u1, u2));
//        addFollowing(u2.getId());
//        addFollower(u1.getId());
//        
//        addFollowing(u3.getId());
//        addFollower(u1.getId());
//        
//        addFollowing(u4.getId());
//        addFollower(u1.getId());
//        
//        addFollowing(u5.getId());
//        addFollower(u1.getId());
//        
//        addFollowing(u1.getId());
//        addFollower(u2.getId());
//        
//        addFollowing(u2.getId());
//        addFollower(u3.getId());
//        
//        addFollowing(u1.getId());
//        addFollower(u3.getId());
//        
//        addFollowing(u3.getId());
//        addFollower(u4.getId());
//        
//        addFollowing(u1.getId());
//        addFollower(u4.getId());
//        
//        addFollowing(u5.getId());
//        addFollower(u4.getId());
//        
//        addFollowing(u1.getId());
//        addFollower(u5.getId());
//        
//        addFollowing(u4.getId());
//        addFollower(u5.getId());

        this.addUser(u1);
        this.addUser(u2);
        this.addUser(u3);
        this.addUser(u4);
        this.addUser(u5);
    }

    public int count() {
        return users.size();
    }

    public void addUser(ApplicationUser user) {
        users.add(user);
    }

    public void addTweet(Tweet tweet) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDate = formatter.format(new Date());
        Date date;
        try {
            date = formatter.parse(formattedDate);
            tweet.setPostDate(date);
        } catch (ParseException ex) {
            Logger.getLogger(DataStorageBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        tweets.add(tweet);
    }

    public void editUser(ApplicationUser user) {
        for (ApplicationUser user1 : users) {
            if (user1.getEmail().equals(user.getEmail())) {
                users.add(users.indexOf(user), user1);
            }
        }
    }

    public void editTweet(Tweet tweet) {
        for (Tweet t : tweets) {
            if (t.getId() == tweet.getId()) {
                tweets.add(tweets.indexOf(t), tweet);
            }
        }
    }

    public List<ApplicationUser> findAllUsers() {
        return users;
    }

    public List<Tweet> findAllTweets() {
        return tweets;
    }

    public void removeUser(ApplicationUser user) {
        users.remove(user);
    }

    public void removeTweet(Tweet tweet) {
        tweets.remove(tweet);
    }

    public ApplicationUser findUser(long id) {
        for (ApplicationUser user : users) {
            if (user.getId() == id) {

                return user;
            }
        }
        return null;
    }

    public Tweet findTweet(long id) {
        for (Tweet tweet : tweets) {
            if (tweet.getId() == id) {
                return tweet;
            }
        }
        return null;
    }

    public List<Tweet> findUserTweets(long id) {
        List<Tweet> temp = new ArrayList<Tweet>();
        for (Tweet tweet : tweets) {
            if (tweet.getPostedBy() == id) {
                temp.add(tweet);
            }
        }
        return temp;
    }

    public void addFollow(Follow follow) {
        follows.add(follow);
    }

}
