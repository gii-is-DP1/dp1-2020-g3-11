package org.springframework.samples.springfest.web;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.samples.springfest.model.Concert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ConcertValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {
		Concert concierto = (Concert) obj;
		LocalDate fecha = concierto.getFecha();
		LocalDateTime horaCom = concierto.getHoraCom();
		LocalDateTime horaFin = concierto.getHoraFin();

		if (fecha == null || horaCom == null || horaFin == null) {

			if (fecha == null) {
				errors.rejectValue("fecha", "Campo requerido", "Campo requerido");
			}

			if (horaCom == null) {
				errors.rejectValue("horaCom", "Campo requerido", "Campo requerido");
			}

			if (horaFin == null) {
				errors.rejectValue("horaFin", "Campo requerido", "Campo requerido");
			}

		} else if (fecha != null || horaCom != null || horaFin != null) {

			if (horaFin != null) {
				if (horaCom != null && (horaFin.isBefore(horaCom) || horaFin.equals(horaCom))) {
					errors.rejectValue("horaCom", "La hora comienzo tiene que ser anterior a la hora final",
							"La hora comienzo tiene que ser anterior a la hora final");
				} else if (fecha != null && !horaCom.toLocalDate().isEqual(fecha)) {
					errors.rejectValue("fecha", "La fecha del concierto debe ser igual a la fecha de inicio",
							"La fecha del concierto debe ser igual a la fecha de inicio");
				}
			}

			if (fecha.isBefore(LocalDate.now()) && fecha != null) {
				errors.rejectValue("fecha", "La fecha tiene que ser posterior a la actualidad",
						"La fecha tiene que ser posterior a la actualidad");
			}

		}

		if (concierto.isNew() && concierto.getRecinto() == null) {
			errors.rejectValue("recinto.name", "Elige un recinto", "Elige un recinto");

		}

		if (concierto.isNew() && concierto.getArtista() == null) {
			errors.rejectValue("artista.name", "Elige un artista", "Elige un artista");

		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Concert.class.isAssignableFrom(clazz);
	}

}
