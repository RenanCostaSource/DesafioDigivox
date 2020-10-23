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

import com.digivox.desafio.model.Cliente;
import com.digivox.desafio.model.Livro;
import com.digivox.desafio.model.Reserva;
import com.digivox.desafio.model.ReservaCancelada;
import com.digivox.desafio.model.ReservaRequest;
import com.digivox.desafio.repositories.AluguelRepository;
import com.digivox.desafio.repositories.ClienteRepository;
import com.digivox.desafio.repositories.LivroRepository;
import com.digivox.desafio.repositories.ReservaCanceladaRepository;
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
	@Autowired
	AluguelRepository aluguelRepo;
	@Autowired
	ReservaCanceladaRepository reservaCanceladaRepo;
	@GetMapping
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
			return new ResponseEntity <String>("Reserva não cadastrada", httpHeaders, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity <Reserva>(reserva, httpHeaders, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity <?> postOne(@RequestBody ReservaRequest newReserva){
		HttpHeaders httpHeaders = new HttpHeaders();
		LocalDate datainicial =LocalDate.parse(newReserva.getDataInicial());
		LocalDate datafinal = LocalDate.parse(newReserva.getDataFinal());
		Reserva reservado = reservaRepo.findIfBookReserved(datainicial, datafinal, newReserva.getLivroId());
		if(reservado!=null) {
			return new ResponseEntity <String>("Livro Já Reservado nessa data.", httpHeaders, HttpStatus.CONFLICT);
		}
		int alugado = aluguelRepo.findIfBookRented(datainicial, datafinal, newReserva.getLivroId());
		if(alugado!=0) {
			return new ResponseEntity <String>("Livro não devolvido nessa data.", httpHeaders, HttpStatus.CONFLICT);
		}
		
		if(newReserva.getDataInicial()!=null && newReserva.getDataFinal()!=null && newReserva.getClienteId()!=-1 && newReserva.getLivroId()!=-1) {
			Cliente cliente = clienteRepo.findById(newReserva.getClienteId());
			if (cliente==null) {
				return new ResponseEntity <String>("Cliente não cadastrado.", httpHeaders, HttpStatus.NOT_FOUND);
			}
			Livro livro = livroRepo.findById(newReserva.getLivroId());
			if (livro == null) {
				return new ResponseEntity <String>("Livro não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
			}
			try {
				
				Reserva ReservaToDB = new Reserva(datainicial,datafinal,
						cliente,livro);
				reservaRepo.save(ReservaToDB);
				return new ResponseEntity <String>("Criado com sucesso", httpHeaders, HttpStatus.CREATED);
			} catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity <String>(e.getMessage(), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			return new ResponseEntity <>("Informações incompletas", httpHeaders, HttpStatus.BAD_REQUEST);
		}	
	}
	@PutMapping(value="/{id}")
	public ResponseEntity <?> updateOne(@PathVariable("id") Long id, @RequestBody ReservaRequest newReserva){
		HttpHeaders httpHeaders = new HttpHeaders();
		
		if(newReserva.getDataInicial()!=null && newReserva.getDataFinal()!=null && newReserva.getClienteId()!=-1 && newReserva.getLivroId()!=-1 && id!=null) {
			Reserva doesExist = reservaRepo.findById(id);
			if(doesExist == null) {
				return new ResponseEntity <String>("Reserva não cadastrada", httpHeaders, HttpStatus.NOT_FOUND);
			}
			Cliente cliente = clienteRepo.findById(newReserva.getClienteId());
			if (cliente==null) {
				return new ResponseEntity <String>("Cliente não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
			}
			Livro livro = livroRepo.findById(newReserva.getLivroId());
			if (livro == null) {
				return new ResponseEntity <String>("Livro não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
			}
			try {
				Reserva ReservaToDB = new Reserva(id,
						LocalDate.parse(newReserva.getDataInicial()),
						LocalDate.parse(newReserva.getDataFinal()),
						cliente,livro);
				reservaRepo.save(ReservaToDB);
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
		Reserva doesExist = reservaRepo.findById(id);
		if(doesExist == null) {
			return new ResponseEntity <String>("Reserva não cadastrada", httpHeaders, HttpStatus.NOT_FOUND);
		}
		reservaCanceladaRepo.save(new ReservaCancelada(doesExist));
		reservaRepo.deleteById(id);
		
		return new ResponseEntity <String>("Reserva deletada com sucesso", httpHeaders, HttpStatus.OK);
	}
	
	@GetMapping(value="/rentweek/{data}")
	public ResponseEntity <?> rentWeek(@PathVariable("data") String data){
		HttpHeaders httpHeaders = new HttpHeaders();
		LocalDate hoje = LocalDate.parse(data);
		List<Reserva> reservasSemanais = reservaRepo.findByRentWeek(hoje.with(DayOfWeek.MONDAY), hoje.with(DayOfWeek.SUNDAY));
		return new ResponseEntity <List<Reserva>>(reservasSemanais, httpHeaders, HttpStatus.OK);
	}
	
	@GetMapping(value="/returnweek/{data}")
	public ResponseEntity <?> returnWeek(@PathVariable("data") String data){
		HttpHeaders httpHeaders = new HttpHeaders();
		LocalDate hoje = LocalDate.parse(data);;
		List<Reserva> reservasSemanais = reservaRepo.findByReturnWeek(hoje.with(DayOfWeek.MONDAY), hoje.with(DayOfWeek.SUNDAY));
		return new ResponseEntity <List<Reserva>>(reservasSemanais, httpHeaders, HttpStatus.OK);
	}

@GetMapping(value="/week/{data}")
public ResponseEntity <?> getWeek(@PathVariable("data") String data){
	HttpHeaders httpHeaders = new HttpHeaders();
	LocalDate hoje = LocalDate.parse(data);
	List<Reserva> reservasSemanais = reservaRepo.findByWeek(hoje.with(DayOfWeek.MONDAY), hoje.with(DayOfWeek.SUNDAY));
	return new ResponseEntity <List<Reserva>>(reservasSemanais, httpHeaders, HttpStatus.OK);
}
}
