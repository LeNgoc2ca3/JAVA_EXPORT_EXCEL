package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDTO {

    private int id;

    private String code;

    private String name;

    private String email;

    private Integer phone;
}
