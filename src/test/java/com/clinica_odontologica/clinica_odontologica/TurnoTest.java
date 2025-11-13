package com.clinica_odontologica.clinica_odontologica;

import com.clinica_odontologica.clinica_odontologica.dto.OdontologoDTO;
import com.clinica_odontologica.clinica_odontologica.dto.PacienteDTO;
import com.clinica_odontologica.clinica_odontologica.dto.TurnoDTO;
import com.clinica_odontologica.clinica_odontologica.entity.Domicilio;
import com.clinica_odontologica.clinica_odontologica.service.OdontologoService;
import com.clinica_odontologica.clinica_odontologica.service.PacienteService;
import com.clinica_odontologica.clinica_odontologica.service.TurnoService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
public class TurnoTest {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @DisplayName("Crear turno correctamente")
    public void crearTurno() {
        // Primero guardar odontologo para tener ID válido
        OdontologoDTO odontologo = new OdontologoDTO();
        odontologo.setNombre("Homero");
        odontologo.setApellido("Simpson");
        odontologo.setMatricula(12345);
        OdontologoDTO odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

        // Primero guardar paciente para tener ID válido
        PacienteDTO paciente = new PacienteDTO();
        paciente.setNombre("Lisa");
        paciente.setApellido("Simpson");
        paciente.setNumeroContacto(11223344);
        paciente.setEmail("lisa@disney.com");
        paciente.setFechaIngreso(LocalDate.of(2025, 10, 9));

        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle Falsa");
        domicilio.setNumero(123);
        domicilio.setLocalidad("Springfield");
        domicilio.setProvincia("USA");
        paciente.setDomicilio(domicilio);

        PacienteDTO pacienteGuardado = pacienteService.guardarPaciente(paciente);

        // Ahora sí creamos el turno con odontologo y paciente que ya tienen ID
        TurnoDTO turno = new TurnoDTO();
        turno.setPaciente(pacienteGuardado);
        turno.setOdontologo(odontologoGuardado);
        turno.setFecha(LocalDate.now());

        TurnoDTO guardado = turnoService.guardarTurno(turno);


        Assertions.assertNotNull(guardado.getId());
    }


    @Test
    @DisplayName("Listar todos los turnos")
    public void listarTurnos() {
        List<TurnoDTO> turnos = turnoService.listarTurnos();

        Assertions.assertNotNull(turnos);
    }

    @Test
    @DisplayName("Modificar fecha de un turno")
    public void modificarTurno() {
        List<TurnoDTO> turnos = turnoService.listarTurnos();
        if(turnos.isEmpty()) {
            return;
        }

        TurnoDTO turno = turnos.get(0);
        LocalDate nuevaFecha = LocalDate.now().plusDays(5);
        turno.setFecha(nuevaFecha);

        TurnoDTO modificado = turnoService.editarTurno(turno.getId(), turno);

        Assertions.assertEquals(nuevaFecha, modificado.getFecha());
    }

    @Test
    @DisplayName("Eliminar un turno existente")
    public void eliminarTurno() {
        List<TurnoDTO> turnos = turnoService.listarTurnos();
        if(turnos.isEmpty()) {

            return;
        }

        TurnoDTO turno = turnos.get(0);
        String mensaje = turnoService.eliminarTurnoPorId(turno.getId());


        Assertions.assertThrows(RuntimeException.class, () -> turnoService.buscarTurnoPorId(turno.getId()));
    }
}
