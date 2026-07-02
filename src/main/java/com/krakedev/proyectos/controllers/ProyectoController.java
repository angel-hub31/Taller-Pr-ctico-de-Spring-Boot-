package com.krakedev.proyectos.controllers;

import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.services.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap; 
import java.util.Map;     


@RestController//Es una anotación compuesta que le indica a Spring que esta clase es un controlador web.
@RequestMapping("/api/proyectos") // Base para todas las rutas de este controlador

@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = {"Authorization", "Content-Type"})
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    // GET: Lista todos los proyectos. Accesible para ADMIN y USER
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(proyectoService.obtenerTodos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // GET: Busca un proyecto específico por su ID. Accesible para ADMIN y USER
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        try {
            Proyecto p = proyectoService.obtenerPorId(id);
            return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // POST: Crea un nuevo proyecto. Solo para ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> crear(@RequestBody Proyecto proyecto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(proyectoService.guardar(proyecto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // PUT: Actualiza un proyecto existente. Solo para ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Proyecto proyecto) {
        try {
            proyecto.setId(id); // Asegura que el ID del objeto coincida con el de la URL
            return ResponseEntity.ok(proyectoService.guardar(proyecto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // DELETE: Elimina un proyecto por ID. Solo para ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        try {
            proyectoService.eliminar(id);
            return ResponseEntity.ok("Proyecto eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    
    @GetMapping("/publico/resumen")
    public ResponseEntity<?> resumenPublico() {


        Map<String, Object> respuesta = new HashMap<>();


        respuesta.put(
                "cantidadProyectos",
                proyectoService.contarProyectos()
        );


        return ResponseEntity.ok(respuesta);

    }
}