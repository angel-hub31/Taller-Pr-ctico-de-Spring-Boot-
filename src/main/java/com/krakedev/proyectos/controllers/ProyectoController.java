package com.krakedev.proyectos.controllers;

import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.services.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

	@Autowired
	private ProyectoService proyectoService;

	@GetMapping
	public ResponseEntity<?> listar() {
		try {
			return ResponseEntity.ok(proyectoService.obtenerTodos());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable int id) {
		try {
			Proyecto p = proyectoService.obtenerPorId(id);
			return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Proyecto proyecto) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(proyectoService.guardar(proyecto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Proyecto proyecto) {
		try {
			proyecto.setId(id);
			return ResponseEntity.ok(proyectoService.guardar(proyecto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable int id) {
		try {
			proyectoService.eliminar(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}