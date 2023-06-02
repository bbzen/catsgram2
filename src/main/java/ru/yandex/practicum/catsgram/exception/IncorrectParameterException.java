package ru.yandex.practicum.catsgram.exception;

public class IncorrectParameterException extends RuntimeException {
    private String parameter;

    public IncorrectParameterException(String message, String parameter) {
        super(message);
        this.parameter = parameter;
    }
}
