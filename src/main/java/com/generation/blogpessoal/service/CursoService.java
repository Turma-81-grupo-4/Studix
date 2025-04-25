package com.generation.blogpessoal.service;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import com.generation.blogpessoal.model.Curso;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.security.JwtService;

@Service
public class CursoService {

	@Autowired
	Curso curso = new Curso();

	public void VerificarDisponibilidade(Curso curso, UsuarioLogin usuarioLogin) {
		int vagas = curso.getVagas();
		LocalDate dataAtual = usuarioLogin.getData();
		LocalDate dataCurso = curso.getData();

		boolean isDataValida = dataAtual.isBefore(dataCurso);
		boolean isVagaDisponivel = vagas > 0;

		curso.setDisponibilidade(isDataValida && isVagaDisponivel);
	}

}
