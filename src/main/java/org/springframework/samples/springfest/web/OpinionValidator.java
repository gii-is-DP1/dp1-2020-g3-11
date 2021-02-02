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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.samples.springfest.model.Opinion;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 * <p>
 * We"re not using Bean Validation annotations here because it is easier to
 * define such validation rule in Java.
 * </p>
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
public class OpinionValidator implements Validator {

	private String htmlEntities(String s) {
	      return s.replace("&ntilde;", "ñ")
	                        .replace("&Ntilde;", "Ñ")
	                        .replace("&amp;", "&")
	                        .replace("&Ntilde;", "Ñ")
	                        .replace("&ntilde;", "ñ")
	                        .replace("&Ntilde;", "Ñ")
	                        .replace("&Agrave;", "À")
	                        .replace("&Aacute;", "Á")  
	                        .replace("&Acirc;","Â")
	                        .replace("&Atilde;","Ã")   
	                        .replace("&Auml;","Ä")
	                        .replace("&Aring;","Å")
	                        .replace("&AElig;","Æ")
	                        .replace("&Ccedil;","Ç")
	                        .replace("&Egrave;","È")
	                        .replace("&Eacute;","É")
	                        .replace("&Ecirc;", "Ê")
	                        .replace("&Euml;","Ë")
	                        .replace(   "&Igrave;", "Ì")
	                        .replace("&Iacute;", "Í"    )
	                        .replace("&Icirc;", "Î" )
	                        .replace(   "&Iuml;", "Ï")
	                        .replace(   "&ETH;", "Ð")
	                        .replace(   "&Ntilde;", "Ñ")
	                        .replace(   "&Ograve;", "Ò")
	                        .replace(   "&Oacute;", "Ó")
	                        .replace("&Ocirc;", "Ô" )
	                        .replace(   "&Otilde;", "Õ")
	                        .replace("&Ouml;", "Ö"  )
	                        .replace("&Oslash;", "Ø"    )
	                        .replace(   "&Ugrave;" ,"Ù")
	                        .replace(   "&Uacute;", "Ú")
	                        .replace(   "&Ucirc;", "Û")
	                        .replace(   "&Uuml;", "Ü")
	                        .replace(   "&Yacute;", "Ý")
	                        .replace("&THORN;", "Þ" )
	                        .replace(   "&szlig;", "ß")
	                        .replace(   "&agrave;", "à")
	                        .replace(   "&aacute;", "á")
	                        .replace(   "&acirc;", "â")
	                        .replace(   "&atilde;", "ã")
	                        .replace("&auml;", "ä"  )
	                        .replace(   "&aring;", "å")
	                        .replace(   "&aelig;", "æ")
	                        .replace(   "&ccedil;", "ç")
	                        .replace("&egrave;", "è"    )
	                        .replace("&eacute;", "é"    )
	                        .replace("&ecirc;", "ê" )
	                        .replace("&euml;", "ë"  )
	                        .replace(   "&igrave;", "ì")
	                        .replace("&iacute;", "í"    )
	                        .replace("&icirc;", "î" )
	                        .replace("&iuml;", "ï"  )
	                        .replace("&eth;", "ð"   )
	                        .replace(   "&ntilde;", "ñ")
	                        .replace("&ograve;","ò")
	                        .replace("&oacute;","ó")
	                        .replace("&ocirc;","ô")
	                        .replace("&otilde;","õ")
	                        .replace("&ouml;","ö")
	                        .replace("&oslash;","ø")
	                        .replace("&ugrave;","ù")
	                        .replace("&uacute;","ú")
	                        .replace("&ucirc;","û")
	                        .replace("&uuml;" , "ü")   
	                        .replace("&yacute;", "ý")  
	                        .replace("&thorn;", "þ")
	                        .replace("&yuml;", "ÿ");
	    }

	private List<String> leerFichero(String fichero) {
		List<String> l= new ArrayList<String>();

		try {
			l = Files.lines(Paths.get(fichero)).collect(Collectors.toList());

		} catch (IOException e) {
			
			System.out.println("No se puede leer el fichero de insultos.");
		}
		return l;
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		Opinion opinion = (Opinion) obj;
		String descripcion= htmlEntities(opinion.getDescripcion());
		Integer puntuacion= opinion.getPuntuacion();
		
		List<String> l= leerFichero("insultos.txt");
		List<String> x= new ArrayList<String>();

		boolean p = false;
		System.out.println(descripcion);
		for (int i = 0; i < l.size(); i++) {
			if(descripcion.indexOf(l.get(i))!=-1 || descripcion.indexOf(l.get(i).toLowerCase())!=-1
					|| descripcion.indexOf(l.get(i).toUpperCase())!=-1) {
				System.out.println(l.get(i));
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