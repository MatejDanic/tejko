package matej.tejkogames.utils;

public class ApiErrorUtil {

    public static String constructApiErrorContent(Throwable exception) {
        String apiErrorContent = exception.getMessage();
        if (exception.getCause() != null) {
            apiErrorContent += constructApiErrorContent(exception.getCause());
        }
        return apiErrorContent;
    }

}
