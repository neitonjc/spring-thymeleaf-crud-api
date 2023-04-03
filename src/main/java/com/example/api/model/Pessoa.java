package com.example.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="PESSOA")
public class Pessoa {
	
	@Id
	@Column(name="COD")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cod;
	
	@NotBlank(message = "Name is mandatory")
	@Column(name="NOME")
	private String nome;
	
	@Email
	@NotBlank(message = "Email is mandatory")
	@Column(name="EMAIL")
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Genero genero;
	
	

	public Pessoa(Integer cod, @NotBlank(message = "Name is mandatory") String nome,
			@Email @NotBlank(message = "Email is mandatory") String email, Genero genero) {
		super();
		this.cod = cod;
		this.nome = nome;
		this.email = email;
		this.genero = genero;
	}

	public Pessoa() {
		super();
	}

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	
}
