package com.example.demo.mockito.concepto1;

import com.example.demo.domain.Employee;
import com.example.demo.repository.EmployeeRepositoryImpl;
import com.example.demo.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceImpl2Test {

    // dependencia @Mock
    EmployeeRepositoryImpl repositoryMock;

    // Clase bajo testing - dependiente (depende de EmployeeRepository) @InjectMocks
    EmployeeServiceImpl service; // SUT - System Under Test

    @BeforeEach
    void setUp() {
        repositoryMock = mock(EmployeeRepositoryImpl.class); // objeto ficticio creado por Mockito
        service = new EmployeeServiceImpl(repositoryMock);
    }

    @Test
    void count() {

        // configuracion escenario
        when(repositoryMock.count()).thenReturn(5);

        // ejecutar el comportamiento a testear
        Integer result = service.count();

        // Aserciones y verificaciones
        assertNotNull(result);
        assertEquals(5, result);
    }

    @Test
    void findAll() {
        // configuracion escenario mock
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(new Employee(1L, "E1", 20));
        employees.add(new Employee(2L, "E2", 20));
        employees.add(new Employee(3L, "E3", 20));

        when(repositoryMock.findAll()).thenReturn(employees);

        // ejecutar el comportamiento a testear
        List<Employee> result = service.findAll();

        // Aserciones y verificaciones
        assertNotNull(result);
        assertEquals(3L, result.size());
        verify(repositoryMock).findAll();
    }

/*

    findOneOptional CASUÍSTICAS:

    1 - Que devuelva un objeto employee correctamente
    2 - Que devuelva null
    3 - Que lance una excepción

 */
    // ESCENARIO 1
    @Test
    void findOneOptional() {

        // 1. configuracion
        Employee employee1 = new Employee(1L, "E1", 40);
        Employee employee2 = new Employee(2L, "E1", 40);

        // opcion 1: mapear los datos exactos
//        when(repositoryMock.findOne(1L)).thenReturn(employee1);
//        when(repositoryMock.findOne(2L)).thenReturn(employee2);

        // opcion 2: utilizar funciones any() para que mapee cualquier valor:
        when(repositoryMock.findOne(any())).thenReturn(employee1);

        // 2. comportamiento
        Optional<Employee> employeeOpt = service.findOneOptional(800L);

        // 3. verificaciones
        assertTrue(employeeOpt.isPresent());
    }

    // ESCENARIO 2
    @Test
    void findOneNullOptional() {

        // 1. configuracion

        // opcion 1: mapear los datos exactos
        // PONER thenReturn(null) Y NO PONER NADA ES LO MISMO PORQUE
        // SI EL MOCK NO ESTÁ CONFIGURADO ENTONCES DEVUELVE NULL
        // POR TANTO NO ES NECESARIO CONFIGURAR EL MOCK
        // PARA QUE DEVUELVA NULL PORQUE
        // YA DEVUELVE NULL SIN CONFIGURARLO
        when(repositoryMock.findOne(anyLong())).thenReturn(null);

        // 2. comportamiento
        Optional<Employee> employeeOpt = service.findOneOptional(800L);

        // 3. verificaciones
        assertTrue(employeeOpt.isEmpty()); // se comprueba que el Optional está vacío porque el mock devolvió null
        verify(repositoryMock).findOne(anyLong());
    }

    // ESCENARIO 3 - excepción
    @Test
    void findOneOptionalException() {

        // 1. configuracion
        when(
                repositoryMock.findOne(anyLong())
        ).thenThrow(new IllegalArgumentException());

//        when(
//                repositoryMock.findOne(anyLong())
//        ).thenThrow(IllegalArgumentException.class);

        // 2. comportamiento
        Optional<Employee> employeeOpt = service.findOneOptional(800L);

        // 3. verificaciones
        assertTrue(employeeOpt.isEmpty());
        verify(repositoryMock).findOne(anyLong());
    }

    /*
    1. employee null
    2. employee no null, se guarda correctamente
     */
    @Test
    void save() {

        Employee employee1 = new Employee(1L, "E1", 40);
        Employee employee2 = new Employee(1L, "E1", 40);
        when(repositoryMock.save(any())).thenReturn(employee1);

        // comportamiento
        Employee result = service.save(employee1);
        Employee result2 = service.save(employee2);

        // verificaciones
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertNotNull(result2);
        assertEquals(1L, result2.getId());

    }

    @Test
    void delete() {

        // 1
        when(repositoryMock.delete(any())).thenReturn(true);

        // 2 comportamiento
        boolean result = service.delete(1L);

        // 3
        assertTrue(result);
        verify(repositoryMock).delete(any());

    }

    @Test
    void deleteAll() {

        service.deleteAll();
        verify(repositoryMock).deleteAll();
    }
}





