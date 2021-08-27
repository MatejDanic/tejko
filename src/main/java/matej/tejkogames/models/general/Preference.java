package matej.tejkogames.models.general;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.rest.core.annotation.RestResource;

import matej.tejkogames.models.general.enums.Theme;

@Entity
@Table(name = "user_preference")
@RestResource(rel = "preferences", path = "preferences")
public class Preference {

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

	@OneToOne
    @JsonIgnore
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

    @Column()
    private int volume;

    @Column()
    private Theme theme;

    public Preference(int volume, Theme theme) {
        this.volume = volume;
        this.theme = theme;
    }

    public Preference() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

}