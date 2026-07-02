package com.krakedev.proyectos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Habilita la configuración automática y el escaneo de componentes de Spring
@SpringBootApplication
public class ProyectosApplication {

	// Punto de entrada de la aplicación
	public static void main(String[] args) {
		// Inicia el contexto de Spring y el servidor embebido (Tomcat)
		SpringApplication.run(ProyectosApplication.class, args);
	}

}