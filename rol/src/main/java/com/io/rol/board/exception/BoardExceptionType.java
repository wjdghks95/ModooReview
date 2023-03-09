package com.io.rol.board.exception;

import com.io.rol.common.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum BoardExceptionType implements BaseExceptionType {

    BOARD_NOT_FOUND(700, HttpStatus.NOT_FOUND, "찾으시는 게시글이 없습니다.");

    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    BoardExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
