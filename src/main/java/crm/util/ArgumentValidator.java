package crm.util;

public class ArgumentValidator {
    public static void validate(boolean condition, String errorMessage) {
        if (!condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
