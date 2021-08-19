package matej.tejkogames.utils;

public class ExceptionLogUtil {

    public static String constructExceptionLogMessage(Throwable exception) {
        String exceptionLogMessage = exception.getMessage();
        if (exception.getCause() != null) {
            exceptionLogMessage += constructExceptionLogMessage(exception.getCause());
        }
        return exceptionLogMessage;
    }
    
}
