package com.example.demo.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Usuario;
import com.example.demo.service.UsuarioService;
import com.example.demo.utils.Utilitarios;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioControlador {

	@Autowired
	private UsuarioService usuarioServicio;
	
	
	@GetMapping("/registrar")
	public String RegistrarUsuario(Model model) {
		
		model.addAttribute("usuario", new Usuario());
		
		return "registrar_usuario";
	}
	
	
	@PostMapping("/registrar")
	public String registrarUsuario(Usuario usuario, Model model, @RequestParam("foto") MultipartFile foto) {
		
		usuarioServicio.crearUsuarioLogin(usuario, model, foto);
		
		return "registrar_usuario";	
	}
	
	
	
	@GetMapping("/")
	public String login(Model model) {
		
		model.addAttribute("usuario", new Usuario());
		
		return "login";
	}
	
	
	
	@PostMapping("/login")
	public String loginPost(Usuario usuario, Model model, HttpSession session) {
		
		boolean usuarioValido = usuarioServicio.validarUsuario(usuario, session);
		
		if(usuarioValido) {
			
			return "redirect:/menu";
		}
		
		model.addAttribute("loginInvalido", "No existe el usuario");
		model.addAttribute("usuario", new Usuario());
		
		return "login";
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
