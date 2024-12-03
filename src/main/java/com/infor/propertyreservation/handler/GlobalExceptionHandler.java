package com.infor.propertyreservation.handler;

import com.infor.propertyreservation.exception.PropertyNotFoundException;
import com.infor.propertyreservation.exception.PropertyUnavailableException;
import com.infor.propertyreservation.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiCallError<Map<String, String>>> handleMethodArgumentNotValidException(
			HttpServletRequest request, MethodArgumentNotValidException ex) {
		logError(request, ex);

		var details = new ArrayList<Map<String, String>>();
		ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
			Map<String, String> detail = new HashMap<>();
			detail.put("objectName", fieldError.getObjectName());
			detail.put("field", fieldError.getField());
			detail.put("rejectedValue", "" + fieldError.getRejectedValue());
			detail.put("errorMessage", fieldError.getDefaultMessage());
			details.add(detail);
		});

		return ResponseEntity.badRequest().body(new ApiCallError<>("METHOD_ARGS_VALIDATION_FAILED", details));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiCallError<String>> handleMethodArgumentTypeMismatchException(HttpServletRequest request,
			MethodArgumentTypeMismatchException ex) {
		logError(request, ex);
		return ResponseEntity.badRequest()
			.body(new ApiCallError<>("TYPE_MISMATCH",
					List.of("Parameter '" + ex.getName() + "' should be of type '" + ex.getRequiredType() + "'")));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiCallError<String>> handleConstraintViolationException(HttpServletRequest request,
			ConstraintViolationException ex) {
		logError(request, ex);
		List<String> violations = ex.getConstraintViolations()
			.stream()
			.map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
			.collect(Collectors.toList());
		return ResponseEntity.badRequest().body(new ApiCallError<>("CONSTRAINT_VIOLATIONS", violations));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiCallError<String>> handleGenericException(HttpServletRequest request, Exception ex) {
		logError(request, ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(new ApiCallError<>("UNEXPECTED_ERROR_OCCURRED", List.of(ex.getMessage())));
	}

	@ExceptionHandler(PropertyNotFoundException.class)
	public ResponseEntity<ApiCallError<String>> handlePropertyNotFoundException(HttpServletRequest request,
			PropertyNotFoundException ex) {
		logError(request, ex);
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(new ApiCallError<>("PROPERTY_NOT_FOUND", List.of(ex.getMessage())));
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiCallError<String>> handlePropertyNotFoundException(HttpServletRequest request,
			UserNotFoundException ex) {
		logError(request, ex);
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(new ApiCallError<>("USER_NOT_FOUND", List.of(ex.getMessage())));
	}

	@ExceptionHandler(PropertyUnavailableException.class)
	public ResponseEntity<ApiCallError<String>> handlePropertyNotFoundException(HttpServletRequest request,
			PropertyUnavailableException ex) {
		logError(request, ex);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ApiCallError<>("PROPERTY_UNAVAILABLE", List.of(ex.getMessage())));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiCallError<String>> handleHttpMessageNotReadableException(HttpServletRequest request,
			HttpMessageNotReadableException ex) {
		logError(request, ex);

		return ResponseEntity.badRequest()
			.body(new ApiCallError<>("MALFORMED_JSON", List.of("Invalid request payload")));
	}

	private void logError(HttpServletRequest request, Exception ex) {
		log.error("Exception at [{}]: {}", request.getRequestURI(), ex.getMessage(), ex);
	}

	@AllArgsConstructor
	@Getter
	@Setter
	public static class ApiCallError<T> {

		private String message;

		private List<T> details;

	}

}
