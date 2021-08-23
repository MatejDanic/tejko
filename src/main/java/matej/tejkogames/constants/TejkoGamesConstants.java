package matej.tejkogames.constants;

import matej.tejkogames.models.general.enums.Theme;

public class TejkoGamesConstants {

    public static final int USERNAME_LENGTH_MIN = 3;
    public static final int USERNAME_LENGTH_MAX = 15;

    public static final String ORIGIN_DEFAULT = "https://tejko.games";
    public static final String ORIGIN_WWW = "https://www.tejko.games";
    public static final String ORIGIN_HEROKU = "https://tejko-games.herokuapp.com";

    public static final int EXCEPTION_LOG_SIZE = 1024;
    public static final int RECURSION_LIMIT = 5;

    public static final int DEFAULT_VOLUME = 1;
    public static final int VOLUME_MIN = 0;
    public static final int VOLUME_MAX = 3;

    public static final Theme DEFAULT_THEME = Theme.LIGHT;

}
