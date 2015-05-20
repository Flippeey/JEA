package domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@NamedQueries({
    @NamedQuery(name = "Tweet.findAll",
            query = "SELECT t FROM Tweet t"),
    @NamedQuery(name = "Tweet.findByUser",
            query = "SELECT t FROM Tweet t WHERE t.postedBy = :id"),
    @NamedQuery(name = "Tweet.findTimeline",
            query = "SELECT t FROM Tweet t WHERE t.postedBy IN "
            + "(SELECT f.followed.id FROM Follow f WHERE f.follower.id = :id)"),
    @NamedQuery(name = "Tweet.countTweets",
            query = "SELECT count(t) FROM Tweet t WHERE t.postedBy = :id"),})
public class Tweet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String message;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date postDate;
    private String postedFrom;
    private long postedBy;
    private String screenName;

    public Tweet() {
    }

    
    public Tweet(String message, Date postDate, String postedFrom, long postedBy) {
        this.message = message;
        this.postDate = postDate;
        this.postedFrom = postedFrom;
        this.postedBy = postedBy;
    }

    public Tweet(String message, String postedFrom, long postedBy, String screenName) {
        this.message = message;
        this.postedFrom = postedFrom;
        this.postedBy = postedBy;
        this.screenName = screenName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getPostedFrom() {
        return postedFrom;
    }

    public void setPostedFrom(String postedFrom) {
        this.postedFrom = postedFrom;
    }

    public long getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(long postedBy) {
        this.postedBy = postedBy;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (message != null ? message.hashCode() + postDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Tweet)) {
            return false;
        }
        Tweet other = (Tweet) object;
        return this.hashCode() == other.hashCode();
    }

    @Override
    public String toString() {
        return "twitter.domain.Tweet[id=" + postDate.toString() + "]";
    }

}

