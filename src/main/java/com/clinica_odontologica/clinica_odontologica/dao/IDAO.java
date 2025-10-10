package com.clinica_odontologica.clinica_odontologica.dao;

import java.util.List;

public interface IDAO<T> {
    // Guardar
    T guardar(T t);

    // Buscar
    T bucar(Integer id);
    List<T> buscarTodos();

    // Eliminar
    void eliminar(T t);

    // Modificar
    void modificar(T t);

}
