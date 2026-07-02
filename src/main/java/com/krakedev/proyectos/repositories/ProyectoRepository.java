package com.krakedev.proyectos.repositories;

import com.krakedev.proyectos.entidades.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Repository: Define esta interfaz como un componente que gestiona datos.
 * JpaRepository<Proyecto, Integer>: 
 * - Proyecto: Entidad que se va a persistir.
 * - Integer: Tipo de dato del identificador (ID) de la entidad.
 */
@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
    // Hereda todos los métodos CRUD básicos (save, findAll, delete, etc.)
    // de forma automática desde JpaRepository.
}