package com.ianxc.vtradereporter.controller;

import com.ianxc.vtradereporter.service.filter.UnknownTradeFilterException;
import com.ianxc.vtradereporter.service.submit.XmlExtractorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnknownTradeFilterException.class)
    public ProblemDetail handle(UnknownTradeFilterException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(XmlExtractorException.class)
    public ProblemDetail handle(XmlExtractorException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Failed to parse XML data. Check that the contents conform to the required schema.");
    }
}
