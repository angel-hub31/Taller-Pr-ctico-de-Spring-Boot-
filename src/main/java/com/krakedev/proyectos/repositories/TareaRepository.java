package com.krakedev.proyectos.repositories;

import com.krakedev.proyectos.entidades.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Repository: Marca esta interfaz como un componente de acceso a datos.
 * * JpaRepository<Tarea, Integer>:
 * - Tarea: Entidad que se gestionará.
 * - Integer: Tipo del ID (la clave primaria de la entidad Tarea).
 */
@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {
    // Al extender JpaRepository, obtienes métodos listos para usar como:
    // - save(T entity): Guardar o actualizar.
    // - findAll(): Listar todos los registros.
    // - findById(ID id): Buscar por ID.
    // - deleteById(ID id): Eliminar un registro.
}