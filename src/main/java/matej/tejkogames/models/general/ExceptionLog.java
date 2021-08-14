package matej.tejkogames.models.general;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class ExceptionLog {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private LocalDateTime time;

    @Column(nullable = false)
	private String content;

	public ExceptionLog(String content) {
		this.time = LocalDateTime.now();
		this.content = content;
	}
    
}
