package com.krakedev.proyectos.controllers;

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController//Es una anotación compuesta que le indica a Spring que esta clase es un controlador web.
@RequestMapping("/api/empleados")//Cualquier petición web que llegue con la ruta

@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = {"Authorization", "Content-Type"})
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    // Obtener todos: Permitido para roles ADMIN y USER
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(empleadoService.obtenerTodos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Buscar por ID: Permitido para roles ADMIN y USER
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        try {
            Empleado emp = empleadoService.obtenerPorId(id);
            return emp != null ? ResponseEntity.ok(emp) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Crear: Solo permitido para rol ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> crear(@RequestBody Empleado empleado) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.guardar(empleado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Actualizar: Solo permitido para rol ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Empleado empleado) {
        try {
            empleado.setId(id);
            return ResponseEntity.ok(empleadoService.guardar(empleado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Eliminar: Solo permitido para rol ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        try {
            empleadoService.eliminar(id);
            return ResponseEntity.ok("Empleado eliminado Correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}