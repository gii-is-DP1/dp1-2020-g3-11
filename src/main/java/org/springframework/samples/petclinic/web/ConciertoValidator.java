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
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.samples.petclinic.model.Concierto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ConciertoValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {
		Concierto concierto = (Concierto) obj;
		LocalDate fecha = concierto.getFecha();
		LocalDateTime horaCom = concierto.getHoraCom();
		LocalDateTime horaFin = concierto.getHoraFin();
//		Recinto recinto= concierto.getRecinto();
//		Artista artista= concierto.getArtista();
//		Integer numeroMaxEscenarios= concierto.getRecinto().getNumMaxEscenarios();

		if (fecha == null) {
			errors.rejectValue("fecha", "es requerido", "es requerido");

		}

		if (horaCom == null) {
			errors.rejectValue("horaCom", "es requerido", "es requerido");

		}

		if (horaFin == null) {
			errors.rejectValue("horaFin", "es requerido", "es requerido");

		}

		if (concierto.isNew() && concierto.getRecinto() == null) {
			errors.rejectValue("recinto", "es requerido", "es requerido");

		}

		if (concierto.isNew() && concierto.getArtista() == null) {
			errors.rejectValue("artista", "es requerido", "es requerido");

		}

		// fecha validation
		if (fecha.isBefore(LocalDate.now())) {
			errors.rejectValue("fecha", "La fecha tiene que ser posterior a la actualidad",
					"La fecha tiene que ser posterior a la actualidad");
		}

		// horas validation
		if (horaFin.isBefore(horaCom) || horaFin.equals(horaCom)) {
			errors.rejectValue("horaCom", "La fecha comienzo tiene que ser anterior a la hora final",
					"La fecha comienzo tiene que ser anterior a la hora final");
		}

		// hora de comienzo igual a la fecha de concierto

		if (!horaCom.toLocalDate().isEqual(fecha)) {
			errors.rejectValue("fecha", "La fecha del concierto debe ser igual a la fecha de inicio",
					"La fecha del concierto debe ser igual a la fecha de inicio");
		}
//			
//			if(conciertosAVez(conciertoS.findAllConciertosByRecintoId(recinto.getId()), concierto)>=numeroMaxEscenarios) {
//				errors.rejectValue("recinto", "Se supera el numero maximo de escenarios ",
//						"Se supera el numero maximo de escenarios ");
//			}

	}

//	public static Integer conciertosAVez(Collection<Concierto> conciertos, Concierto conciertoMeter) {
//		LocalDateTime conciertoMeterHoraComienzo = conciertoMeter.getHoraCom();
//		return (int) conciertos.stream().filter(x-> conciertoMeterHoraComienzo.isAfter(x.getHoraCom())&& conciertoMeterHoraComienzo.isBefore(x.getHoraFin())).count();
//	}

	/**
	 * This Validator validates *just* Concierto instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Concierto.class.isAssignableFrom(clazz);
	}

}
