package com.example.demo.mockito.concepto3;

import com.example.demo.domain.Employee;
import com.example.demo.repository.EmployeeRepositoryImpl;
import com.example.demo.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ExceptionMockTest {
    // dependencia @Mock
    EmployeeRepositoryImpl repositoryMock;

    // Clase bajo testing - dependiente (depende de EmployeeRepository) @InjectMocks
    EmployeeServiceImpl service; // SUT - System Under Test

    @BeforeEach
    void setUp() {
        repositoryMock = mock(EmployeeRepositoryImpl.class); // objeto ficticio creado por Mockito
        service = new EmployeeServiceImpl(repositoryMock);
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
}
