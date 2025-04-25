package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Categoria;
import com.generation.blogpessoal.repository.CategoriaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Categoria>> getAll() {
		return ResponseEntity.ok(categoriaRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getById(@PathVariable Long id){
		return categoriaRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
    @GetMapping("busca/{categoria}")
    public ResponseEntity<List<Categoria>> getByCategoria(@PathVariable String categoria) {
        List<Categoria> resultado = categoriaRepository.findAllByCategoriaContainingIgnoreCase(categoria);

        if (resultado.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        }

        return ResponseEntity.ok(resultado);
    }

	
	@PostMapping 
	public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria){
		 if (categoriaRepository.existsById(categoria.getId()))    
	        	return ResponseEntity.ok(categoriaRepository.save(categoria));   
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não encontrada", null );
	    } 
	
	@PutMapping
    public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria categoria){
        if (categoriaRepository.existsById(categoria.getId()))    
        	return ResponseEntity.ok(categoriaRepository.save(categoria));   
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não encontrada", null );
    }
	
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		
		if(categoria.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		categoriaRepository.deleteById(id);
	}
}	
