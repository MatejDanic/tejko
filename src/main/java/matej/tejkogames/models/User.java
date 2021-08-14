package matej.tejkogames.models;

// import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
// import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@Table(name = "auth_user")
@RestResource(rel = "users", path = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // @JsonIgnoreProperties("user")
    // @OneToMany(mappedBy = "user", cascade = {CascadeType.DETACH,
    // CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    // private List<GameScore> scores;

    // @JsonIgnore
    // @OneToOne(mappedBy = "user", cascade = {CascadeType.DETACH,
    // CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    // private GameForm form;

    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 15)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "auth_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.REMOVE }, fetch = FetchType.LAZY)
    private Preference preference;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // public List<GameScore> getScores() {
    // return scores;
    // }

    // public void setScores(List<GameScore> scores) {
    // this.scores = scores;
    // }

    // public GameForm getForm() {
    // return form;
    // }

    // public void setForm(GameForm form) {
    // this.form = form;
    // }

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    @Override
    public String toString() {
        String string = username;
        for (Role role : roles) {
            string += role.getLabel() + ": " + role.getdescription() + "\n";
        }
        return string;
    }
}