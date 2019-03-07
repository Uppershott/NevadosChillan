package com.nevados.chillan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.nevados.chillan.domain.Producto;
import com.nevados.chillan.repository.ProductoRepository;
import com.nevados.chillan.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService{

	private ProductoRepository productoRepository;
	
	@Autowired
	public ProductoServiceImpl(ProductoRepository productoRepository) {
		super();
		this.productoRepository = productoRepository;
	}

	@Override
	public Producto findById(int id) {
		Producto producto = null;
		try {
			producto = productoRepository.findById(id);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return producto;
	}

	@Override
	public <S extends Producto> S save(Producto producto) {
		return productoRepository.save(producto);
	}
	
	@Override
	public List<Producto> findAll() {
		List<Producto> productos = null;
		try {
			productos = productoRepository.findAll();
			System.out.println("Productos: " + productos.toString());
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return productos;
	}
	
	@Override
	public Producto findByNombre(String nombre) {
		Producto producto = null;
		try {
			producto = productoRepository.findByNombre(nombre);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			return null;
		}
		return producto;
	}
}
