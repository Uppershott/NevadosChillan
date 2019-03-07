package com.nevados.chillan.service;

import java.util.List;

import com.nevados.chillan.domain.Caja;

public interface CajaService {
	Caja findById(int id);
	Caja findByCorreo(String correo);
	Caja findByPassword(String password);
	List <Caja> findAll();
	<S extends Caja>S save(Caja caja);
}
