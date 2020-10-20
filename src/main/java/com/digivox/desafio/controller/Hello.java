package com.digivox.desafio.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value= {"/"})
public class Hello {
	@GetMapping
	public ResponseEntity <?> HelloWorld(){
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>("Hello World!", httpHeaders, HttpStatus.OK);
	}
}
