package org.skypro.exam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QuestionsAreNotEnoughException extends RuntimeException {
    public QuestionsAreNotEnoughException(String message) {
        super(message);
    }
}

