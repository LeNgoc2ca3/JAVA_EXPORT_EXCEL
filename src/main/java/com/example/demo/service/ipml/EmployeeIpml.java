package com.example.demo.service.ipml;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.entity.Employee;
import com.example.demo.repositories.EmployeeRepo;
import com.example.demo.service.EmployeeService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EmployeeIpml implements EmployeeService {

    @Autowired
    EmployeeRepo repo;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<EmployeeDTO> getAll() {
        List<Employee> employee = repo.findAll();
        TypeToken<List<EmployeeDTO>> typeToken = new TypeToken<>() {
        };
        List<EmployeeDTO> employeeDTO = mapper.map(employee, typeToken.getType());
        return employeeDTO;
    }

    @Override
    public ResponseEntity<Employee> create(EmployeeDTO employeeDTO) {
        Employee employee = mapper.map(employeeDTO, Employee.class);
        if (!repo.existsById(employee.getId())) {
            return ResponseEntity.ok(repo.save(employee));
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<Employee> update(EmployeeDTO employeeDTO) {
        Employee employee = mapper.map(employeeDTO, Employee.class);
        if (repo.existsById(employee.getId())) {
            return ResponseEntity.ok(repo.save(employee));
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }

    @Override
    public void generateExcel(HttpServletResponse response) throws IOException {//gửi excel dưới dạng response
        //và khách hàng sẽ đc nhận
        List<Employee> employees = repo.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Employee");
        HSSFRow row = sheet.createRow(0);//tạo dòng đầu cho tiêu đề
        row.createCell(0).setCellValue("STT");
        row.createCell(1).setCellValue("CODE");
        row.createCell(2).setCellValue("NAME");
        row.createCell(3).setCellValue("EMAIL");
        row.createCell(4).setCellValue("PHONE");

        int dataRowIndex = 1; // tạo cho dữ liệu bắt đầu từ 1
        int i = 0;
        for (Employee employee : employees){
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(i++);
            dataRow.createCell(1).setCellValue(employee.getCode());
            dataRow.createCell(2).setCellValue(employee.getName());
            dataRow.createCell(3).setCellValue(employee.getEmail());
            dataRow.createCell(4).setCellValue(employee.getPhone());
            dataRowIndex++;//tăng dòng
        }

        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);//ghi dữ liệu vào outputStream
        workbook.close();
        ops.close();

    }
}
