package com.nevados.chillan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nevados.chillan.domain.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{
	Producto findById(int id);
	Producto findByNombre(String nombre);
	List<Producto> findAll();
	<S extends Producto> S save(Producto producto);
}