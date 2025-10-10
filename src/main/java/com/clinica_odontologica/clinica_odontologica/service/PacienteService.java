package com.clinica_odontologica.clinica_odontologica.service;

import com.clinica_odontologica.clinica_odontologica.dao.IDAO;
import com.clinica_odontologica.clinica_odontologica.model.Paciente;

import java.time.LocalDate;
import java.util.List;

public class PacienteService {
    private IDAO<Paciente> pacienteDAO;

    public PacienteService(IDAO<Paciente> pacienteDAO) {
        this.pacienteDAO = pacienteDAO;
    }

    // GUARDAR PACIENTE
    public Paciente guardarPaciente(Paciente paciente) {
        validarPaciente(paciente);

        // Verificamos si ya existe un paciente con el mismo email (TODO: Si es necesario, agregamos más validaciones)
        List<Paciente> existenten = pacienteDAO.buscarTodos();
        boolean emailDuplicado = existenten.stream()
                .anyMatch(p -> p.getEmail().equalsIgnoreCase(paciente.getEmail()));

        if (emailDuplicado) {
            // TODO: Crear a futuro  carpeta Exceptions para Excepciones custom (Response Entity)
            throw new IllegalArgumentException("❌ Ya existe un paciente con el mismo email: " + paciente.getEmail() + " ❌");
        }

        Paciente guardado = pacienteDAO.guardar(paciente);

        if(guardado == null) {
            throw new IllegalArgumentException("❌ El paciente no se pudo guardar ❌");
        }

        System.out.println("✅ Paciente guardado correctamente: " + guardado.getId());

        return guardado;
    }

    // LISTADO TODOS LOS PACIENTES
    public List<Paciente> buscarPacientes(){
        List<Paciente> listadoPacientes = pacienteDAO.buscarTodos();

        if (listadoPacientes.isEmpty()) {
            System.out.println("❌ No hay pacientes registrados. ❌");
        }

        return listadoPacientes;
    }

    // BUSCAR PACIENTE POR ID
    public Paciente buscarPacientePorId(Integer id){
        if (id == null) {
            throw new IllegalArgumentException("❌ El ID del paciente no puede ser null ❌");
        }

        Paciente paciente = pacienteDAO.buscar(id);

        if (paciente == null) {
            System.out.println("❌ No se encontró el paciente con ID " + id + " ❌");
        }
        return paciente;
    }

    // ELIMINAR PACIENTE
    public List<Paciente> eliminarPaciente(Integer id){
        if (id == null) {
            throw new IllegalArgumentException("❌ El ID del paciente no puede ser null. ❌");
        }

        Paciente paciente = pacienteDAO.buscar(id);
        if (paciente == null) {
            throw new IllegalArgumentException("❌ No se puede eliminar: no existe un paciente con ID " + id + " ❌");
        }

        pacienteDAO.eliminar(id);

        System.out.println("✅ Paciente eliminado con ID " + id + " ✅");

        List<Paciente> pacientesRestantes = pacienteDAO.buscarTodos();

        return pacientesRestantes;
    }

    // MODIFICAR PACIENTE
    public Paciente modificarPaciente(Paciente paciente){
        validarPaciente(paciente);

        if (paciente.getId() <= 0) {
            throw new IllegalArgumentException("❌ El ID del paciente a modificar es inválido. ❌");
        }

        Paciente existente = pacienteDAO.buscar(paciente.getId());

        if (existente == null) {
            throw new IllegalArgumentException("❌ No existe un paciente con ID " + paciente.getId() + " ❌");
        }

        Paciente actualizado = pacienteDAO.modificar(paciente);

        if (actualizado == null) {
            throw new RuntimeException("❌ Error al intentar modificar el paciente con ID " + paciente.getId() + " ❌");
        }

        System.out.println("✅ Paciente actualizado correctamente: " + paciente.getId() + " ✅");

        return actualizado;
    }

    private void validarPaciente(Paciente paciente) {

        // TODO: CREAR EXCEPTIONES CUSTOM A FUTURO PARA DEVOLVER CON RESPONSE ENTITY
        if (paciente == null) {
            throw new IllegalArgumentException("❌ El paciente no puede ser nulo. ❌");
        }
        if (isEmptyString(paciente.getNombre()) || isEmptyString(paciente.getApellido())) {
            throw new IllegalArgumentException("❌ El paciente debe tener nombre y apellido. ❌");
        }
        if (paciente.getNumeroContacto() <= 0) {
            throw new IllegalArgumentException("❌ El número de contacto debe ser mayor que cero. ❌");
        }
        if (isEmptyString(paciente.getEmail()) || !paciente.getEmail().contains("@")) {
            throw new IllegalArgumentException("❌ El email del paciente es inválido. ❌");
        }
        if (paciente.getFechaIngreso() == null || paciente.getFechaIngreso().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("❌ La fecha de ingreso es inválida ❌");
        }
    }

    // Helper
    private boolean isEmptyString(String s) {
        return s == null || s.trim().isEmpty();
    }
}
