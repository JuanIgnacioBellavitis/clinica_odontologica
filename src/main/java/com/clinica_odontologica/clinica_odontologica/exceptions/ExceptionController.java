package com.clinica_odontologica.clinica_odontologica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.clinica_odontologica.clinica_odontologica.dto.ExceptionDTO;

@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> notFound(NotFoundException e) {
		ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage());
		return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> badRequest(BadRequestException e) {
		ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage());
		return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
	}
}
