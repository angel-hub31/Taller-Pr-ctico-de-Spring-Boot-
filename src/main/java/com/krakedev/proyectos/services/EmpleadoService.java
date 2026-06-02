package com.krakedev.proyectos.services;

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmpleadoService {

	@Autowired
	private EmpleadoRepository empleadoRepository;

	public List<Empleado> obtenerTodos() {
		return empleadoRepository.findAll();
	}

	public Empleado obtenerPorId(int id) {
		return empleadoRepository.findById(id).orElse(null);
	}

	public Empleado guardar(Empleado empleado) {
		return empleadoRepository.save(empleado);
	}

	public void eliminar(int id) {
		empleadoRepository.deleteById(id);
	}
}