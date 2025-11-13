package com.clinica_odontologica.clinica_odontologica;

import com.clinica_odontologica.clinica_odontologica.dto.OdontologoDTO;
import com.clinica_odontologica.clinica_odontologica.service.OdontologoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional // cada test se ejecuta y revierte sus cambios automáticamente
public class OdontologoTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @DisplayName("Guardar odontólogo correctamente")
    public void GuardarOdontologo() {
        OdontologoDTO dto = new OdontologoDTO();
        dto.setNombre("Lisa");
        dto.setApellido("Simpson");
        dto.setMatricula(12345);

        OdontologoDTO guardado = odontologoService.guardarOdontologo(dto);

        Assertions.assertNotNull(guardado.getId());
        Assertions.assertEquals("Lisa", guardado.getNombre());
    }

    @Test
    @DisplayName("Listar odontólogos existentes")
    public void ListarOdontologos() {
        List<OdontologoDTO> lista = odontologoService.listarOdontologos();
        Assertions.assertNotNull(lista);
    }

    @Test
    @DisplayName("Buscar odontólogo por ID")
    public void BuscarPorId() {
        OdontologoDTO dto = new OdontologoDTO();
        dto.setNombre("Homero");
        dto.setApellido("Simpson");
        dto.setMatricula(77777);

        OdontologoDTO guardado = odontologoService.guardarOdontologo(dto);
        OdontologoDTO encontrado = odontologoService.buscarOdontologoPorId(guardado.getId());

        Assertions.assertEquals("Homero", encontrado.getNombre());
    }

    @Test
    @DisplayName("Modificar odontólogo existente")
    public void ModificarOdontologo() {
        OdontologoDTO dto = new OdontologoDTO();
        dto.setNombre("Bart");
        dto.setApellido("Simpson");
        dto.setMatricula(88888);

        OdontologoDTO guardado = odontologoService.guardarOdontologo(dto);

        guardado.setNombre("Bartolomeo");
        OdontologoDTO actualizado = odontologoService.editarOdontologos(guardado.getId(), guardado);

        Assertions.assertEquals("Bartolomeo", actualizado.getNombre());
    }

    @Test
    @DisplayName("Eliminar odontólogo correctamente")
    public void EliminarOdontologo() {
        OdontologoDTO dto = new OdontologoDTO();
        dto.setNombre("Marge");
        dto.setApellido("Bouvier");
        dto.setMatricula(99999);

        OdontologoDTO guardado = odontologoService.guardarOdontologo(dto);

        String msj = odontologoService.eliminarOdontologo(guardado.getId());
        Assertions.assertTrue(msj.contains("eliminado") || msj.contains("Eliminado"));
    }
}
