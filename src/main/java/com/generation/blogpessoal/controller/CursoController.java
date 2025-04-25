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

import com.generation.blogpessoal.model.Curso;
import com.generation.blogpessoal.repository.CursoRepository;
import com.generation.blogpessoal.repository.CategoriaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cursos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CursoController {

	@Autowired // Injeção de dependências, injetou a interface Repository com todos seus
				// poderes
	// dentro da classe Controller
	private CursoRepository cursoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Curso>> getAll() {
		return ResponseEntity.ok(postagemRepository.findAll());
	}

	@GetMapping("/{id}") // entre chaves {} significa que pegará o valor que será inserido no id e
							// passará na @PathVariable
	public ResponseEntity<Curso> getById(@PathVariable Long id) {
		return postagemRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Curso>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}

	@PostMapping // verbo POST - Enviar, "cadastrar"
	public ResponseEntity<Curso> post(@Valid @RequestBody Curso postagem) {
		if (temaRepository.existsById(postagem.getTema().getId())) {
			return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe!", null);
	}

	@PutMapping // Verb PUT - atualizar
	public ResponseEntity<Curso> put(@Valid @RequestBody Curso postagem) {
		if (postagemRepository.existsById(postagem.getId())) {

			if (temaRepository.existsById(postagem.getTema().getId())) {
				return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
			}

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe", null);

		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Curso> postagem = postagemRepository.findById(id);

		if (postagem.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		postagemRepository.deleteById(id);
	}
}
