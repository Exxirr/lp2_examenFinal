package com.example.demo.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Categoria;
import com.example.demo.entity.Producto;
import com.example.demo.entity.Usuario;
import com.example.demo.service.CategoriaService;
import com.example.demo.service.ProductoService;
import com.example.demo.service.UsuarioService;


import jakarta.servlet.http.HttpSession;

@Controller
public class ProductoControlador {

	
	@Autowired
	private UsuarioService usuarioServicio;
	
	@Autowired
	private ProductoService productoServicio;
	
	@Autowired
	private CategoriaService categoriaServicio;
	
	
	@GetMapping("/menu")
	
	public String mostrarMenu(HttpSession session, Model model){
		
		if(session.getAttribute("usuario") == null) {
			
			return "redirect:/";
			
		}
		
		String correo = session.getAttribute("usuario").toString();
		
		Usuario usuario = usuarioServicio.buscarUsuarioPorCorreo(correo);
		
		model.addAttribute("foto", usuario.getUrlPerfil());
		
		List<Producto> productos = productoServicio.buscarTodosProductos();
		
		model.addAttribute("listarProductos", productos);
		
		return "menu";
	}
	
	
	@GetMapping("/registrar_producto")
	public String mostrarRegistrarProducto(Model model) {
		
		List<Categoria> listarCategoria = categoriaServicio.buscarTodosCategoria();
		
		model.addAttribute("productos", new Producto());
		
		model.addAttribute("categorias", listarCategoria);
		
		return "registrar_productos";
	}
	
	
	@PostMapping("/registrar_producto")
	public String registrarProducto(@ModelAttribute Producto producto, Model model ) {
		
		productoServicio.guardarProducto(producto);
		
		return "redirect:/menu";
	}
	
		
	
}
