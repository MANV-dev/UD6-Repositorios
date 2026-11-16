package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.ProductoDTO;
import com.example.demo.entity.Producto;
import com.example.demo.service.CategoriaService;
import com.example.demo.service.ProductoService;

@Controller 
@RequestMapping("/productos")
public class ProductoController {
	
	private final ProductoService productoService;
	private final CategoriaService categoriaService;
	
	private final static String VISTA = "vista";
	private static final String PRODUCTOS_VIEW="productos";
	private static final String FORM_VIEW="productoForm";

	public ProductoController(ProductoService productoService, CategoriaService categoriaService) {
		
		this.productoService = productoService;
		this.categoriaService= categoriaService;
	}
	
	// http://localhost:8080/productos/listar?categoria=Culinarios&precioMin=1&precioMax=7000
	@GetMapping("/listar")
	public String listar(
			@RequestParam String categoria,
			@RequestParam Double precioMin,
			@RequestParam Double precioMax,
			@PageableDefault (
					size= 5,
					sort= "precio",
					direction = Sort.Direction.ASC
					)
			Pageable pageable,
			Model modelo) {
		
		Page<Producto> pagina = productoService.buscar(
				categoria, precioMin, precioMax, pageable);
		
		modelo.addAttribute("pagina",pagina);
		
		return VISTA;
		
	}
	
	@GetMapping({"", "/"})
	public ModelAndView listProductos() {

	    ModelAndView mav = new ModelAndView("productos");
	    mav.addObject("productos", productoService.listarProductos());

	    return mav;
	}
	
	@GetMapping("/formProducto")
	public String formProducto(Model model) {
		model.addAttribute("producto", new Producto());
		model.addAttribute("categorias", categoriaService.listarCategorias());
		model.addAttribute("editando", false);
		return FORM_VIEW;
	}
	
	@PostMapping("/formProducto")
	public String addProducto(@ModelAttribute("producto") Producto producto) {
		productoService.crearProducto(producto);
		return "redirect:/productos";
	}
	
	
	@PostMapping("/{id}/eliminarProducto")
	public String eliminarProducto(@PathVariable long id, @ModelAttribute("producto") Producto producto) {
		productoService.eliminarProducto(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/{id}/editar")
	public String editarProducto(long id, Model model) {
		Producto p = productoService.buscarPorId(id);
		
		model.addAttribute("producto", p);
		model.addAttribute("editando", true);
		model.addAttribute("categorias", categoriaService.listarCategorias());
		
		return FORM_VIEW;
	}
	
	@PostMapping("/{id}/editar")
	public String editar(@PathVariable long id, @ModelAttribute("producto") Producto producto) {

		productoService.crearProducto(producto);

		return "redirect:/productos";
	}
	
	@GetMapping("/listarProductosDTO")
	public String listarProductosDTO(Model model) {
		model.addAttribute("productos", productoService.listarProductosDTO());
		return "listaDTO";
	}
	

}
