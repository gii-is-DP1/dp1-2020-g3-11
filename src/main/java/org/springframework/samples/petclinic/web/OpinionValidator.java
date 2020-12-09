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

import java.util.ArrayList;
import java.util.List;

import org.springframework.samples.petclinic.model.Opinion;
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
public class OpinionValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {
		Opinion opinion = (Opinion) obj;
		String descripcion= opinion.getDescripcion();
		Integer puntuacion= opinion.getPuntuacion();
		List<String> l= new ArrayList<String>();
		List<String> x= new ArrayList<String>();

		l.add("puta");
		l.add("zorra");
		l.add("marica");
		boolean p = false;

		for (int i = 0; i < l.size(); i++) {
			if(descripcion.indexOf(l.get(i))!=-1) {
				 p= true;
				x.add(l.get(i));
			}
		}
		if(p) {
			String listaInsultos= x.toString().replace("[", "").replace("]", "");
			errors.rejectValue("descripcion", "No puedes usar las siguientes palabras ofensivas: " + listaInsultos + ".", "No puedes usar las siguientes palabras ofensivas: "  + listaInsultos + ".");
			
		}
	
		if(descripcion.length()>1024) {
			errors.rejectValue("descripcion", "El número máximo de caracteres es 1024", "El número máximo de caracteres es 1024");
		}
		
		if(descripcion.length()<15 || descripcion==null) {
			errors.rejectValue("descripcion", "La descripción de la valoración tiene que ser más extensa", "La descripción de la valoración tiene que ser más extensa");
		}
		if(puntuacion==null) {
			errors.rejectValue("descripcion", "Elige una puntuación", "Elige una puntuación");

		}
		
		}
		

	/**
	 * This Validator validates *just* Pet instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Opinion.class.isAssignableFrom(clazz);
	}

}
