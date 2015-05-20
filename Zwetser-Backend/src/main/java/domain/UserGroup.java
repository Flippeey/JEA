///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package domain;
//
//import java.io.Serializable;
//import java.util.List;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToMany;
//import javax.xml.bind.annotation.XmlRootElement;
//
///**
// *
// * @author Flippey
// */
//@XmlRootElement
//@Entity
//public class UserGroup implements Serializable {
//
//    private static final long serialVersionUID = -7274308564659753174L;
//    @Id
//    @GeneratedValue
//    private Long id;
//    private List<String> groupNames;
//    @ManyToMany
//    @JoinColumn(name = "USER_ID")
//    private ApplicationUser applicationUser;
//
//    public UserGroup() {
//    }
//
//    public List<String> getGroupNames() {
//        return groupNames;
//    }
//
//    public void setGroupNames(List<String> groupNames) {
//        this.groupNames = groupNames;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public ApplicationUser getUser() {
//        return applicationUser;
//    }
//
//    public void setUser(ApplicationUser user) {
//        this.applicationUser = user;
//    }
//
//    
//}
