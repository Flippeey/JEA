/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Flippey
 */
@XmlRootElement
@Entity
public class Follow implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "follower")
    private ApplicationUser follower;

    @Id
    @ManyToOne
    @JoinColumn(name = "followed")
    private ApplicationUser followed;

    public Follow() {
    }

    public Follow(ApplicationUser follower, ApplicationUser followed) {
        this.follower = follower;
        this.followed = followed;
    }

    public ApplicationUser getFollower() {
        return follower;
    }

    public void setFollower(ApplicationUser follower) {
        this.follower = follower;
    }

    public ApplicationUser getFollowed() {
        return followed;
    }

    public void setFollowed(ApplicationUser followed) {
        this.followed = followed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((followed == null) ? 0 : followed.hashCode());
        result = prime * result + ((follower == null) ? 0 : follower.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Follow other = (Follow) obj;
        if (followed == null) {
            if (other.followed != null) {
                return false;
            }
        } else if (!followed.equals(other.followed)) {
            return false;
        }
        if (follower == null) {
            if (other.follower != null) {
                return false;
            }
        } else if (!follower.equals(other.follower)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Following [user=" + follower + ", follows=" + followed + "]";
    }
}
