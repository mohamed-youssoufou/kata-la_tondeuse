package fr.harington.mowitnow.exceptions;

public class InvalidFileFormatException extends RuntimeException {
    public InvalidFileFormatException(final String message) {
        super(message);
    }
}
