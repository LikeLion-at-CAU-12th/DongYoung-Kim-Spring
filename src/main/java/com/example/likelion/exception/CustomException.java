package com.example.likelion.exception;

public class CustomException extends RuntimeException {
    public CustomException() {
        super("에러가 발생했습니다.");
    }

    public CustomException(String message) {
        super("에러가 발생했습니다. 에러 메시지 : " + message);
    }
}
