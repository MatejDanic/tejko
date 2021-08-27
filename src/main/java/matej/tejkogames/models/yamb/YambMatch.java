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
@Table(name = "yamb_match")
@RestResource(rel = "matches", path = "matches")
public class YambMatch {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @JsonIncludeProperties({ "id", "value" })
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Score> scores;

    @JsonIncludeProperties({ "id", "username" })
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "yamb_match_user", joinColumns = @JoinColumn(name = "match_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = true)
    private boolean finished;

    public YambMatch() {
    }

    public YambMatch(Set<User> users) {
        this.users = users;
        this.finished = false;
        this.date = LocalDateTime.now();
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

}
