package com.io.rol.Image.exception;

import com.io.rol.common.exception.BaseException;
import com.io.rol.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileException extends BaseException {

    private final BaseExceptionType exceptionType;
    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
