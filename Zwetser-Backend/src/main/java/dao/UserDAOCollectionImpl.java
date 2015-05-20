package dao;

import domain.Follow;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import domain.ApplicationUser;
import qualifiers.Collection;

@Stateless
@Collection
public class UserDAOCollectionImpl implements UserDAO {
    
    @Inject
    DataStorageBean dsb;
    
    public UserDAOCollectionImpl() {
    }

    @Override
    public int countUsers() {
        return dsb.count();
    }

    @Override
    public void addUser(ApplicationUser user) {
        dsb.addUser(user);
    }

    @Override
    public void editUser(ApplicationUser user) {
       dsb.editUser(user);
    }

    @Override
    public List<ApplicationUser> findAll() {
        return dsb.findAllUsers();
    }

    @Override
    public void remove(ApplicationUser user) {
        dsb.removeUser(user);
    }

    @Override
    public ApplicationUser find(long id) {
        return dsb.findUser(id);
    }   

    @Override
    public void addFollow(Follow follow) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countFollowing(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countFollowers(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ApplicationUser> getFollowing(ApplicationUser follower) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ApplicationUser> getFollowers(ApplicationUser followed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeFollow(Follow f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ApplicationUser findByUsernameAndPassword(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ApplicationUser findByUsernameAndAuthToken(String authId, String authToken) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void userloggedIn(ApplicationUser user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void userloggedOut(ApplicationUser user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
 
}
