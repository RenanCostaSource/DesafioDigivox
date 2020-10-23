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

import com.digivox.desafio.model.Cliente;
import com.digivox.desafio.repositories.ClienteRepository;

import org.springframework.dao.DataIntegrityViolationException;
/**
 * Desafio Digivox - 19/10/2020
 * Candidato: Renan Costa
 * Arquivo: Controller para CRUD  de Clientes
 * 
 * Histórico de Alteraçôes:
 * - 19/10/2020 Renan Costa Criação
 */

@RestController
@RequestMapping(value= {"/cliente"})
public class ClienteController {
	@Autowired
	ClienteRepository clienteRepo;
	
	@GetMapping
	
	public ResponseEntity <List<Cliente>> getAll(){
		HttpHeaders httpHeaders = new HttpHeaders();
		
		List<Cliente> todosClientes =  clienteRepo.findAll();
		
		return new ResponseEntity <List<Cliente>>(todosClientes, httpHeaders, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity <?> getOne(@PathVariable("id") Long id){
		HttpHeaders httpHeaders = new HttpHeaders();
		Cliente cliente = clienteRepo.findById(id);
		if(cliente == null) {
			return new ResponseEntity <String>("Cliente não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
		}
		
		
		
		return new ResponseEntity <Cliente>(cliente, httpHeaders, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity <?> postOne(@RequestBody Cliente newCliente){
		HttpHeaders httpHeaders = new HttpHeaders();
		if(newCliente.getId()!=null) {
			return new ResponseEntity <String>("Atributo \"id\" não permitido", httpHeaders, HttpStatus.BAD_REQUEST);
		}
		if(newCliente.getNome()!=null && newCliente.getNome()!="" && newCliente.getCPF()!=null && newCliente.getCPF()!="") {
		
			try {
				clienteRepo.save(newCliente);
				return new ResponseEntity <String>("Criado com sucesso", httpHeaders, HttpStatus.CREATED);
			} catch(DataIntegrityViolationException e){
				return new ResponseEntity <String>("Cliente já Cadastrado", httpHeaders, HttpStatus.CONFLICT);
			} catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity <String>(e.getMessage(), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		return new ResponseEntity <String>("Informações incompletas", httpHeaders, HttpStatus.BAD_REQUEST);
		
	}
	
	@PutMapping
	public ResponseEntity <?> updateOne(@RequestBody Cliente newCliente){
		HttpHeaders httpHeaders = new HttpHeaders();
		System.out.println(newCliente.getId());
		System.out.println(newCliente.getNome());
		System.out.println(newCliente.getCPF());
		if(newCliente.getNome()!=null && newCliente.getNome()!="" && newCliente.getId()!=null) {
			Cliente doesExist = clienteRepo.findById(newCliente.getId());
			if(doesExist == null)return new ResponseEntity <>("Cliente não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
			try {
				clienteRepo.save(newCliente);
				return new ResponseEntity <String>("Atualizado com sucesso", httpHeaders, HttpStatus.CREATED);
			} catch(DataIntegrityViolationException e){
				return new ResponseEntity <String>("CPF já cadastrado", httpHeaders, HttpStatus.CONFLICT);
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
		try {
		Cliente doesExist = clienteRepo.findById(id);
		if(doesExist == null) {
			return new ResponseEntity <String>("Cliente não cadastrado", httpHeaders, HttpStatus.NOT_FOUND);
		}
		clienteRepo.deleteById(id);
		return new ResponseEntity <String>("Cliente deletado com sucesso", httpHeaders, HttpStatus.OK);
		} catch(Exception e) { 
			e.printStackTrace();
			return new ResponseEntity <String>(e.getMessage(), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
