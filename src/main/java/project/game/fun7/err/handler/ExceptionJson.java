package project.game.fun7.err.handler;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ExceptionJson {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime timestamp;
    @JsonFormat
    private String message;

    private ExceptionJson() {
        this.timestamp = LocalDateTime.now();
    }

    public ExceptionJson(String message) {
        this();
        this.message = message;
    }
}