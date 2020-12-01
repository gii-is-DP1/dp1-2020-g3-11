package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.TipoUsuario;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

	private UsuarioRepository usuarioRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Transactional
	public void saveUsuario(Usuario usuario) throws DataAccessException {
		usuarioRepository.save(usuario);
		userService.saveUser(usuario.getUser());
		if (usuario.getTipoUsuario().getName().equals("sponsor")) {
			authoritiesService.saveAuthorities(usuario.getUser().getUsername(), "sponsor");
		} else {
			authoritiesService.saveAuthorities(usuario.getUser().getUsername(), "usuario");

		}
	}

	public Optional<Usuario> findUser(Integer id) {
		return usuarioRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public TipoUsuario findTipoUsuario(String usuario) throws DataAccessException {
		return usuarioRepository.findTipoUsuarioByName(usuario);
	}
}
