package com.krakedev.proyectos.entidades;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "proyectos")
public class Proyecto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 100)
	private String nombre;

	@Column(length = 250)
	private String description; // El PDF indica descripcion en minúsculas

	@Column(nullable = false)
	private LocalDate fechaInicio;

	@OneToMany(mappedBy = "proyecto")
	@JsonIgnore
	private List<Tarea> tareas;

	// Constructor vacío
	public Proyecto() {
	}

	// Constructor con parámetros 
	public Proyecto(int id, String nombre, String description, LocalDate fechaInicio) {
		this.id = id;
		this.nombre = nombre;
		this.description = description;
		this.fechaInicio = fechaInicio;
	}

	// Getters y Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public List<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}
}