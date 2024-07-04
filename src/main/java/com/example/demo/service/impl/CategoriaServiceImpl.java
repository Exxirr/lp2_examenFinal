package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.Categoria;
import com.example.demo.repositorio.*;
import com.example.demo.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	
	@Autowired 
	private CategoriaRepositorio categoriaRepositorio;
	
	@Override
	public List<Categoria> buscarTodosCategoria() {
	
		return categoriaRepositorio.findAll();
	}

}
