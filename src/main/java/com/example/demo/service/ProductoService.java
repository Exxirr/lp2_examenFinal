package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.*;

public interface ProductoService {

	List<Producto> buscarTodosProductos();
	
	Producto guardarProducto(Producto producto);
}
