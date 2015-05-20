package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@NamedQueries({
    @NamedQuery(name = "User.findAll",
            query = "SELECT u FROM ApplicationUser u"),
    @NamedQuery(name = "User.findById",
            query = "SELECT u FROM ApplicationUser u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByNameAndPassword",
            query = "SELECT u FROM ApplicationUser u WHERE u.email = :email AND u.password = :password"),
    @NamedQuery(name = "User.findByUsernameAndAuthToken",
            query = "SELECT u FROM ApplicationUser u WHERE u.email = :authId AND u.authToken = :authToken")})
public class ApplicationUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;
    private String web;
    private String bio;
    private String email;
    private String password;
    private String avatar;
    private String location;
    private String role;

    private String authToken;


    @OneToMany(mappedBy = "follower", orphanRemoval = true)
    private Collection<Follow> following = new ArrayList();
    @OneToMany(mappedBy = "followed", orphanRemoval = true)
    private Collection<Follow> followers = new ArrayList();
//
//    @ManyToMany(mappedBy = "applicationUser")
//    private List<UserGroup> user_roles;

    public ApplicationUser() {
    }

    public ApplicationUser(long id, String name, String username, String web, String bio, String email, String password, String avatar, String location) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.web = web;
        this.bio = bio;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.location = location;
    }
   
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() + bio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the name fields are not set
        if (!(object instanceof ApplicationUser)) {
            return false;
        }
        ApplicationUser other = (ApplicationUser) object;
        return this.hashCode() == other.hashCode();
    }

    @Override
    public String toString() {
        return "twitter.domain.User[naam=" + name + "]";
    }

}
