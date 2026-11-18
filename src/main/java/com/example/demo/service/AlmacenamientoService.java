package com.example.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class AlmacenamientoService {

	@Value("${app.upload.dir}")
	private String directorioSubidas;

	public String guardarImagen(MultipartFile archivo) throws IOException {

		if (archivo == null || archivo.isEmpty()) {
			throw new IllegalArgumentException("El archivo está vacío");
		}

		String tipo = archivo.getContentType();

		if (!List.of("image/jpeg", "image/png").contains(tipo)) {
			throw new IllegalArgumentException("Formato no permitido");
		}
		String extension = tipo.equals("image/png")
                ? ".png" : ".jpg";

        String nombre = UUID.randomUUID() + extension;

        Path directorio = Paths.get(directorioSubidas)
                .toAbsolutePath().normalize();

        Files.createDirectories(directorio);

        Path destino = directorio.resolve(nombre);

        try (InputStream in = archivo.getInputStream()) {
            Files.copy(in, destino);
        }

        return nombre;
    }
	
	public void borrarImagen(String nombreImagen) throws IOException {

	    if (nombreImagen == null || nombreImagen.isBlank()) {
	        return;
	    }

	    Path directorio = Paths.get(directorioSubidas)
	            .toAbsolutePath().normalize();

	    Path archivo = directorio.resolve(nombreImagen).normalize();

	    System.out.println("\n\n\n"+archivo);
	    Files.deleteIfExists(archivo);
	}
}