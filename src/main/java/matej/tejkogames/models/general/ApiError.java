package matej.tejkogames.models.general;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.rest.core.annotation.RestResource;

import matej.tejkogames.utils.ApiErrorUtil;

@Entity
@Table(name = "api_error")
@RestResource(rel = "errors", path = "errors")
@TypeDef(name = "json_binary", typeClass = JsonBinaryType.class)
public class ApiError {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String message;

    @Type(type = "json_binary")
    @Column(columnDefinition = "jsonb")
    private List<String> content;

    public ApiError() {}

    public ApiError(Throwable exception) {
        this.timestamp = LocalDateTime.now();    
        this.message = ApiErrorUtil.constructErrorMessage(exception);   
        this.content = ApiErrorUtil.constructErrorContent(exception);

    }

    public ApiError(User user, Throwable exception) {
        this.user = user;
        this.timestamp = LocalDateTime.now();
        this.message = ApiErrorUtil.constructErrorMessage(exception);
        this.content = ApiErrorUtil.constructErrorContent(exception);
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    
}