package org.springframework.samples.petclinic.model;


import java.time.LocalDate;     
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "concierto")
public class Concert extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "recinto_id")
	@NotNull
	private Recinto recinto;
	
	@ManyToOne
	@JoinColumn(name = "festival_id")
	@NotNull
	private Festival festival;
	
	@ManyToOne
	@JoinColumn(name = "artista_id")
	@NotNull
	private Artista artista;
	
	@Column(name = "fecha")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	private LocalDate fecha;

	@Column(name = "horaCom")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@NotNull
	private LocalDateTime horaCom;

	@Column(name = "horaFin")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@NotNull
	private LocalDateTime horaFin;

}
