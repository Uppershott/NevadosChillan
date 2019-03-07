package com.nevados.chillan.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nevados.chillan.domain.Sucursal;

@Repository
public interface SucursalRepository extends JpaRepository <Sucursal,Integer>{
	Sucursal findById(int id);
	Sucursal findByNombre(String nombre);
	List<Sucursal> findAll();
	<S extends Sucursal> S save(Sucursal sucursal);
}
