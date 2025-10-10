package com.clinica_odontologica.clinica_odontologica.service;

import java.util.List;

public interface ISERVICE<T> {
	// Guardar
	T guardar(T t);

	// Buscar
	T buscar(Integer id);

	List<T> buscarTodos();

	// Eliminar
	List<T> eliminar(Integer id);

	// Modificar
	T modificar(T t);

}
