package matej.tejkogames.models.payload.requests;

import java.util.Set;

import javax.validation.constraints.*;

import matej.tejkogames.constants.TejkoGamesConstants;
 
public class RegisterRequest {

    @NotBlank
    @Size(min = TejkoGamesConstants.USERNAME_LENGTH_MIN, max = TejkoGamesConstants.USERNAME_LENGTH_MAX)
    private String username;
    
    private Set<String> roles;
    
    @NotBlank
    private String password;
  
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
    
    public Set<String> getRoles() {
      return this.roles;
    }
    
    public void setRole(Set<String> roles) {
      this.roles = roles;
    }
}