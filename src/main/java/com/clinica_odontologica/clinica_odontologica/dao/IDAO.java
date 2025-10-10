package com.clinica_odontologica.clinica_odontologica.dao;

import java.util.List;

public interface IDAO<T> {
	// Guardar
	T guardar(T t);

	// Buscar
	T buscar(Integer id);

	// Eliminar
	void eliminar(Integer id);

	// Modificar

	T modificar(T t);

	List<T> buscarTodos();
}
