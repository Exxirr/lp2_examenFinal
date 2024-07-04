package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Producto;
import com.example.demo.repositorio.ProductoRepository;
import com.example.demo.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoRepository productoRepositorio;
	
	@Override
	public List<Producto> buscarTodosProductos() {
	
		return productoRepositorio.findAll();
	}

	@Override
	public Producto guardarProducto(Producto producto) {
		
		return productoRepositorio.save(producto);
		
	}

	@Override
	public Producto buscarProductoPorId(Integer id) {
		
		return productoRepositorio.findById(id).get();
	}

	@Override
	public void eliminarProductoPorId(Integer id) {
		productoRepositorio.deleteById(id);
	}

}
