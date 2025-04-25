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
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.CursoRepository;
import com.generation.blogpessoal.service.CursoService;
import com.generation.blogpessoal.repository.CategoriaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cursos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CursoController {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private CursoService cursoService;

	private UsuarioLogin usuarioLogin;
           
	@GetMapping
	public ResponseEntity<List<Curso>> getAll() {

		List<Curso> cursos = cursoRepository.findAll();

		for (Curso curso : cursos) {
			cursoService.VerificarDisponibilidade(curso, usuarioLogin);
		}
		return ResponseEntity.ok(cursos);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Curso> getById(@PathVariable Long id) {

		Optional<Curso> cursoOptional = cursoRepository.findById(id);

		if (cursoOptional.isPresent()) {
			Curso curso = cursoOptional.get();
			cursoService.VerificarDisponibilidade(curso, usuarioLogin);
			return ResponseEntity.ok(curso);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Curso>> getByTitulo(@PathVariable String titulo) {


		List<Curso> cursos = cursoRepository.findAllByTituloContainingIgnoreCase(titulo);

		for (Curso curso : cursos) {
			cursoService.VerificarDisponibilidade(curso, usuarioLogin);
		}
		return ResponseEntity.ok(cursos);

	}

	@PostMapping
	public ResponseEntity<Curso> post(@Valid @RequestBody Curso curso) {
		if (categoriaRepository.existsById(curso.getCategoria().getId())) {
			return ResponseEntity.status(HttpStatus.CREATED).body(cursoRepository.save(curso));
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!", null);
	}

	@PutMapping
	public ResponseEntity<Curso> put(@Valid @RequestBody Curso curso) {
		if (cursoRepository.existsById(curso.getId())) {

			if (categoriaRepository.existsById(curso.getCategoria().getId())) {
				return ResponseEntity.status(HttpStatus.OK).body(cursoRepository.save(curso));
			}

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe", null);

		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Curso> curso = cursoRepository.findById(id);

		if (curso.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		cursoRepository.deleteById(id);
	}
}
