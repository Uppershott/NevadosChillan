package com.nevados.chillan.service;

import java.util.List;


import com.nevados.chillan.domain.Usuario;


public interface UsuarioService {
	Usuario findById(int id);
	Usuario findByRut(String rut);
	Usuario findByCorreo(String correo);
	List <Usuario> findAll();
	//Usuario findByCaja(Caja caja);
	<S extends Usuario> S save(Usuario usuario);
}
