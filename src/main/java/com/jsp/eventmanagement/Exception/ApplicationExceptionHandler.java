package com.jsp.eventmanagement.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jsp.eventmanagement.Response.ResponseStructure;
import com.jsp.eventmanagement.model.Event;

@RestControllerAdvice
public class ApplicationExceptionHandler {
	
	@ExceptionHandler(value = UnableToSaveEventException.class)
	public ResponseEntity<ResponseStructure<String>>
		handleUnableToSaveEventException(UnableToSaveEventException exception){
		
		ResponseStructure<String> response = new ResponseStructure<String>();
		response.setStatusCode(HttpStatus.NO_CONTENT.value());
		response.setMessage("Event Failed to save");
		response.setData(exception.getMessage());

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(value = EventNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>>
		handleEventNotFoundException(EventNotFoundException exception){
		
		ResponseStructure<String> response = new ResponseStructure<String>();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage("Event failed to find");
		response.setData(exception.getMessage());

		return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.NOT_FOUND);
	}
	
	

}
