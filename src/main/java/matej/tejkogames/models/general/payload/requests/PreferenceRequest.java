package matej.tejkogames.models.general.payload.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import matej.tejkogames.models.general.enums.Theme;

public class PreferenceRequest {

    @Max(3)
    @Min(0)
	private Integer volume;

    private Theme theme;

    protected PreferenceRequest() { }

    public Integer getVolume() {
        return volume;
    }
    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

}