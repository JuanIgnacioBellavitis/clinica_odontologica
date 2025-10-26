package com.clinica_odontologica.clinica_odontologica;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.clinica_odontologica.clinica_odontologica.entity.Odontologo;
import com.clinica_odontologica.clinica_odontologica.service.OdontologoService;

public class OdontologoTest {

    /*
	@Test
	@DisplayName("Buscar Odontologo con ID")
	public void buscarOdontologo() {
		System.out.println("************ BUSCAR ODONTOLOGO ************");
		// Arrange
		BD.crearTablas();
		OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());

		// Act
		Odontologo odontologo = odontologoService.buscar(1);
		System.out.println("Datos encontrados: " + odontologo);

		// Assert
		System.out.println("Paciente encontrado: " + odontologo);
		Assertions.assertTrue(odontologo != null);
		Assertions.assertEquals("Sicolo", odontologo.getApellido());
		System.out.println("*****************************************");
	}
	
	
	@Test
	@DisplayName("Buscar Odontologo por NOMBRE")
	public void buscarOdontologoPorNombre() {
		System.out.println("************ BUSCAR ODONTOLOGO POR NOMBRE ************");
		// Arrange
		BD.crearTablas();
		OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());

		// Act
		Odontologo odontologo = odontologoService.buscarPorNombre("Juan");
		System.out.println("Datos encontrados: " + odontologo);

		// Assert
		System.out.println("Paciente encontrado: " + odontologo);
		Assertions.assertTrue(odontologo != null);
//		Assertions.assertEquals("Sicolo", odontologo.getApellido());
		System.out.println("*****************************************");
	}

	@Test
	@DisplayName("Obtener listado de odontologos")
	public void obtenerTodosLosOdontologos() {
		System.out.println("************ LISTAR TODOS ODONTOLOGOS ************");
		BD.crearTablas();
		OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());

		List<Odontologo> odontologoIniciales = odontologoService.buscarTodos();

		System.out.println("Pacientes encontrados: " + odontologoIniciales);
		Assertions.assertEquals(2, odontologoIniciales.size());
		System.out.println("*************************************************");
	}

	@Test
	@DisplayName("Guardado existoso de odontologo")
	public void guardarOdontologo() {
		System.out.println("************ GUARDAR ODONTOLOGO ************");
		// Arrange
		BD.crearTablas();
		OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());

		Odontologo nuevo = new Odontologo("Bobina", "Ruxon", 11223344);

		// Act
		Odontologo guardado = odontologoService.guardar(nuevo);
		System.out.println("Odontologo guardado: " + guardado);

		// Assert
		Assertions.assertNotNull(guardado);
		Assertions.assertNotNull(guardado.getId());
		Assertions.assertEquals("Bobina", guardado.getNombre());
		Assertions.assertEquals("Ruxon", guardado.getApellido());
		System.out.println("********************************************");
	}
	
    @Test
    @DisplayName("Eliminar odontologo existente")
    public void eliminarOdontologo() {
        System.out.println("************ ELIMINAR ODONTOLOGO ************");
        // Arrange
        BD.crearTablas();
        OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());

        // Act
        // Comprobamos que inicialmente hay 2 odontologos
        List<Odontologo> antes = odontologoService.buscarTodos();
        Assertions.assertEquals(2, antes.size());

        // Eliminamos al odontologo con ID 1
        List<Odontologo> despues = odontologoService.eliminar(1);

        // Validamos que la lista ahora tiene 1 menos
        Assertions.assertEquals(1, despues.size());

        // Assert
        // Verificamos que no exista el ID 1
        Odontologo eliminado = despues.stream()
                .filter(p -> p.getId() == 1)
                .findFirst()
                .orElse(null);


        Assertions.assertNull(eliminado);
        System.out.println("********************************************");
    }

    @Test
    @DisplayName("Modificar odontologo existente")
    public void modificarOdontologo() {
        System.out.println("************ MODIFICAR ODONTOLOGO ************");
        // Arrange
        BD.crearTablas();
        OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());

        // Act
        Odontologo odontologo = odontologoService.buscar(1);
        Assertions.assertNotNull(odontologo);

        odontologo.setNombre("Carlos");
        odontologo.setApellido("Leyes");
       

        Odontologo modificado = odontologoService.modificar(odontologo);

        Odontologo verificado = odontologoService.buscar(modificado.getId());

        // Assert
        System.out.println("Odontologo modificado: " + verificado);
        Assertions.assertNotNull(verificado);
        Assertions.assertEquals("Carlos", verificado.getNombre());
        Assertions.assertEquals("Leyes", verificado.getApellido());
        System.out.println("********************************************");
    }*/

}
