package com.krakedev.proyectos.services;

import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.repositories.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProyectoService {

	@Autowired
	private ProyectoRepository proyectoRepository;

	public List<Proyecto> obtenerTodos() {
		return proyectoRepository.findAll();
	}

	public Proyecto obtenerPorId(int id) {
		return proyectoRepository.findById(id).orElse(null);
	}

	public Proyecto guardar(Proyecto proyecto) {
		return proyectoRepository.save(proyecto);
	}

	public void eliminar(int id) {
		proyectoRepository.deleteById(id);
	}
}