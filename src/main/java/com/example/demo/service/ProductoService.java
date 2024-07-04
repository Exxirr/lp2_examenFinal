package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.*;

public interface ProductoService {

	List<Producto> buscarTodosProductos();
	
	Producto guardarProducto(Producto producto);
	
	Producto buscarProductoPorId(Integer prodId);
	
	void eliminarProductoPorId(Integer proId);
}
