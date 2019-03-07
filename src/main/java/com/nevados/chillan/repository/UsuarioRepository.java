package com.nevados.chillan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nevados.chillan.domain.Usuario;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	Usuario findById(int id);
	Usuario findByRut(String rut);
	Usuario findByCorreo(String correo);
	List<Usuario> findAll();
	//Usuario findByCaja(Caja caja);
	<S extends Usuario> S save(Usuario usuario);
}
