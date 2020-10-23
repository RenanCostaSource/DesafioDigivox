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
import com.digivox.desafio.model.Reserva;
import com.digivox.desafio.repositories.AluguelRepository;
import com.digivox.desafio.repositories.ClienteRepository;
import com.digivox.desafio.repositories.LivroRepository;
import com.digivox.desafio.repositories.ReservaRepository;

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
	@Autowired
	ReservaRepository reservaRepo;
	
	@GetMapping
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
			return new ResponseEntity <String>("Aluguel não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity <Aluguel>(aluguel, httpHeaders, HttpStatus.OK);
	}
	@Transactional
	@PostMapping
	public ResponseEntity <?> postOne(@RequestBody AluguelRequest newAluguel){
		HttpHeaders httpHeaders = new HttpHeaders();
		LocalDate datainicial = LocalDate.now();
		LocalDate datafinal = LocalDate.parse(newAluguel.getDataFinal());
		if(datafinal.compareTo(LocalDate.now())<=0) {
			return new ResponseEntity <String>("Data de entrega precisa ser ao menos um dia após hoje.", httpHeaders, HttpStatus.BAD_REQUEST);
		}
		
		if( newAluguel.getDataFinal()!=null && newAluguel.getClienteId()!=-1 && newAluguel.getLivroId()!=-1) {
			Cliente cliente = clienteRepo.findById(newAluguel.getClienteId());
			if (cliente==null) {
				return new ResponseEntity <String>("Cliente não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
			}
			Livro livro = livroRepo.findById(newAluguel.getLivroId());
			if (livro == null) {
				return new ResponseEntity <String>("Livro não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
			}
			int alugado = aluguelRepo.findIfBookRented(datainicial, datafinal, newAluguel.getLivroId());
			if(alugado!=0) {
				return new ResponseEntity <String>("Livro já alugado", httpHeaders, HttpStatus.CONFLICT);
			}
			Reserva reservado = reservaRepo.findIfBookReserved(datainicial, datafinal, newAluguel.getLivroId());
			if(reservado!=null) {
				
				Reserva doCliente = reservaRepo.findIfBookReservedByClient(datainicial, datafinal, newAluguel.getLivroId(),newAluguel.getClienteId());
				if(doCliente == null || reservado.getData_inicial().compareTo(LocalDate.now())<=-1) {//cliente tem 1 dia para fazer aluguel da reserva
					return new ResponseEntity <String>("Livro Reservado a Outro Cliente", httpHeaders, HttpStatus.CONFLICT);
				}else {
					if(reservado.getData_inicial().compareTo(LocalDate.now())>=-2) {
						reservaRepo.deleteById(reservado.getId());
					}
					reservaRepo.deleteById(doCliente.getId());
				}
			}
			try {
				
				Aluguel AluguelToDB = new Aluguel(
						LocalDate.parse(newAluguel.getDataFinal()),
						cliente,livro);
				aluguelRepo.save(AluguelToDB);
				return new ResponseEntity <String>("Criado com sucesso", httpHeaders, HttpStatus.CREATED);
			} catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity <String>(e.getMessage(), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			return new ResponseEntity <String>("Informações incompletas", httpHeaders, HttpStatus.BAD_REQUEST);
		}	
	}
	@PutMapping(value="/{id}")
	public ResponseEntity <?> updateOne(@PathVariable("id") Long id, @RequestBody AluguelRequest newAluguel){
		HttpHeaders httpHeaders = new HttpHeaders();
		
		if( newAluguel.getDataFinal()!=null && newAluguel.getClienteId()!=-1 && newAluguel.getLivroId()!=-1 && id!=null) {
			Aluguel existente = aluguelRepo.findById(id);
			if(existente == null) {
				return new ResponseEntity <String>("Aluguel não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
			}
			Cliente cliente = clienteRepo.findById(newAluguel.getClienteId());
			if (cliente==null) {
				return new ResponseEntity <String>("Cliente não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
			}
			Livro livro = livroRepo.findById(newAluguel.getLivroId());
			if (livro == null) {
				return new ResponseEntity <String>("Livro não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
			}
			try {
				Aluguel AluguelToDB = new Aluguel(id,
						existente.getData_inicial(),
						LocalDate.parse(newAluguel.getDataFinal()),
						cliente,livro);
				aluguelRepo.save(AluguelToDB);
				return new ResponseEntity <String>("Atualizado com sucesso", httpHeaders, HttpStatus.CREATED);
			} catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity <String>(e.getMessage(), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			return new ResponseEntity <String>("Informações incompletas", httpHeaders, HttpStatus.BAD_REQUEST);
		}
	}
	@Transactional
	@DeleteMapping(value="/{id}")
	public ResponseEntity <?> deleteOne(@PathVariable("id") Long id){
		HttpHeaders httpHeaders = new HttpHeaders();
		Aluguel doesExist = aluguelRepo.findById(id);
		if(doesExist == null) {
			return new ResponseEntity <String>("Aluguel não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
		}
		aluguelRepo.deleteById(id);
		
		return new ResponseEntity <String>("Aluguel deletado com sucesso", httpHeaders, HttpStatus.OK);
	}
	
	@GetMapping(value="/return/{id}")
	public ResponseEntity <?> returnOne(@PathVariable("id") Long id){
		HttpHeaders httpHeaders = new HttpHeaders();
		Aluguel returned = aluguelRepo.findById(id);
		if(returned == null) {
			return new ResponseEntity <String>("Aluguel não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
		}
		returned.setDevolvido(LocalDate.now());
		aluguelRepo.save(returned);
		return new ResponseEntity <String>("Aluguel devolvido com sucesso", httpHeaders, HttpStatus.OK);
	}
	
	@GetMapping(value="/rentweek/{data}")
	public ResponseEntity <?> rentWeek(@PathVariable("data") String data){
		HttpHeaders httpHeaders = new HttpHeaders();
		LocalDate hoje = LocalDate.parse(data);
		List<Aluguel> alugueisSemanais = aluguelRepo.findByRentWeek(hoje.with(DayOfWeek.MONDAY), hoje.with(DayOfWeek.SUNDAY));
		return new ResponseEntity <List<Aluguel>>(alugueisSemanais, httpHeaders, HttpStatus.OK);
	}
	@GetMapping(value="/returnweek/{data}")
	public ResponseEntity <?> returnWeek(@PathVariable("data") String data){
		HttpHeaders httpHeaders = new HttpHeaders();
		LocalDate hoje = LocalDate.parse(data);
		List<Aluguel> alugueisSemanais = aluguelRepo.findByReturnWeek(hoje.with(DayOfWeek.MONDAY), hoje.with(DayOfWeek.SUNDAY));
		return new ResponseEntity <List<Aluguel>>(alugueisSemanais, httpHeaders, HttpStatus.OK);
	}
	
	@GetMapping(value="/week/{data}")
	public ResponseEntity <?> getWeek(@PathVariable("data") String data){
		HttpHeaders httpHeaders = new HttpHeaders();
		LocalDate hoje = LocalDate.parse(data);
		List<Aluguel> alugueisSemanais = aluguelRepo.findByWeek(hoje.with(DayOfWeek.MONDAY), hoje.with(DayOfWeek.SUNDAY));
		return new ResponseEntity <List<Aluguel>>(alugueisSemanais, httpHeaders, HttpStatus.OK);
	}
}
