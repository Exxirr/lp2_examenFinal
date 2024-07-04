package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Usuario;
import com.example.demo.repositorio.UsuarioRepositorio;
import com.example.demo.service.UsuarioService;
import com.example.demo.utils.Utilitarios;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Override
	public void crearUsuarioLogin(Usuario usuario, Model model, MultipartFile foto) {
		
		String nombreFoto = Utilitarios.guardarImagen(foto);
		
		usuario.setUrlPerfil(nombreFoto);
		
		String passwordHash = Utilitarios.extraerHash(usuario.getContra());
		
		usuario.setContra(passwordHash);
		
		usuarioRepositorio.save(usuario);
		
		model.addAttribute("registroCorrecto","Registro Correcto");
		
		model.addAttribute("usuario", new Usuario());
	}

	@Override
	public boolean validarUsuario(Usuario usuario, HttpSession session) {
		
		Usuario usuarioCorreo = usuarioRepositorio.findBycorreo(usuario.getCorreo());
		
		if(usuarioCorreo == null) {
			
			return false;
		}
		if(Utilitarios.verificarContrasenia(usuario.getContra(), usuarioCorreo.getContra())) {
			
			return  false;
		}
		
		session.setAttribute("usuario", usuarioCorreo.getCorreo());
		
		return true;
	}

	@Override
	public Usuario buscarUsuarioPorCorreo(String correo) {
		
		return usuarioRepositorio.findBycorreo(correo);
	}

}
