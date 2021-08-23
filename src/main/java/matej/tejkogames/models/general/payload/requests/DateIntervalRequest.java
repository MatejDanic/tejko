package matej.tejkogames.models.general.payload.requests;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

public class DateIntervalRequest {
    
	@NotBlank
	private LocalDateTime start;

	@NotBlank
	private LocalDateTime end;

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    
	
}