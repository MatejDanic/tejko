package matej.tejkogames.models.payload.requests;

import matej.tejkogames.models.enums.Theme;

public class PreferenceRequest {

	private Integer volume;

    private Theme theme;

    protected PreferenceRequest() { }

    public Integer getVolume() {
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