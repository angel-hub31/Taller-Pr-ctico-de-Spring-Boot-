package com.krakedev.proyectos.controllers;

import com.krakedev.proyectos.entidades.Tarea;
import com.krakedev.proyectos.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap; 
import java.util.Map;    
@RestController
@RequestMapping("/api/tareas") // Ruta base para gestionar tareas
public class TareaController {

    @Autowired
    private TareaService tareaService;

    // GET: Obtiene la lista completa de tareas. Permitido para ADMIN y USER
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(tareaService.obtenerTodas());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // POST: Crea una nueva tarea. Restringido solo para ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> crear(@RequestBody Tarea tarea) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(tareaService.guardar(tarea));
        } catch (Exception e) {
        	
        	
        	 Map<String, String> error = new HashMap<>();
             error.put("error", e.getMessage());

             return ResponseEntity
                     .status(HttpStatus.BAD_REQUEST)
                     .body(error);
        }
    }
}