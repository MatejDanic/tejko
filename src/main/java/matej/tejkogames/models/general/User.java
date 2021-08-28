package matej.tejkogames.models.general;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import matej.tejkogames.constants.TejkoGamesConstants;
import matej.tejkogames.models.yamb.Yamb;
import matej.tejkogames.models.yamb.YambChallenge;
import matej.tejkogames.models.yamb.YambMatch;

@Entity
@Table(name = "auth_user")
@RestResource(rel = "users", path = "users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Score> scores;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<ApiError> apiErrors;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Yamb> yambs;

    @Column(nullable = false, unique = true)
    @Size(min = TejkoGamesConstants.USERNAME_LENGTH_MIN, max = TejkoGamesConstants.USERNAME_LENGTH_MAX)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @JsonIncludeProperties({ "id", "label" })
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "auth_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @JsonIgnore
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<YambChallenge> challenges;

    @JsonIgnore
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<YambMatch> matches;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Preference preference;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public Set<Yamb> getYambs() {
        return yambs;
    }

    public void setYambs(Set<Yamb> yambs) {
        this.yambs = yambs;
    }

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
            string += role.getLabel() + ": " + role.getDescription() + "\n";
        }
        return string;
    }
}