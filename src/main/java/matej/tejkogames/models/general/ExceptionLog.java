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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.GenericGenerator;

import matej.tejkogames.constants.TejkoGamesConstants;

@Entity
@Table
public class ExceptionLog {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

	@Column(nullable = false)
	private LocalDateTime time;
    
	@ManyToOne
    @JsonIgnoreProperties({"exceptions", "yamb"})
	@JoinColumn(name = "user_id")
	private User user;

    @Column(nullable = false, length = TejkoGamesConstants.EXCEPTION_LOG_SIZE)
	private String content;
    
	public ExceptionLog(String content) {
		this.time = LocalDateTime.now();
		this.content = content;
	}
    
	public ExceptionLog(User user, String content) {
        this.user = user;
		this.time = LocalDateTime.now();
		this.content = content;
	}

    public ExceptionLog() { }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    
    
}
