package com.nevados.chillan.service;
import java.util.List;
import com.nevados.chillan.domain.Sucursal;

public interface SucursalService {
	Sucursal findById(int id);	
	Sucursal findByNombre(String nombre);
	List<Sucursal> findAll();
	<S extends Sucursal> S save(Sucursal sucursal);
}
