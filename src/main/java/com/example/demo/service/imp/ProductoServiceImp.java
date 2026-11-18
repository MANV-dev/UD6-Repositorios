package com.example.demo.service.imp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ProductoDTO;
import com.example.demo.entity.Producto;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.service.AlmacenamientoService;
import com.example.demo.service.ProductoService;

@Service
public class ProductoServiceImp implements ProductoService{

	private ProductoRepository productoRepository;
	private AlmacenamientoService almacenamientoService;
	
	
	public ProductoServiceImp(ProductoRepository productoRepository, AlmacenamientoService almacenamientoService) {
		this.productoRepository = productoRepository;
		this.almacenamientoService = almacenamientoService;
	}
	public void guardarImagen(Long id, MultipartFile archivo)
            throws IOException {

        // Buscar producto
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado"));

        // Guardar imagen en disco
        String nombreImagen =
                almacenamientoService.guardarImagen(archivo);

        // Actualizar producto
        producto.setNombreImagen(nombreImagen);

        // Guardar cambios en MySQL
        productoRepository.save(producto);
    }
	
	@Override
	public Producto crearProducto(Producto p) {
		return productoRepository.save(p);
		
	}

	@Override
	public Producto buscarPorId(long id) { 
		return productoRepository.findById(id).orElse(null);
	}

	@Override
	public List<Producto> listarProductos() {
		return productoRepository.findAll();
	}

	@Override
	public Producto modificarProducto(Producto p) {
		return productoRepository.save(p);
		
	}

	@Override
	public void eliminarProducto(Producto p) throws IOException { // El objeto es todo null menos el id por la cara
		Producto pEntero = buscarPorId(p.getId());
		almacenamientoService.borrarImagen(pEntero.getNombreImagen());
		productoRepository.deleteById(p.getId());
		
	}
	@Override
	public List<Producto> buscarPorNombre(String nombre) {
		return productoRepository.findByNombre(nombre);
	}

	@Override
	public List<Producto> buscarPorCadena(String cadena) {
		return productoRepository.findByNombreContaining(cadena);
	}
	@Override
	public List<Producto> buscarEntrePrecios(Double min,Double max){
		return productoRepository.findByPrecioBetween(min, max);
	}
	
	@Override
	public List<Producto> buscarEntrePreciosOrdenNombre(Double min,Double max){
		
		List<Producto> prods= productoRepository.findByPrecioBetween(min, max);

		return prods.stream().sorted(Comparator.comparing(Producto::getNombre)).toList();
	}

	
	@Override
	public List<Producto> buscarEntrePreciosOrdenPrecio(Double min,Double max){
		
		List<Producto> prods= productoRepository.findByPrecioBetween(min, max);

		return prods.stream().sorted(Comparator.comparing(Producto::getPrecio)).toList();
	}

	@Override
	public List<Producto> buscarPorCategoria(String nombre) {
		
		return productoRepository.findByCategoriaNombre(nombre);
	}

	//Pageable
	
	@Override
	public Page<Producto> buscar(String categoria, Double precioMin, Double precioMax, Pageable pageable) {
		
		return productoRepository.findByCategoriaNombreAndPrecioBetween(categoria, precioMin, precioMax, pageable);
	}

	private ProductoDTO transform(Producto producto) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(producto, ProductoDTO.class);
	}
	
	@Override
	public List<ProductoDTO> listarProductosDTO() {
		List<Producto> productos = productoRepository.findAll();
		List<ProductoDTO> dto = new ArrayList<>();
		for(Producto p: productos) {
			dto.add(transform(p));
		}
		return dto;
	}
	@Override
	public void subirImagen(long id, MultipartFile archivo) throws IOException {
		Producto p = buscarPorId(id);
		String nombreImagen = almacenamientoService.guardarImagen(archivo);
		p.setNombreImagen(nombreImagen);
		productoRepository.save(p);
	}
}