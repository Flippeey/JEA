package service;

import Authentication.AuthAccessElement;
import Authentication.AuthLoginElement;
import dao.DataStorageBean;
import dao.TweetDAO;
import java.util.List;
import dao.UserDAO;
import domain.Follow;
import domain.Tweet;
import domain.ApplicationUser;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import qualifiers.JPA;

@Stateless
public class KwetterService {

    @JPA
    @Inject
    Event<Tweet> addTweetEvent;

    @JPA
    @Inject
    Event<ApplicationUser> LoginEvent;

    @JPA
    @Inject
    private UserDAO userDAO;

    @JPA
    @Inject
    private TweetDAO tweetDAO;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public KwetterService() {
    }

    public void addUser(ApplicationUser user) {
        userDAO.addUser(user);
    }

    public void editUser(ApplicationUser user) {
        userDAO.editUser(user);
    }

    public void removeUser(ApplicationUser user) {
        userDAO.remove(user);
    }

    public List<ApplicationUser> findAll() {
        return userDAO.findAll();
    }

    public ApplicationUser find(long id) {
        System.out.println(id);
        return userDAO.find(id);
    }

    public void addFollow(Follow follow) {
        userDAO.addFollow(follow);
    }

    public List<ApplicationUser> getFollowing(long follower) {
        ApplicationUser u = find(follower);
        return userDAO.getFollowing(u);
    }

    public List<ApplicationUser> getFollowers(long followed) {
        ApplicationUser u = find(followed);
        return userDAO.getFollowers(u);
    }

    public int countFollowing(long userId) {
        return userDAO.countFollowing(userId);
    }

    public int countFollowers(long userId) {
        return userDAO.countFollowers(userId);
    }

    public int countTweets(long id) {
        return tweetDAO.countUserTweets(id);
    }

    public int countUsers() {
        return userDAO.countUsers();
    }

    private java.sql.Date tweetDate() {
        String formattedDate = DATE_FORMAT.format(new Date());
        try {
            Date date = DATE_FORMAT.parse(formattedDate);
            java.sql.Date sqldate = new java.sql.Date(date.getTime());
            return sqldate;
        } catch (ParseException ex) {
            Logger.getLogger(DataStorageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void addTweet(Tweet tweet) {
        tweet.setPostDate(tweetDate());
        addTweetEvent.fire(tweet);
    }

    public void updateTweet(Tweet t) {
        t.setPostDate(tweetDate());
        tweetDAO.editTweet(t);
    }

    public void removeTweet(Tweet t) {
        tweetDAO.removeTweet(t);
    }

    public List<Tweet> getAllTweets() {
        return tweetDAO.getAll();
    }

    public List<Tweet> getTweetsByUser(long id) {
        return tweetDAO.getTweetsByUser(id);
    }

    public void removeFollow(Follow f) {
        userDAO.removeFollow(f);
    }

    public List<Tweet> getTimeLine(long id) {
        return tweetDAO.getTimeLine(id);
    }

    public List<Tweet> getMentions(String username) {
        username = '@' + username.toLowerCase();
        List<Tweet> mentions = new ArrayList<>();
        for (Tweet tweet : tweetDAO.getAll()) {
            if (tweet.getMessage().toLowerCase().contains(username)) {
                mentions.add(tweet);
            }
        }
        return mentions;
    }

    public static String sha256(String base) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(base.getBytes());
            Encoder bas = Base64.getEncoder();
            String base64 = bas.encodeToString(digest);
            return base64;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public AuthAccessElement login(AuthLoginElement loginElement) {
        ApplicationUser user = userDAO.findByUsernameAndPassword(loginElement.getUsername(), sha256(loginElement.getPassword()));

        if (user != null) {
            System.out.println(user.getName());
            user.setAuthToken(UUID.randomUUID().toString());
            userDAO.editUser(user);
            LoginEvent.fire(user);
            return new AuthAccessElement(loginElement.getUsername(), user.getAuthToken(), user.getRole());
        }
        System.out.println("User not found for: " + loginElement.getUsername());
        return null;
    }

    public boolean isAuthorized(String authId, String authToken, Set<String> rolesAllowed) {
        ApplicationUser user = userDAO.findByUsernameAndAuthToken(authId, authToken);
        if (user != null) {
            return rolesAllowed.contains(user.getRole());
        } else {
            return false;
        }
    }

    public ApplicationUser findByIdAndToken(String id, String token) {
        return userDAO.findByUsernameAndAuthToken(id, token);
    }

}
