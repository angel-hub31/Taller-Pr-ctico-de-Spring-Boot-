package com.krakedev.proyectos.controllers;

import com.krakedev.proyectos.entidades.Tarea;
import com.krakedev.proyectos.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

	@Autowired
	private TareaService tareaService;

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<?> listar() {
		try {
			return ResponseEntity.ok(tareaService.obtenerTodas());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> crear(@RequestBody Tarea tarea) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(tareaService.guardar(tarea));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}