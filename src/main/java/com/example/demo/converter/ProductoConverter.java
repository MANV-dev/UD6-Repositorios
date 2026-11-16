package com.example.demo.converter;

import org.springframework.stereotype.Component;
import com.example.demo.dto.ProductoDTO;
import com.example.demo.entity.Producto;

@Component("productoConverter")
public class ProductoConverter {
	
	public ProductoDTO entity2model(Producto prod) {

		ProductoDTO prodDTO = new ProductoDTO();
	
		prodDTO.setNombre(prod.getNombre());
		prodDTO.setPrecio(prod.getPrecio());
		
		return prodDTO;
		
	}
	
	public Producto model2entity(ProductoDTO prodDTO) {

		Producto prod = new Producto();
		
		prod.setNombre(prodDTO.getNombre());
		prod.setPrecio(prodDTO.getPrecio());

		return prod;
	}
	
	
	
	

}
