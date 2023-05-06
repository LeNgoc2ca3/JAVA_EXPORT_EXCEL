package com.example.demo.controller;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {

    @Autowired
    EmployeeService service;

    @GetMapping()
    public ResponseEntity<List<EmployeeDTO>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/excel")
    public void generateExcel(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");//gửi response dưới dạng stream và gửi đến client
//        đặt loại nội dung của phản hồi HTTP để chỉ ra rằng đó là luồng dữ liệu nhị phân. Điều này là do tệp Excel
//        được tạo không phải là văn bản thuần túy mà là định dạng tệp nhị phân.

        String headerKey = "Content-Disposition";//tạo khóa tiêu đề cho tiêu đề phản hồi HTTP.
        String headerValue = "attachment;filename=courses.xls";//gửi dưới dạng tệp đính kèm

        response.setHeader(headerKey,headerValue);

        service.generateExcel(response);
    }
}
