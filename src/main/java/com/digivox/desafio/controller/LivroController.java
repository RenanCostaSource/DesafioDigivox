package com.digivox.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.dao.DataIntegrityViolationException;
import com.digivox.desafio.model.Livro;
import com.digivox.desafio.repositories.LivroRepository;

/**
 * Desafio Digivox - 19/10/2020
 * Candidato: Renan Costa
 * Arquivo: Controller para CRUD  de livros
 * 
 * Histórico de Alteraçôes:
 * - 19/10/2020 Renan Costa Criação
 */

@RestController
@RequestMapping(value= {"/livro"})
public class LivroController {
	@Autowired
	LivroRepository livroRepo;
	
	@GetMapping(value="/all")
	public ResponseEntity <List<Livro>> getAll(){
		HttpHeaders httpHeaders = new HttpHeaders();
		
		List<Livro> todosLivros =  livroRepo.findAll();
		
		return new ResponseEntity <List<Livro>>(todosLivros, httpHeaders, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity <?> getOne(@PathVariable("id") Long id){
		HttpHeaders httpHeaders = new HttpHeaders();
		Livro livro = livroRepo.findById(id);
		if(livro == null) {
			return new ResponseEntity <>("Livro não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity <Livro>(livro, httpHeaders, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity <?> postOne(@RequestBody Livro newLivro){
		HttpHeaders httpHeaders = new HttpHeaders();
		if(newLivro.getId()!=null) {
			return new ResponseEntity <>("Atributo \"id\" não permitido", httpHeaders, HttpStatus.BAD_REQUEST);
		}
		if(newLivro.getNome()!=null && newLivro.getNome()!="") {
		
			try {
				livroRepo.save(newLivro);
				return new ResponseEntity <>("Criado com sucesso", httpHeaders, HttpStatus.CREATED);
			} catch(DataIntegrityViolationException e){
				return new ResponseEntity <>("Livro já Cadastrado", httpHeaders, HttpStatus.CONFLICT);
			} catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity <>(e.getMessage(), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			return new ResponseEntity <>("Informações incompletas", httpHeaders, HttpStatus.BAD_REQUEST);
		}	
	}
	
	@PutMapping
	public ResponseEntity <?> updateOne(@RequestBody Livro newLivro){
		HttpHeaders httpHeaders = new HttpHeaders();
		
		if(newLivro.getNome()!=null && newLivro.getNome()!="" && newLivro.getId()!=null) {
			Livro doesExist = livroRepo.findById(newLivro.getId());
			if(doesExist == null)return new ResponseEntity <>("Livro não existente", httpHeaders, HttpStatus.NOT_FOUND);
			try {
				livroRepo.save(newLivro);
				return new ResponseEntity <>("Atualizado com sucesso", httpHeaders, HttpStatus.CREATED);
			} catch(DataIntegrityViolationException e){
				return new ResponseEntity <>("Nome do livro já cadastrado", httpHeaders, HttpStatus.CONFLICT);
			} catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity <>(e.getMessage(), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			return new ResponseEntity <>("Informações incompletas", httpHeaders, HttpStatus.BAD_REQUEST);
		}
	}
	@Transactional
	@DeleteMapping(value="/{id}")
	public ResponseEntity <?> deleteOne(@PathVariable("id") Long id){
		HttpHeaders httpHeaders = new HttpHeaders();
		Livro doesExist = livroRepo.findById(id);
		if(doesExist == null) {
			return new ResponseEntity <>("Livro não existente", httpHeaders, HttpStatus.NOT_FOUND);
		}
		livroRepo.deleteById(id);
		
		return new ResponseEntity <>("Livro deletado com sucesso", httpHeaders, HttpStatus.OK);
	}
}
