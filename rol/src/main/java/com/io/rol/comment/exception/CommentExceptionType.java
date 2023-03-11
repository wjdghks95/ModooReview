package com.io.rol.comment.exception;

import com.io.rol.common.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum CommentExceptionType implements BaseExceptionType {

    COMMENT_NOT_FOUND(700, HttpStatus.NOT_FOUND, "찾으시는 댓글이 없습니다.");

    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    CommentExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return 0;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return null;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }
}
