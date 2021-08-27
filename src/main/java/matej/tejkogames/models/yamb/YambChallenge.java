package matej.tejkogames.models.yamb;

import java.time.LocalDateTime;
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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import matej.tejkogames.models.general.User;
import matej.tejkogames.models.general.Score;

@Entity
@Table(name = "yamb_challenge")
@RestResource(rel = "challenges", path = "challenges")
public class YambChallenge {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @JsonIncludeProperties({ "id", "username" })
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "yamb_challenge_user", joinColumns = @JoinColumn(name = "challenge_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    @JsonIncludeProperties({ "id", "value" })
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Score> scores;

    @JsonIncludeProperties({ "id" })
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Yamb> yambs;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private LocalDateTime date;

    public YambChallenge() {
    }

    public YambChallenge(Set<User> users, Set<Yamb> yambs) {
        this.users = users;
        this.yambs = yambs;
        this.active = true;
        this.date = LocalDateTime.now();
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}