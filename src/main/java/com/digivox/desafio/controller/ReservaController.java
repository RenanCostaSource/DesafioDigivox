package com.digivox.desafio.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.digivox.desafio.model.Cliente;
import com.digivox.desafio.model.Livro;
import com.digivox.desafio.model.Reserva;
import com.digivox.desafio.model.ReservaRequest;
import com.digivox.desafio.repositories.ClienteRepository;
import com.digivox.desafio.repositories.LivroRepository;
import com.digivox.desafio.repositories.ReservaRepository;

/**
 * Desafio Digivox - 19/10/2020
 * Candidato: Renan Costa
 * Arquivo: Controller para acões de Reserva
 * 
 * Histórico de Alteraçôes:
 * - 20/10/2020 Renan Costa Criação
 */

@RestController
@RequestMapping(value= {"/reserva"})
public class ReservaController {
	@Autowired
	LivroRepository livroRepo;
	@Autowired
	ClienteRepository clienteRepo;
	@Autowired
	ReservaRepository reservaRepo;
	
	@GetMapping(value="/all")
	public ResponseEntity <List<Reserva>> getAll(){
		HttpHeaders httpHeaders = new HttpHeaders();
		
		List<Reserva> todasReservas =  reservaRepo.findAll();
		
		return new ResponseEntity <List<Reserva>>(todasReservas, httpHeaders, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity <?> getOne(@PathVariable("id") Long id){
		HttpHeaders httpHeaders = new HttpHeaders();
		Reserva reserva = reservaRepo.findById(id);
		if(reserva == null) {
			return new ResponseEntity <>("Reserva não cadastrada", httpHeaders, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity <Reserva>(reserva, httpHeaders, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity <?> postOne(@RequestBody ReservaRequest newReserva){
		HttpHeaders httpHeaders = new HttpHeaders();
		
		if(newReserva.getDataInicial()!=null && newReserva.getDataFinal()!=null && newReserva.getClienteId()!=-1 && newReserva.getLivroId()!=-1) {
			Cliente cliente = clienteRepo.findById(newReserva.getClienteId());
			if (cliente==null) {
				return new ResponseEntity <>("Cliente não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
			}
			Livro livro = livroRepo.findById(newReserva.getLivroId());
			if (livro == null) {
				return new ResponseEntity <>("Livro não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
			}
			try {
				
				Reserva ReservaToDB = new Reserva(
						LocalDate.of(newReserva.getDataInicial()[0], newReserva.getDataInicial()[1], newReserva.getDataInicial()[2]),
						LocalDate.of(newReserva.getDataFinal()[0], newReserva.getDataFinal()[1], newReserva.getDataFinal()[2]),
						cliente,livro);
				reservaRepo.save(ReservaToDB);
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
	public ResponseEntity <?> updateOne(@RequestBody ReservaRequest newReserva){
		HttpHeaders httpHeaders = new HttpHeaders();
		
		if(newReserva.getDataInicial()!=null && newReserva.getDataFinal()!=null && newReserva.getClienteId()!=-1 && newReserva.getLivroId()!=-1 && newReserva.getId()!=null) {
			Reserva doesExist = reservaRepo.findById(newReserva.getId());
			if(doesExist == null) {
				return new ResponseEntity <>("Reserva não cadastrada", httpHeaders, HttpStatus.NOT_FOUND);
			}
			Cliente cliente = clienteRepo.findById(newReserva.getClienteId());
			if (cliente==null) {
				return new ResponseEntity <>("Cliente não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
			}
			Livro livro = livroRepo.findById(newReserva.getLivroId());
			if (livro == null) {
				return new ResponseEntity <>("Livro não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
			}
			try {
				Reserva ReservaToDB = new Reserva(
						LocalDate.of(newReserva.getDataInicial()[0], newReserva.getDataInicial()[1], newReserva.getDataInicial()[2]),
						LocalDate.of(newReserva.getDataFinal()[0], newReserva.getDataFinal()[1], newReserva.getDataFinal()[2]),
						cliente,livro);
				reservaRepo.save(ReservaToDB);
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
		Reserva doesExist = reservaRepo.findById(id);
		if(doesExist == null) {
			return new ResponseEntity <>("Reserva não cadastrada", httpHeaders, HttpStatus.NOT_FOUND);
		}
		reservaRepo.deleteById(id);
		
		return new ResponseEntity <>("Reserva deletada com sucesso", httpHeaders, HttpStatus.OK);
	}
}
