package org.springframework.samples.springfest.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "usuario")
public class Usuario extends Person {

	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
	private Set<Entrada> entradas;

	@Column(name = "correo")
	@NotBlank
	private String correo;

	@Column(name = "dni")
	@NotBlank
	private String DNI;

	@ManyToOne(optional=false)
	@JoinColumn(name = "tipos_usuario")
	@NotNull
	private TipoUsuario tipoUsuario;

	@Column(name = "telefono")
	@NotBlank
	private String telefono;

	@Column(name = "marca")
	private String marca;

	@Column(name = "fechaNacimiento")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	private LocalDate fechaNacimiento;

	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

	@OneToOne(optional=true)
	@JoinColumn(name = "id", referencedColumnName = "id")
	private Festival festival;

}
