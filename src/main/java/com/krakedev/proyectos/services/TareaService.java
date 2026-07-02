package com.krakedev.proyectos.services;

import com.krakedev.proyectos.entidades.Tarea;
import com.krakedev.proyectos.repositories.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TareaService {

	@Autowired
	private TareaRepository tareaRepository;

	// Recupera la lista completa de tareas desde la base de datos
	public List<Tarea> obtenerTodas() {
		return tareaRepository.findAll();
	}

	// Persiste una tarea (creación o actualización) en la base de datos
	public Tarea guardar(Tarea tarea) throws Exception {

		String prioridad = tarea.getPrioridad();

		if (prioridad == null || !(prioridad.equals("ALTA") || prioridad.equals("MEDIA") || prioridad.equals("BAJA"))) {

			throw new Exception("Prioridad no válida");

		}
		return tareaRepository.save(tarea);

	}
}