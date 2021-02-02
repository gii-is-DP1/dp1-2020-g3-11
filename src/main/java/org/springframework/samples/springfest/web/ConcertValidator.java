/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.springfest.web;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.samples.springfest.model.Concert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 * <p>
 * We're not using Bean Validation annotations here because it is easier to
 * define such validation rule in Java.
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
public class ConcertValidator implements Validator {

////numero escenarios maximos
//public static Integer conciertosAVez(Collection<Concert> conciertos, Concert c) {
//	List<Concert> lConcerts= conciertos.stream().collect(Collectors.toList());
//	LocalDateTime newStartDate= c.getHoraCom();
//	LocalDateTime newFinishDate= c.getHoraFin();
//	Integer x=0;
//	for(int i=0; i<lConcerts.size(); i++) {
//		LocalDateTime actualStartDate= lConcerts.get(i).getHoraCom();
//		LocalDateTime actualFinishDate= lConcerts.get(i).getHoraFin();
//
//		boolean b1 = newStartDate.isBefore(actualStartDate);
//		boolean b2 = newFinishDate.isBefore(actualFinishDate);
//		boolean b3 = newStartDate.isAfter(actualStartDate);
//		boolean b4 = newFinishDate.isAfter(actualStartDate);
//		boolean b5 = newStartDate.isBefore(actualFinishDate);
//		boolean b6 = newFinishDate.isAfter(actualFinishDate);
//
//		if ((b1 && b4) || (b3 && b2) || (b5 && b6)) {
//			x++;
//	}
//	}
//	
//	return x;
//}

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

	/**
	 * This Validator validates *just* Pet instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Concert.class.isAssignableFrom(clazz);
	}

}
