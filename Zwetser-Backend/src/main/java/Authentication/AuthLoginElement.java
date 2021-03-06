/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Authentication;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Flippey
 */
@XmlRootElement
public class AuthLoginElement implements Serializable {
 
    private String username;
    private String password;
 
    public AuthLoginElement(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthLoginElement() {
    }
    
     
    // Getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}