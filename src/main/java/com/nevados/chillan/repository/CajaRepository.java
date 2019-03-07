package com.nevados.chillan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nevados.chillan.domain.Caja;

@Repository
public interface CajaRepository extends JpaRepository<Caja,Integer> {
	Caja findById(int id);
	Caja findByCorreo(String correo);
	Caja findByPassword(String password);
	List <Caja> findAll();
	<S extends Caja>S save(Caja caja);
}
