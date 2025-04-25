package com.generation.studix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.studix.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> { // O long representa o ID
	
	//Personalizando a consulta
	public List<Curso> findAllByTituloContainingIgnoreCase(@Param("titulo") String titulo);
	
}
