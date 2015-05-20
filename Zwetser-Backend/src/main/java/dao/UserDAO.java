package dao;

import domain.Follow;
import domain.ApplicationUser;
import java.util.List;
import javax.enterprise.event.Observes;

public interface UserDAO {

    int countUsers();

    void addUser(ApplicationUser user);

    void editUser(ApplicationUser user);

    List<ApplicationUser> findAll();

    ApplicationUser find(long id);

    void remove(ApplicationUser user);

    void addFollow(Follow follow);
    
    void userloggedIn(@Observes ApplicationUser user);
    
    void userloggedOut(ApplicationUser user);

    List<ApplicationUser> getFollowing(ApplicationUser follower);

    List<ApplicationUser> getFollowers(ApplicationUser followed);

    int countFollowing(long id);
    
    int countFollowers(long id);

    public void removeFollow(Follow f);

    public ApplicationUser findByUsernameAndPassword(String username, String password);

    public ApplicationUser findByUsernameAndAuthToken(String authId, String authToken);
}
