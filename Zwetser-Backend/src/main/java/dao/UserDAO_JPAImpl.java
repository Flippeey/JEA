/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Follow;
import domain.ApplicationUser;
import domain.Follow_;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import qualifiers.JPA;

/**
 *
 * @author Flippey
 */
@JPA
@Stateless
public class UserDAO_JPAImpl implements UserDAO {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    @Override
    public int countUsers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addUser(ApplicationUser user) {
        em.persist(user);
    }

    @Override
    public void editUser(ApplicationUser user) {
        em.merge(user);
    }

    @Override
    public List<ApplicationUser> findAll() {
        TypedQuery<ApplicationUser> query = em.createNamedQuery("User.findAll", ApplicationUser.class);
        return query.getResultList();
    }

    @Override
    public ApplicationUser find(long id) {
        try {
            TypedQuery<ApplicationUser> query = em.createNamedQuery("User.findById", ApplicationUser.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("find user null");
            return null;
        }
    }

    @Override
    public void remove(ApplicationUser user) {
        em.remove(user);
    }

    @Override
    public void addFollow(Follow follow) {
        em.persist(follow);
    }

    @Override
    public List<ApplicationUser> getFollowing(ApplicationUser follower) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ApplicationUser> criteriaQuery = cb.createQuery(ApplicationUser.class);
        Root<Follow> follow = criteriaQuery.from(Follow.class);
        criteriaQuery.select(follow.get(Follow_.followed));
        criteriaQuery.where(cb.equal(follow.get(Follow_.follower), cb.parameter(ApplicationUser.class, "follower")));
        Query query = em.createQuery(criteriaQuery);
        query.setParameter("follower", follower);
        List<ApplicationUser> result = query.getResultList();

        return result;
    }

    @Override
    public List<ApplicationUser> getFollowers(ApplicationUser followed) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ApplicationUser> criteriaQuery = cb.createQuery(ApplicationUser.class);
        Root<Follow> follow = criteriaQuery.from(Follow.class);
        criteriaQuery.select(follow.get(Follow_.follower));
        criteriaQuery.where(cb.equal(follow.get(Follow_.followed), cb.parameter(ApplicationUser.class, "followed")));
        Query query = em.createQuery(criteriaQuery);
        query.setParameter("followed", followed);
        List<ApplicationUser> result = query.getResultList();

        return result;
    }

    @Override
    public int countFollowing(long id) {
//        ApplicationUser u = find(id);
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Long> query = cb.createQuery(Long.class);
//        Root<Follow> follow = query.from(Follow.class);
//        query.select(follow.get(Follow_.follower));
//        query.where(cb.equal(follow.get(Follow_.follower), cb.parameter(ApplicationUser.class, "follower")));
//        query.setParameter("follower", id);
//        int count = em.createQuery(query).getSingleResult().intValue();
//        return count;
        return 0;
    }

    @Override
    public int countFollowers(long id) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Long> query = cb.createQuery(Long.class);
//        Root<Follow> follow = query.from(Follow.class);
//        query.select(follow.get(Follow_.follower));
//        query.where(cb.equal(follow.get(Follow_.followed), cb.parameter(ApplicationUser.class, "followed")));
//        int count = em.createQuery(query).getSingleResult().intValue();
//        return count;
        return 0;
    }

    @Override
    public void removeFollow(Follow f) {
        Follow remove = em.merge(f);
        em.remove(remove);
    }

    @Override
    public ApplicationUser findByUsernameAndPassword(String username, String password) {
        try {
            TypedQuery<ApplicationUser> query = em.createNamedQuery("User.findByNameAndPassword", ApplicationUser.class);
            query.setParameter("email", username);
            query.setParameter("password", password);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("find user null");
            return null;
        }
    }

    @Override
    public ApplicationUser findByUsernameAndAuthToken(String authId, String authToken) {
        try {
            TypedQuery<ApplicationUser> query = em.createNamedQuery("User.findByUsernameAndAuthToken", ApplicationUser.class);
            query.setParameter("authId", authId);
            query.setParameter("authToken", authToken);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("find user null");
            return null;
        }
    }

    @Override
    public void userloggedIn(@Observes @JPA ApplicationUser user) {
        System.out.println("User Logged in: " + user.getName());
    }

    @Override
    public void userloggedOut(ApplicationUser user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
