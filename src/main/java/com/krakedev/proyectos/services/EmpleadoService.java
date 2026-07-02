package com.krakedev.proyectos.services;

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @Service: Indica a Spring que esta clase es un componente de servicio.
 * Aquí se encapsula la lógica de negocio antes de tocar los datos.
 */
@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Retorna todos los empleados registrados
    public List<Empleado> obtenerTodos() {
        return empleadoRepository.findAll();
    }

    // Busca un empleado. .orElse(null) es vital: si no existe, devuelve null 
    // en lugar de lanzar una excepción
    public Empleado obtenerPorId(int id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    // Guarda o actualiza un empleado. JPA decide si es INSERT o UPDATE 
    // basándose en si el ID ya existe en la base de datos
    public Empleado guardar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    // Elimina un empleado por su identificador
    public void eliminar(int id) {
        empleadoRepository.deleteById(id);
    }
}