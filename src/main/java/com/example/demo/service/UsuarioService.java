package com.example.demo.service;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Usuario;

import jakarta.servlet.http.HttpSession;

public interface UsuarioService {

	void crearUsuarioLogin(Usuario usuario, Model model, MultipartFile fotoPerfil);
	
	boolean validarUsuario(Usuario usuario, HttpSession session);
	
	Usuario buscarUsuarioPorCorreo(String correo);
	
}
