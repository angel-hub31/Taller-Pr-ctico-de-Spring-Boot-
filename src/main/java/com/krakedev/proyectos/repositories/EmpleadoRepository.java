package com.krakedev.proyectos.repositories;

import com.krakedev.proyectos.entidades.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Repository: Indica que esta interfaz es un componente de persistencia 
 * de datos, permitiendo a Spring manejar excepciones de base de datos.
 * * JpaRepository<Empleado, Integer>: 
 * - Empleado: Es la entidad que gestionará.
 * - Integer: Es el tipo de dato de su clave primaria (ID).
 */
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    // Al extender JpaRepository, obtienes automáticamente métodos como:
    // save(), findAll(), findById(), deleteById(), existsById(), etc.
    // ¡Sin escribir una sola línea de código SQL!
}