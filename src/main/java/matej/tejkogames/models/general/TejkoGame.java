package matej.tejkogames.models.general;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@Table(name="game")
@RestResource(rel = "games", path = "games")
public class TejkoGame {

    @Id
    private int id;

    @JsonIgnoreProperties("score")
    @OneToMany(mappedBy = "score", fetch = FetchType.LAZY)
    private List<Score> scores;

    @Column(nullable = false, unique = true)
	private String name;

	@Column
    private String description;

    public TejkoGame() {}

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

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
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
