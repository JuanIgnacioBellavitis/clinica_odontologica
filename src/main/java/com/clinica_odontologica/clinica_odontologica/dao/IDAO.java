package com.clinica_odontologica.clinica_odontologica.dao;

import java.util.List;

public interface IDAO<T> {
	// Guardar
	T guardar(T t);

	// Buscar
	T buscar(Integer id);

	// Buscar
	T buscarPorString(String string);

	// Eliminar
	void eliminar(Integer id);

	// Modificar

	T modificar(T t);

	List<T> buscarTodos();
}
