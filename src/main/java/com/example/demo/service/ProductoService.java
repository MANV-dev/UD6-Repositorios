package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ProductoDTO;
import com.example.demo.entity.Producto;

public interface ProductoService {
	
	Producto crearProducto(Producto p);
	Producto buscarPorId(long id);
	List<Producto> listarProductos();
	Producto modificarProducto(Producto p);
	void eliminarProducto(Producto p2) throws IOException;

	List<Producto> buscarPorNombre(String nombre);
	List<Producto> buscarPorCadena(String cadena);
	
	List<Producto> buscarEntrePrecios(Double min,Double max);
	List<Producto> buscarEntrePreciosOrdenNombre(Double min,Double max);
	List<Producto> buscarEntrePreciosOrdenPrecio(Double min,Double max);
	
	List<Producto> buscarPorCategoria(String nombre);
	
	//Pageable
    Page<Producto> buscar(String categoria,Double precioMin,Double precioMax,Pageable pageable); 
    
    List<ProductoDTO> listarProductosDTO();
    
    void subirImagen(long id, MultipartFile archivo) throws IOException;
    
	
}