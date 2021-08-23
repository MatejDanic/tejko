package matej.tejkogames.models.general;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import matej.tejkogames.models.general.enums.TejkoGame;

@Entity
@Table(name="game_score")
@RestResource(rel = "scores", path = "scores")
public class Score {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

	@ManyToOne
    // @JsonIgnoreProperties({"scores", "yamb"})
	@JsonIncludeProperties({"username"})
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private TejkoGame game;
	
	@Column(nullable = false)
	private int value;
	
	@Column(nullable = false)
	private LocalDateTime date;
	
	public Score() {}

	public Score (TejkoGame game, int value) {
		this.value = value;
		this.date = LocalDateTime.now();
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}