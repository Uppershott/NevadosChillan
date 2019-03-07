package com.nevados.chillan.service;

import java.util.List;

import com.nevados.chillan.domain.Producto;


public interface ProductoService {
	Producto findById(int id);
	Producto findByNombre(String nombre);
	List<Producto> findAll();
	<S extends Producto> S save(Producto producto);
}