package com.example.demo.service;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.entity.Employee;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> getAll();

    ResponseEntity<Employee> create(EmployeeDTO employeeDTO);

    ResponseEntity<Employee> update(EmployeeDTO employeeDTO);

    void delete(int id);

    void generateExcel(HttpServletResponse response) throws IOException;
}
