package com.krakedev.proyectos.services;

import com.krakedev.proyectos.entidades.Tarea;
import com.krakedev.proyectos.repositories.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @Service: Indica que esta clase gestiona la lógica de negocio 
 * relacionada con las tareas de los proyectos.
 */
@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    // Recupera la lista completa de tareas desde la base de datos
    public List<Tarea> obtenerTodas() {
        return tareaRepository.findAll();
    }

    // Persiste una tarea (creación o actualización) en la base de datos
    public Tarea guardar(Tarea tarea) {
        return tareaRepository.save(tarea);
    }
}