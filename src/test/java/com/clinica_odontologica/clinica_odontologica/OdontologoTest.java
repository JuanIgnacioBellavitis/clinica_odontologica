package com.clinica_odontologica.clinica_odontologica;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.clinica_odontologica.clinica_odontologica.dto.OdontologoDTO;
import com.clinica_odontologica.clinica_odontologica.entity.Odontologo;
import com.clinica_odontologica.clinica_odontologica.exceptions.NotFoundException;
import com.clinica_odontologica.clinica_odontologica.repository.OdontologoRepository;
import com.clinica_odontologica.clinica_odontologica.service.OdontologoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class OdontologoTest {

	@Mock
	private OdontologoRepository odontologoRepository;

	@Mock
	private ObjectMapper objectMapper;

	@InjectMocks
	private OdontologoService odontologoService;

	@Test
	@DisplayName("Guardar odontólogo correctamente")
	public void GuardarOdontologo() {
		// Arrange
		OdontologoDTO dto = new OdontologoDTO();
		dto.setNombre("Lisa");
		dto.setApellido("Simpson");
		dto.setMatricula(12345);

		Odontologo entity = new Odontologo();
		entity.setNombre("Lisa");
		entity.setApellido("Simpson");
		entity.setMatricula(12345);

		Odontologo entityGuardado = new Odontologo();
		entityGuardado.setId(1L);
		entityGuardado.setNombre("Lisa");
		entityGuardado.setApellido("Simpson");
		entityGuardado.setMatricula(12345);

		OdontologoDTO dtoGuardado = new OdontologoDTO();
		dtoGuardado.setId(1L);
		dtoGuardado.setNombre("Lisa");
		dtoGuardado.setApellido("Simpson");
		dtoGuardado.setMatricula(12345);

		when(objectMapper.convertValue(dto, Odontologo.class)).thenReturn(entity);
		when(odontologoRepository.save(any(Odontologo.class))).thenReturn(entityGuardado);
		when(objectMapper.convertValue(entityGuardado, OdontologoDTO.class)).thenReturn(dtoGuardado);

		// Act
		OdontologoDTO guardado = odontologoService.guardarOdontologo(dto);

		// Assert
		Assertions.assertNotNull(guardado.getId());
		Assertions.assertEquals("Lisa", guardado.getNombre());
		Assertions.assertEquals("Simpson", guardado.getApellido());
		Assertions.assertEquals(12345, guardado.getMatricula());

		// Verify
		verify(objectMapper, times(1)).convertValue(dto, Odontologo.class);
		verify(odontologoRepository, times(1)).save(any(Odontologo.class));
		verify(objectMapper, times(1)).convertValue(entityGuardado, OdontologoDTO.class);
	}

	@Test
	@DisplayName("Listar odontólogos existentes")
	public void ListarOdontologos() {
		// Arrange
		Odontologo odontologo1 = new Odontologo();
		odontologo1.setId(1L);
		odontologo1.setNombre("Lisa");
		odontologo1.setApellido("Simpson");
		odontologo1.setMatricula(12345);

		Odontologo odontologo2 = new Odontologo();
		odontologo2.setId(2L);
		odontologo2.setNombre("Homero");
		odontologo2.setApellido("Simpson");
		odontologo2.setMatricula(77777);

		List<Odontologo> odontologos = Arrays.asList(odontologo1, odontologo2);

		OdontologoDTO dto1 = new OdontologoDTO();
		dto1.setId(1L);
		dto1.setNombre("Lisa");
		dto1.setApellido("Simpson");
		dto1.setMatricula(12345);

		OdontologoDTO dto2 = new OdontologoDTO();
		dto2.setId(2L);
		dto2.setNombre("Homero");
		dto2.setApellido("Simpson");
		dto2.setMatricula(77777);

		when(odontologoRepository.findAll()).thenReturn(odontologos);
		when(objectMapper.convertValue(odontologo1, OdontologoDTO.class)).thenReturn(dto1);
		when(objectMapper.convertValue(odontologo2, OdontologoDTO.class)).thenReturn(dto2);

		// Act
		List<OdontologoDTO> lista = odontologoService.listarOdontologos();

		// Assert
		Assertions.assertNotNull(lista);
		Assertions.assertEquals(2, lista.size());
		Assertions.assertEquals("Lisa", lista.get(0).getNombre());
		Assertions.assertEquals("Homero", lista.get(1).getNombre());

		// Verify
		verify(odontologoRepository, times(1)).findAll();
		verify(objectMapper, times(2)).convertValue(any(Odontologo.class), eq(OdontologoDTO.class));
	}

	@Test
	@DisplayName("Buscar odontólogo por ID")
	public void BuscarPorId() {
		// Arrange
		Long id = 1L;
		Odontologo odontologo = new Odontologo();
		odontologo.setId(id);
		odontologo.setNombre("Homero");
		odontologo.setApellido("Simpson");
		odontologo.setMatricula(77777);

		OdontologoDTO dto = new OdontologoDTO();
		dto.setId(id);
		dto.setNombre("Homero");
		dto.setApellido("Simpson");
		dto.setMatricula(77777);

		when(odontologoRepository.findById(id)).thenReturn(Optional.of(odontologo));
		when(objectMapper.convertValue(odontologo, OdontologoDTO.class)).thenReturn(dto);

		// Act
		OdontologoDTO encontrado = odontologoService.buscarOdontologoPorId(id);

		// Assert
		Assertions.assertNotNull(encontrado);
		Assertions.assertEquals("Homero", encontrado.getNombre());
		Assertions.assertEquals("Simpson", encontrado.getApellido());
		Assertions.assertEquals(77777, encontrado.getMatricula());

		// Verify
		verify(odontologoRepository, times(1)).findById(id);
		verify(objectMapper, times(1)).convertValue(odontologo, OdontologoDTO.class);
	}

	@Test
	@DisplayName("Buscar odontólogo por ID - No encontrado")
	public void BuscarPorId_NoEncontrado() {
		// Arrange
		Long id = 999L;
		when(odontologoRepository.findById(id)).thenReturn(Optional.empty());

		// Act & Assert
		NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
				() -> odontologoService.buscarOdontologoPorId(id));

		Assertions.assertTrue(exception.getMessage().contains("No se encontró el odontologo con el ID " + id));

		// Verify
		verify(odontologoRepository, times(1)).findById(id);
		verify(objectMapper, never()).convertValue(any(), eq(OdontologoDTO.class));
	}

	@Test
	@DisplayName("Modificar odontólogo existente")
	public void ModificarOdontologo() {
		// Arrange
		Long id = 1L;
		OdontologoDTO dtoActualizado = new OdontologoDTO();
		dtoActualizado.setId(id);
		dtoActualizado.setNombre("Bartolomeo");
		dtoActualizado.setApellido("Simpson");
		dtoActualizado.setMatricula(88888);

		Odontologo odontologoExistente = new Odontologo();
		odontologoExistente.setId(id);
		odontologoExistente.setNombre("Bart");
		odontologoExistente.setApellido("Simpson");
		odontologoExistente.setMatricula(88888);

		Odontologo odontologoModificado = new Odontologo();
		odontologoModificado.setId(id);
		odontologoModificado.setNombre("Bartolomeo");
		odontologoModificado.setApellido("Simpson");
		odontologoModificado.setMatricula(88888);

		OdontologoDTO dtoResultado = new OdontologoDTO();
		dtoResultado.setId(id);
		dtoResultado.setNombre("Bartolomeo");
		dtoResultado.setApellido("Simpson");
		dtoResultado.setMatricula(88888);

		when(odontologoRepository.findById(id)).thenReturn(Optional.of(odontologoExistente));
		when(odontologoRepository.save(any(Odontologo.class))).thenReturn(odontologoModificado);
		when(objectMapper.convertValue(odontologoModificado, OdontologoDTO.class)).thenReturn(dtoResultado);

		// Act
		OdontologoDTO actualizado = odontologoService.editarOdontologos(id, dtoActualizado);

		// Assert
		Assertions.assertNotNull(actualizado);
		Assertions.assertEquals("Bartolomeo", actualizado.getNombre());
		Assertions.assertEquals("Simpson", actualizado.getApellido());
		Assertions.assertEquals(88888, actualizado.getMatricula());

		// Verify
		verify(odontologoRepository, times(1)).findById(id);
		verify(odontologoRepository, times(1)).save(any(Odontologo.class));
		verify(objectMapper, times(1)).convertValue(odontologoModificado, OdontologoDTO.class);
	}

	@Test
	@DisplayName("Eliminar odontólogo correctamente")
	public void EliminarOdontologo() {
		// Arrange
		Long id = 1L;
		Odontologo odontologo = new Odontologo();
		odontologo.setId(id);
		odontologo.setNombre("Marge");
		odontologo.setApellido("Bouvier");
		odontologo.setMatricula(99999);

		when(odontologoRepository.findById(id)).thenReturn(Optional.of(odontologo));
		doNothing().when(odontologoRepository).delete(odontologo);

		// Act
		String msj = odontologoService.eliminarOdontologo(id);

		// Assert
		Assertions.assertNotNull(msj);
		Assertions.assertTrue(msj.contains("eliminado") || msj.contains("Eliminado"));
		Assertions.assertTrue(msj.contains("Marge"));

		// Verify
		verify(odontologoRepository, times(1)).findById(id);
		verify(odontologoRepository, times(1)).delete(odontologo);
	}

	@Test
	@DisplayName("Eliminar odontólogo - No encontrado")
	public void EliminarOdontologo_NoEncontrado() {
		// Arrange
		Long id = 999L;
		when(odontologoRepository.findById(id)).thenReturn(Optional.empty());

		// Act & Assert
		NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
				() -> odontologoService.eliminarOdontologo(id));

		Assertions.assertTrue(exception.getMessage().contains("No se encontró el odontologo con ID " + id));

		// Verify
		verify(odontologoRepository, times(1)).findById(id);
		verify(odontologoRepository, never()).delete(any(Odontologo.class));
	}

	@Test
	@DisplayName("Buscar odontólogo por nombre")
	public void BuscarOdontologoPorNombre() {
		// Arrange
		String nombre = "Homero";
		Odontologo odontologo = new Odontologo();
		odontologo.setId(1L);
		odontologo.setNombre(nombre);
		odontologo.setApellido("Simpson");
		odontologo.setMatricula(77777);

		OdontologoDTO dto = new OdontologoDTO();
		dto.setId(1L);
		dto.setNombre(nombre);
		dto.setApellido("Simpson");
		dto.setMatricula(77777);

		when(odontologoRepository.findByNombre(nombre)).thenReturn(Optional.of(odontologo));
		when(objectMapper.convertValue(odontologo, OdontologoDTO.class)).thenReturn(dto);

		// Act
		OdontologoDTO encontrado = odontologoService.buscarOdontologoPorNombre(nombre);

		// Assert
		Assertions.assertNotNull(encontrado);
		Assertions.assertEquals(nombre, encontrado.getNombre());
		Assertions.assertEquals("Simpson", encontrado.getApellido());

		// Verify
		verify(odontologoRepository, times(1)).findByNombre(nombre);
		verify(objectMapper, times(1)).convertValue(odontologo, OdontologoDTO.class);
	}

	@Test
	@DisplayName("Buscar odontólogo por nombre - No encontrado")
	public void BuscarOdontologoPorNombre_NoEncontrado() {
		// Arrange
		String nombre = "NoExiste";
		when(odontologoRepository.findByNombre(nombre)).thenReturn(Optional.empty());

		// Act & Assert
		NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
				() -> odontologoService.buscarOdontologoPorNombre(nombre));

		Assertions.assertTrue(exception.getMessage().contains("No se encontró odontologo con el nombre " + nombre));

		// Verify
		verify(odontologoRepository, times(1)).findByNombre(nombre);
		verify(objectMapper, never()).convertValue(any(), eq(OdontologoDTO.class));
	}
}
