package org.springframework.samples.springfest.web;

import org.springframework.samples.springfest.model.Oferta;
import org.springframework.samples.springfest.model.TipoOferta;
import org.springframework.util.StringUtils;
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
public class OfertaValidator implements Validator {

	private static final String REQUIRED = "Campo requerido.";

	@Override
	public void validate(Object obj, Errors errors) {
		Oferta o = (Oferta) obj;
		TipoOferta te = o.getTipoOferta();
		String name = o.getNombre();
		Integer precio = o.getPrecioOferta();

		if (!StringUtils.hasLength(name) || name.length() > 50 || name.length() < 3) {
			errors.rejectValue("nombre", REQUIRED + " Debe contener entre 3 y 50 caracteres.",
					REQUIRED + " Debe contener entre 3 y 50 caracteres.");
		}
		
		if (precio == null) {
			errors.rejectValue("precioOferta", REQUIRED, REQUIRED);
			
		}else {

		if (precio < 0) {
			errors.rejectValue("precioOferta", REQUIRED + " El precio de la oferta debe ser mayor o igual que 0.",
					REQUIRED + " El precio de la entrada debe ser mayor o igual que 0.");
		}
		}

		// type validation
		if (te == null) {
			errors.rejectValue("tipoOferta.name", "Debe elegir un tipo de oferta", "Debe elegir un tipo de oferta");
		}
	}

	/**
	 * This Validator validates *just* Entrada instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Oferta.class.isAssignableFrom(clazz);
	}
}