package com.example.demo.controlador;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Categoria;
import com.example.demo.entity.Producto;
import com.example.demo.entity.Usuario;
import com.example.demo.service.CategoriaService;
import com.example.demo.service.PdfService;
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
	
	@Autowired
	private PdfService pdfservice;
	
	
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
	
	
	@GetMapping("/editar_producto/{prodId}")
	public String editarProducto(Model model, @PathVariable("prodId") Integer proId, HttpSession session) {
		
		if(session.getAttribute("usuario") == null) {
			
			return "redirect:/";
			
		}
		
		Producto producto = productoServicio.buscarProductoPorId(proId);
		
		List <Categoria> listarCategoria = categoriaServicio.buscarTodosCategoria();
		
		model.addAttribute("productos", producto);
		
		model.addAttribute("categorias", listarCategoria);
		
		 return "editar_productos";
		
	}
	
	
	@PostMapping("/editar_producto/{prodId}")
	public String guardarProductoEditado(@PathVariable("prodId") Integer prodId, @ModelAttribute("productos") Producto producto, HttpSession session) {
	    
	    if (session.getAttribute("usuario") == null) {
	        return "redirect:/";
	    }
	    
	    productoServicio.guardarProducto(producto);
	    
	    return "redirect:/menu";
	}
	
	@GetMapping("/detalle_producto/{prodId}")
	public String verProducto(Model model, @PathVariable("prodId") Integer proId, HttpSession session) {
		
		
		if(session.getAttribute("usuario") == null) {
			
			return "redirect:/login";
			
		}
		
		Producto productoEncontrado = productoServicio.buscarProductoPorId(proId);
		
		model.addAttribute("productos", productoEncontrado);
		
		
		return "detalle_productos";
	
		
	}
	
	
	@GetMapping("/eliminarProducto/{proId}")
	public String eliminarProducto(Model model, @PathVariable("proId") Integer proId, HttpSession session) {
		
		if(session.getAttribute("usuario") == null) {
    		return "redirect:/login";
    	}
		
		
		productoServicio.eliminarProductoPorId(proId);
		
		return "redirect:/menu";
	
	}
	
	
	@GetMapping("/generar_pdf")
	
	public ResponseEntity<InputStreamResource> generarPdf(HttpSession session) throws IOException{
		
		List<Producto> productoSession = null;
		
		if(session.getAttribute("usuario") != null) {
			
			productoSession = productoServicio.buscarTodosProductos();
			
		}else {
			
			productoSession = (List<Producto>) session.getAttribute("productos");
		}
	
		Map<String, Object> datosPdf = new HashMap<String, Object>();
		
		datosPdf.put("productosDatos", productoSession);
		
		ByteArrayInputStream pdfBytes = pdfservice.generarPdfDeHtml("template_pdf", datosPdf);
		
		HttpHeaders httpHeaders = new HttpHeaders();	
		httpHeaders.add("Content-Disposition", "inline; filename=productos.pdf");
		
		return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(pdfBytes));
		
	}
}
