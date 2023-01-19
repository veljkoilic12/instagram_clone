package com.veljkoilic.instagramclone.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.http.HttpHeaders;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorMessage> validationException(BadRequestException ex, WebRequest request) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.BAD_REQUEST.value()).timestamp(new Date())
				.message(ex.getMessage()).description(request.getDescription(false)).build();

		return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorMessage> userNotFoundException(NotFoundException ex, WebRequest request) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.NOT_FOUND.value()).timestamp(new Date())
				.message(ex.getMessage()).description(request.getDescription(false)).build();

		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorMessage> unauthorizedException(UnauthorizedException ex, WebRequest request) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.UNAUTHORIZED.value()).timestamp(new Date())
				.message(ex.getMessage()).description(request.getDescription(false)).build();

		return new ResponseEntity<ErrorMessage>(message, HttpStatus.UNAUTHORIZED);
	}

	@Override
	@Nullable
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDate.now());
		body.put("status", status.value());

		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
}
