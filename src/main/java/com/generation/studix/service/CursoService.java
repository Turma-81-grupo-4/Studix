package com.generation.studix.service;

import java.time.LocalDate;
import org.springframework.stereotype.Service;

import com.generation.studix.model.Curso;


@Service
public class CursoService {

    public void verificarDisponibilidade(Curso curso) {
        int vagas = curso.getVagas();
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataCurso = curso.getData();

        boolean isDataValida = dataAtual.isBefore(dataCurso);
        boolean isVagaDisponivel = vagas > 0;

        curso.setDisponibilidade(isDataValida && isVagaDisponivel);
    }
}

