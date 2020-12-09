package org.springframework.samples.petclinic.service;

import java.util.List;

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

	@Transactional(readOnly = true)
	public List<Usuario> findAllUsuarios() throws DataAccessException {
		return usuarioRepository.findAllUsuarios();
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

	@Transactional(readOnly = true)
	public Usuario findUsuarioById(Integer id) {
		return usuarioRepository.findUsuarioById(id);
	}

	@Transactional(readOnly = true)
	public TipoUsuario findTipoUsuario(String usuario) throws DataAccessException {
		return usuarioRepository.findTipoUsuarioByName(usuario);
	}
	
	@Transactional(readOnly = true)
	public Usuario findUsuarioByUsername(String username) {
		return usuarioRepository.findTipoUsuarioByUserName(username);
	}
}
