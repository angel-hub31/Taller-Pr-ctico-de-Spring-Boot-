package com.krakedev.proyectos.services;

import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.repositories.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @Service: Marca esta clase como un componente de servicio, 
 * encargada de gestionar la lógica de negocio para los proyectos.
 */
@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    // Obtiene todos los proyectos almacenados en la base de datos
    public List<Proyecto> obtenerTodos() {
        return proyectoRepository.findAll();
    }

    // Busca un proyecto por ID. Devuelve el proyecto si existe, o null si no.
    public Proyecto obtenerPorId(int id) {
        return proyectoRepository.findById(id).orElse(null);
    }

    // Guarda un nuevo proyecto o actualiza uno existente mediante el ID
    public Proyecto guardar(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    // Elimina un proyecto de la base de datos utilizando su identificador
    public void eliminar(int id) {
        proyectoRepository.deleteById(id);
    }
    
    public long contarProyectos() {

        return proyectoRepository.count();

    }
}