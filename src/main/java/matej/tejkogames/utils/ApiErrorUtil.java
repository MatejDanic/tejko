package matej.tejkogames.utils;

import java.util.List;
import java.util.ArrayList;

import matej.tejkogames.constants.TejkoGamesConstants;

public class ApiErrorUtil {

    public static String constructErrorMessage(Throwable exception) {
        String message = "";
        message += recursiveExceptionMessage(exception, TejkoGamesConstants.RECURSION_LIMIT);
        return message;
    }

    public static List<String> constructErrorContent(Throwable exception) {
        List<String> content = new ArrayList<>();
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            if (stackTraceElement.getClassName().contains("matej")) {
                content.add("Class: '" + stackTraceElement.getClassName() + "', Method: '" + stackTraceElement.getMethodName() + "', Line Number: '" + stackTraceElement.getLineNumber() + "'");
            }
        }
        return content;
    }

    public static String recursiveExceptionMessage(Throwable exception, int counter) {
        String message = exception.getMessage();

        if (counter == 0 && exception.getCause() != null) {
            message += recursiveExceptionMessage(exception.getCause(), counter - 1);
        }
        
        return message;
    }

}
