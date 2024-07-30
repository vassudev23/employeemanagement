package com.indiaNIC.employeemanagement.controller;

import com.indiaNIC.employeemanagement.service.impl.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/employee")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/export-attendance")
    public ResponseEntity<byte[]> exportAttendance(@RequestParam int month) {
            byte[] excelData = employeeService.generateExcelForAllEmployees(month);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=attendance.xlsx");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
    }

}
