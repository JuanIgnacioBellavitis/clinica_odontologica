package com.clinica_odontologica.clinica_odontologica;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import com.clinica_odontologica.clinica_odontologica.dto.OdontologoDTO;
import com.clinica_odontologica.clinica_odontologica.exceptions.NotFoundException;
import com.clinica_odontologica.clinica_odontologica.service.OdontologoService;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OdontologoIntegrationTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @DisplayName("Integración: Guardar odontólogo correctamente")
    public void guardarOdontologo_Integration() {
        // Arrange
        OdontologoDTO dto = new OdontologoDTO();
        dto.setNombre("Lisa");
        dto.setApellido("Simpson");
        dto.setMatricula(12345);

        // Act
        OdontologoDTO guardado = odontologoService.guardarOdontologo(dto);

        // Assert
        Assertions.assertNotNull(guardado.getId());
        Assertions.assertEquals("Lisa", guardado.getNombre());
        Assertions.assertEquals("Simpson", guardado.getApellido());
        Assertions.assertEquals(12345, guardado.getMatricula());
    }

    @Test
    @DisplayName("Integración: Guardar odontólogo con matrícula duplicada debe fallar")
    public void guardarOdontologo_MatriculaDuplicada_Integration() {
        // Arrange
        OdontologoDTO dto1 = new OdontologoDTO();
        dto1.setNombre("Homero");
        dto1.setApellido("Simpson");
        dto1.setMatricula(12345);

        OdontologoDTO dto2 = new OdontologoDTO();
        dto2.setNombre("Bart");
        dto2.setApellido("Simpson");
        dto2.setMatricula(12345); // Misma matrícula

        // Act
        odontologoService.guardarOdontologo(dto1);

        // Assert
        Assertions.assertThrows(Exception.class, () -> {
            odontologoService.guardarOdontologo(dto2);
        });
    }

    @Test
    @DisplayName("Integración: Listar odontólogos existentes")
    public void listarOdontologos_Integration() {
        // Arrange
        OdontologoDTO dto1 = new OdontologoDTO();
        dto1.setNombre("Lisa");
        dto1.setApellido("Simpson");
        dto1.setMatricula(11111);

        OdontologoDTO dto2 = new OdontologoDTO();
        dto2.setNombre("Homero");
        dto2.setApellido("Simpson");
        dto2.setMatricula(22222);

        odontologoService.guardarOdontologo(dto1);
        odontologoService.guardarOdontologo(dto2);

        // Act
        List<OdontologoDTO> lista = odontologoService.listarOdontologos();

        // Assert
        Assertions.assertNotNull(lista);
        Assertions.assertTrue(lista.size() >= 2);
    }

    @Test
    @DisplayName("Integración: Buscar odontólogo por ID")
    public void buscarPorId_Integration() {
        // Arrange
        OdontologoDTO dto = new OdontologoDTO();
        dto.setNombre("Homero");
        dto.setApellido("Simpson");
        dto.setMatricula(77777);

        OdontologoDTO guardado = odontologoService.guardarOdontologo(dto);

        // Act
        OdontologoDTO encontrado = odontologoService.buscarOdontologoPorId(guardado.getId());

        // Assert
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("Homero", encontrado.getNombre());
        Assertions.assertEquals("Simpson", encontrado.getApellido());
        Assertions.assertEquals(77777, encontrado.getMatricula());
    }

    @Test
    @DisplayName("Integración: Buscar odontólogo por ID - No encontrado")
    public void buscarPorId_NoEncontrado_Integration() {
        // Act & Assert
        NotFoundException exception = Assertions.assertThrows(
            NotFoundException.class,
            () -> odontologoService.buscarOdontologoPorId(99999L)
        );

        Assertions.assertTrue(exception.getMessage().contains("No se encontró el odontologo con el ID"));
    }

    @Test
    @DisplayName("Integración: Buscar odontólogo por nombre")
    public void buscarPorNombre_Integration() {
        // Arrange
        OdontologoDTO dto = new OdontologoDTO();
        dto.setNombre("Marge");
        dto.setApellido("Bouvier");
        dto.setMatricula(88888);

        odontologoService.guardarOdontologo(dto);

        // Act
        OdontologoDTO encontrado = odontologoService.buscarOdontologoPorNombre("Marge");

        // Assert
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("Marge", encontrado.getNombre());
        Assertions.assertEquals("Bouvier", encontrado.getApellido());
    }

    @Test
    @DisplayName("Integración: Buscar odontólogo por nombre - No encontrado")
    public void buscarPorNombre_NoEncontrado_Integration() {
        // Act & Assert
        NotFoundException exception = Assertions.assertThrows(
            NotFoundException.class,
            () -> odontologoService.buscarOdontologoPorNombre("NoExiste")
        );

        Assertions.assertTrue(exception.getMessage().contains("No se encontró odontologo con el nombre"));
    }

    @Test
    @DisplayName("Integración: Modificar odontólogo existente")
    public void modificarOdontologo_Integration() {
        // Arrange
        OdontologoDTO dto = new OdontologoDTO();
        dto.setNombre("Bart");
        dto.setApellido("Simpson");
        dto.setMatricula(99999);

        OdontologoDTO guardado = odontologoService.guardarOdontologo(dto);

        // Act
        guardado.setNombre("Bartolomeo");
        guardado.setApellido("Simpson Jr");
        OdontologoDTO actualizado = odontologoService.editarOdontologos(guardado.getId(), guardado);

        // Assert
        Assertions.assertNotNull(actualizado);
        Assertions.assertEquals("Bartolomeo", actualizado.getNombre());
        Assertions.assertEquals("Simpson Jr", actualizado.getApellido());
        Assertions.assertEquals(guardado.getId(), actualizado.getId());
    }

    @Test
    @DisplayName("Integración: Eliminar odontólogo correctamente")
    public void eliminarOdontologo_Integration() {
        // Arrange
        OdontologoDTO dto = new OdontologoDTO();
        dto.setNombre("Marge");
        dto.setApellido("Bouvier");
        dto.setMatricula(10101);

        OdontologoDTO guardado = odontologoService.guardarOdontologo(dto);
        Long id = guardado.getId();

        // Act
        String msj = odontologoService.eliminarOdontologo(id);

        // Assert
        Assertions.assertNotNull(msj);
        Assertions.assertTrue(msj.contains("eliminado") || msj.contains("Eliminado"));

        // Verificar que ya no existe
        Assertions.assertThrows(NotFoundException.class, () -> {
            odontologoService.buscarOdontologoPorId(id);
        });
    }

    @Test
    @DisplayName("Integración: Eliminar odontólogo - No encontrado")
    public void eliminarOdontologo_NoEncontrado_Integration() {
        // Act & Assert
        NotFoundException exception = Assertions.assertThrows(
            NotFoundException.class,
            () -> odontologoService.eliminarOdontologo(99999L)
        );

        Assertions.assertTrue(exception.getMessage().contains("No se encontró el odontologo con ID"));
    }

    @Test
    @DisplayName("Integración: Flujo completo CRUD de odontólogo")
    public void flujoCompletoCRUD_Integration() {
        // CREATE
        OdontologoDTO dto = new OdontologoDTO();
        dto.setNombre("Ned");
        dto.setApellido("Flanders");
        dto.setMatricula(20202);

        OdontologoDTO creado = odontologoService.guardarOdontologo(dto);
        Assertions.assertNotNull(creado.getId());
        Long id = creado.getId();

        // READ
        OdontologoDTO leido = odontologoService.buscarOdontologoPorId(id);
        Assertions.assertEquals("Ned", leido.getNombre());

        // UPDATE
        leido.setNombre("Nedward");
        OdontologoDTO actualizado = odontologoService.editarOdontologos(id, leido);
        Assertions.assertEquals("Nedward", actualizado.getNombre());

        // DELETE
        odontologoService.eliminarOdontologo(id);
        Assertions.assertThrows(NotFoundException.class, () -> {
            odontologoService.buscarOdontologoPorId(id);
        });
    }
}

