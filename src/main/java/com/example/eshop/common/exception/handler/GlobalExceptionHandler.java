package com.example.eshop.common.exception.handler;

import com.example.eshop.common.dto.ExceptionResult;
import com.example.eshop.common.type.ResultType;
import com.example.eshop.common.exception.BaseException;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // DB: ?????? ?????????
    @ExceptionHandler({DuplicateKeyException.class})
    public ExceptionResult handleDBDuplicateError(DuplicateKeyException ex) {
        log.error("handleDBDuplicateError ex :::", ex);

        return new ExceptionResult(ResultType.DATA_ALREADY_EXIST);
    }

    // DB: not-null ?????? ????????? ??????
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ExceptionResult handleDBIntegrityError(DataIntegrityViolationException ex) {
        log.error("handleDBIntegrityError ex :::", ex);

        return new ExceptionResult(ResultType.NOT_ALLOWED_OPERATION);
    }

    // ????????? ????????? ????????? ????????? ??? ??? jwt ?????? ?????? ???????????? ????????? ??????
    @ExceptionHandler({JwtException.class})
    public ExceptionResult handleJwtException(JwtException ex) {
        log.error("handleJwtException ex :::", ex);

        return new ExceptionResult(ResultType.INVALID_TOKEN);
    }

    @ExceptionHandler({BaseException.class})
    public ExceptionResult handleBaseException(BaseException ex) {
        log.error("handleBaseException ex :::", ex);

        return new ExceptionResult(ex.getResultType());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers, HttpStatus status,
                                                                   WebRequest request) {
        log.error("handleNoHandlerFoundException ex :::", ex);

        return new ResponseEntity<>(new ExceptionResult(ResultType.PAGE_NOT_FOUND),
                ResultType.PAGE_NOT_FOUND.getStatus());
    }

    // http method ????????? ??????
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status,
                                                                         WebRequest request) {
        log.error("handleHttpRequestMethodNotSupported ex :::", ex);

        return new ResponseEntity<>(new ExceptionResult(ResultType.METHOD_NOT_ALLOWED),
                ResultType.METHOD_NOT_ALLOWED.getStatus());
    }

    // ?????? ???????????? ?????? ?????? ???(???????????? ??? ???????????? ???????????? ??????)
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request) {
        log.error("handleMissingServletRequestParameter ex :::", ex);

        return new ResponseEntity<>(new ExceptionResult(ResultType.MISSING_PARAMETER),
                ResultType.MISSING_PARAMETER.getStatus());
    }

    // validation ?????? ???????????? ?????? ??????
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.error("handleMethodArgumentNotValid ex :::", ex);

        return new ResponseEntity<>(new ExceptionResult(ResultType.INVALID_PARAMETER),
                ResultType.INVALID_PARAMETER.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers, HttpStatus status,
                                                                     WebRequest request) {
        log.error("handleHttpMediaTypeNotSupported ex :::", ex);

        return new ResponseEntity<>(new ExceptionResult(ResultType.UNSUPPORTED_MEDIA_TYPE),
                ResultType.UNSUPPORTED_MEDIA_TYPE.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatus status,
                                                                      WebRequest request) {
        log.error("handleHttpMediaTypeNotAcceptable ex :::", ex);

        return new ResponseEntity<>(new ExceptionResult(ResultType.UNSUPPORTED_MEDIA_TYPE),
                ResultType.UNSUPPORTED_MEDIA_TYPE.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
                                                               HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {
        log.error("handleMissingPathVariable ex :::", ex);

        return new ResponseEntity<>(new ExceptionResult(ResultType.MISSING_PARAMETER),
                ResultType.MISSING_PARAMETER.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request) {
        log.error("handleServletRequestBindingException ex :::", ex);

        return new ResponseEntity<>(new ExceptionResult(ResultType.INVALID_PARAMETER),
                ResultType.INVALID_PARAMETER.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.error("handleConversionNotSupported ex :::", ex);

        return new ResponseEntity<>(new ExceptionResult(ResultType.NOT_ALLOWED_OPERATION),
                ResultType.NOT_ALLOWED_OPERATION.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers, HttpStatus status,
                                                        WebRequest request) {
        log.error("handleTypeMismatch ex :::", ex);

        return new ResponseEntity<>(new ExceptionResult(ResultType.INVALID_PARAMETER),
                ResultType.INVALID_PARAMETER.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.error("handleHttpMessageNotReadable ex :::", ex);

        return new ResponseEntity<>(new ExceptionResult(ResultType.INVALID_PARAMETER),
                ResultType.INVALID_PARAMETER.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.error("handleHttpMessageNotWritable ex :::", ex);

        return new ResponseEntity<>(new ExceptionResult(ResultType.NOT_ALLOWED_OPERATION),
                ResultType.NOT_ALLOWED_OPERATION.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                     HttpHeaders headers, HttpStatus status,
                                                                     WebRequest request) {
        log.error("handleMissingServletRequestPart ex :::", ex);

        return new ResponseEntity<>(new ExceptionResult(ResultType.INVALID_PARAMETER),
                ResultType.INVALID_PARAMETER.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex,
                                                         HttpHeaders headers, HttpStatus status,
                                                         WebRequest request) {
        log.error("handleBindException ex :::", ex);

        return new ResponseEntity<>(new ExceptionResult(ResultType.INVALID_PARAMETER),
                ResultType.INVALID_PARAMETER.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
                                                                        HttpHeaders headers, HttpStatus status,
                                                                        WebRequest webRequest) {
        log.error("handleAsyncRequestTimeoutException ex :::", ex);

        return new ResponseEntity<>(new ExceptionResult(ResultType.SERVICE_UNAVAILABLE),
                ResultType.SERVICE_UNAVAILABLE.getStatus());
    }
}
