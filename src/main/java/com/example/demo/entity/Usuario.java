package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "tb_usuario")
public class Usuario {
	
	@Id
	@Column(length = 45, nullable = false, unique = true)
	private String correo;
	
	private String contra;
	
	@Column(length = 45, nullable = false)
	private String nombres;
	
	@Column(length = 45, nullable = false)
	private String apellidos;
	
	@Temporal(TemporalType.DATE)
	private LocalDate fechNacimiento;
	
	private String urlPerfil;
	

}
