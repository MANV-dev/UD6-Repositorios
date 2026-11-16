package com.example.demo.dto;

public class ProductoDTO {
	
	private String nombre;
	private double precio;
	
	public ProductoDTO(String nombre, double precio) {
		super();
		this.nombre = nombre;
		this.precio = precio;
	}

	public ProductoDTO() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "ProductoDTO [nombre=" + nombre + ", precio=" + precio + "]";
	}
	
	
	
	

}
