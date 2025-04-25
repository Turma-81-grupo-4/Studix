package com.generation.blogpessoal.model;

import java.time.LocalDate;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity // @Entity transforma a classe em tabela através do JPA
@Table(name = "tb_cursos") // @Table define o nome para a tabela, caso não coloque o table ele criará com
							// um nome genérico
public class Curso {

	// Variáveis que se transformarão em colunas no MySQL
	@Id // Anotação para indicar a PK - primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Anotação para regra de auto increment no ID
	private Long id; // o Long se transformará em BIGINT no MySQL

	@NotBlank // Anotação para não nulo e não vazio (ou seja, não aceita nem mesmo apenas
				// espaços, diferente do NotNull
	@Size(min = 3, max = 100) // Anotação para o VARCHAR, define o tamanho necessário, minimo e máximo da
								// String
	private String titulo;

	@NotBlank
	@Size(min = 10, max = 1000)
	private String descricao;

	@UpdateTimestamp // Toda postagem criada pegara automaticamente a data e hora atual do usuário
	private LocalDate data;

	private boolean disponibilidade = true;

	@NotNull
	private int vagas;

	@ManyToOne
	@JsonIgnoreProperties("curso")
	private Categoria tema; // Foreign Key, do tipo "Tema" que é a outra tabela, pegará automaticamente o PK
							// id

	@ManyToOne
	@JsonIgnoreProperties("curso")
	private Usuario usuario;

	// Getters e Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Categoria getTema() {
		return tema;
	}

	public void setTema(Categoria tema) {
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isDisponibilidade() {
		return disponibilidade;
	}

	public void setDisponibilidade(boolean disponibilidade) {
		this.disponibilidade = disponibilidade;
	}

	public int getVagas() {
		return vagas;
	}

	public void setVagas(int vagas) {
		this.vagas = vagas;
	}

}
