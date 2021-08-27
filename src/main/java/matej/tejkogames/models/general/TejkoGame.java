package matej.tejkogames.models.general;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@Table(name = "game")
@RestResource(rel = "games", path = "games")
public class TejkoGame {

    @Id
    private int id;

    @JsonIgnore
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
    private Set<Score> scores;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    public TejkoGame() {
    }

    public TejkoGame(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
