package com.digivox.desafio.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
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

import com.digivox.desafio.model.Aluguel;
import com.digivox.desafio.model.AluguelRequest;
import com.digivox.desafio.model.Cliente;
import com.digivox.desafio.model.Livro;
import com.digivox.desafio.repositories.AluguelRepository;
import com.digivox.desafio.repositories.ClienteRepository;
import com.digivox.desafio.repositories.LivroRepository;

/**
 * Desafio Digivox - 19/10/2020
 * Candidato: Renan Costa
 * Arquivo: Controller para acões de Aluguel
 * 
 * Histórico de Alteraçôes:
 * - 20/10/2020 Renan Costa Criação
 */

@RestController
@RequestMapping(value= {"/aluguel"})
public class AluguelController {
	@Autowired
	LivroRepository livroRepo;
	@Autowired
	ClienteRepository clienteRepo;
	@Autowired
	AluguelRepository aluguelRepo;
	
	@GetMapping(value="/all")
	public ResponseEntity <List<Aluguel>> getAll(){
		HttpHeaders httpHeaders = new HttpHeaders();
		
		List<Aluguel> todosAlugueis =  aluguelRepo.findAll();
		
		return new ResponseEntity <List<Aluguel>>(todosAlugueis, httpHeaders, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity <?> getOne(@PathVariable("id") Long id){
		HttpHeaders httpHeaders = new HttpHeaders();
		Aluguel aluguel = aluguelRepo.findById(id);
		if(aluguel == null) {
			return new ResponseEntity <>("Aluguel não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity <Aluguel>(aluguel, httpHeaders, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity <?> postOne(@RequestBody AluguelRequest newAluguel){
		HttpHeaders httpHeaders = new HttpHeaders();
		
		if( newAluguel.getDataFinal()!=null && newAluguel.getClienteId()!=-1 && newAluguel.getLivroId()!=-1) {
			Cliente cliente = clienteRepo.findById(newAluguel.getClienteId());
			if (cliente==null) {
				return new ResponseEntity <>("Cliente não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
			}
			Livro livro = livroRepo.findById(newAluguel.getLivroId());
			if (livro == null) {
				return new ResponseEntity <>("Livro não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
			}
			try {
				
				Aluguel AluguelToDB = new Aluguel(
						LocalDate.of(newAluguel.getDataFinal()[0], newAluguel.getDataFinal()[1], newAluguel.getDataFinal()[2]),
						cliente,livro);
				aluguelRepo.save(AluguelToDB);
				return new ResponseEntity <>("Criado com sucesso", httpHeaders, HttpStatus.CREATED);
			} catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity <>(e.getMessage(), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			return new ResponseEntity <>("Informações incompletas", httpHeaders, HttpStatus.BAD_REQUEST);
		}	
	}
	@PutMapping
	public ResponseEntity <?> updateOne(@RequestBody AluguelRequest newAluguel){
		HttpHeaders httpHeaders = new HttpHeaders();
		
		if( newAluguel.getDataFinal()!=null && newAluguel.getClienteId()!=-1 && newAluguel.getLivroId()!=-1 && newAluguel.getId()!=null) {
			Aluguel existente = aluguelRepo.findById(newAluguel.getId());
			if(existente == null) {
				return new ResponseEntity <>("Aluguel não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
			}
			Cliente cliente = clienteRepo.findById(newAluguel.getClienteId());
			if (cliente==null) {
				return new ResponseEntity <>("Cliente não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
			}
			Livro livro = livroRepo.findById(newAluguel.getLivroId());
			if (livro == null) {
				return new ResponseEntity <>("Livro não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
			}
			try {
				Aluguel AluguelToDB = new Aluguel(newAluguel.getId(),
						existente.getData_inicial(),
						LocalDate.of(newAluguel.getDataFinal()[0], newAluguel.getDataFinal()[1], newAluguel.getDataFinal()[2]),
						cliente,livro);
				aluguelRepo.save(AluguelToDB);
				return new ResponseEntity <>("Atualizado com sucesso", httpHeaders, HttpStatus.CREATED);
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
		Aluguel doesExist = aluguelRepo.findById(id);
		if(doesExist == null) {
			return new ResponseEntity <>("Aluguel não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
		}
		aluguelRepo.deleteById(id);
		
		return new ResponseEntity <>("Aluguel deletado com sucesso", httpHeaders, HttpStatus.OK);
	}
	
	@GetMapping(value="/week")
	public ResponseEntity <?> getWeek(){
		HttpHeaders httpHeaders = new HttpHeaders();
		LocalDate hoje = LocalDate.now();
		List<Aluguel> alugueisSemanais = aluguelRepo.findByWeek(hoje.with(DayOfWeek.MONDAY), hoje.with(DayOfWeek.SUNDAY));
		return new ResponseEntity <List<Aluguel>>(alugueisSemanais, httpHeaders, HttpStatus.OK);
	}
}
