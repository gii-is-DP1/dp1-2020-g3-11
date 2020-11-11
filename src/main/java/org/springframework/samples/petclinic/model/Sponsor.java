package org.springframework.samples.petclinic.model;


import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;


@Entity
@Data
@Table(name = "sponsor")
public class Sponsor extends Person {
	

	@Column(name = "correo")
	@NotBlank
	private String correo;
	
	@Column(name = "dni")
	@NotBlank
	private String DNI;
	
	@Column(name = "marca")
	@NotBlank
	private String marca;


	@Column(name = "telefono")
	@NotNull
	@Digits(fraction = 0, integer = 10)
	private String telefono;

	
	@Column(name = "fechaNacimiento")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	private LocalDate fechaNacimiento;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;



}

