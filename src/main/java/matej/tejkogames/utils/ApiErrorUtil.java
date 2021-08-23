package matej.tejkogames.utils;

public class ApiErrorUtil {

    public static String constructApiErrorContent(Throwable exception, int counter) {
        String apiErrorContent = exception.getMessage();

        if (counter == 0 && exception.getCause() != null) {
            apiErrorContent += constructApiErrorContent(exception.getCause(), counter - 1);
        }
        
        return apiErrorContent;
    }

}
