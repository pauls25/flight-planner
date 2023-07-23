package io.codelex.flightplanner.error;

public class BadDateTimeException extends RuntimeException {
    public BadDateTimeException(String s) {
        super(s);
    }
}
