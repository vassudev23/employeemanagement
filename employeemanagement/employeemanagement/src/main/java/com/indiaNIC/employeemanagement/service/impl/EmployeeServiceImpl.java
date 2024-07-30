package com.indiaNIC.employeemanagement.service.impl;

import com.indiaNIC.employeemanagement.model.Employee;
import com.indiaNIC.employeemanagement.model.EmployeeAttendanceDetail;
import com.indiaNIC.employeemanagement.repository.DesignationRepository;
import com.indiaNIC.employeemanagement.repository.EmployeeAttendanceDetailRepository;
import com.indiaNIC.employeemanagement.repository.EmployeeRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private EmployeeAttendanceDetailRepository attendanceRepository;

    @Override
    public byte[] generateExcelForAllEmployees(int month) {
        int year = LocalDate.now().getYear();
        Month monthEnum = Month.of(month);
        LocalDate startDate = LocalDate.of(year, monthEnum, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeAttendanceDetail> attendanceDetails = attendanceRepository.findAll()
                .stream()
                .filter(detail -> !detail.getAttendanceDate().isBefore(ChronoLocalDate.from(startDate)) &&
                        !detail.getAttendanceDate().isAfter(ChronoLocalDate.from(endDate)))
                .toList();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Attendance");

        int rowNum = 0;
        Row headerRow = sheet.createRow(rowNum++);
        String[] headers = {"Employee ID", "Employee Code", "Employee Name", "Designation", "Date", "In Time", "Out Time", "Status"};
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        Map<Long, Employee> employeeMap = employees.stream()
                .collect(Collectors.toMap(Employee::getEmployeeId, e -> e));

        Map<Long, String> designationMap = designationRepository.findAll().stream()
                .collect(Collectors.toMap(d -> d.getDesignationId(), d -> d.getDesignationName()));

        for (EmployeeAttendanceDetail detail : attendanceDetails) {
            Employee employee = employeeMap.get(detail.getEmployeeId());
            if (employee != null) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(employee.getEmployeeId());
                row.createCell(1).setCellValue(employee.getEmployeeCode());
                row.createCell(2).setCellValue(employee.getEmployeeName());
                row.createCell(3).setCellValue(designationMap.get(employee.getDesignationId()));
                row.createCell(4).setCellValue(detail.getAttendanceDate().toString());
                row.createCell(5).setCellValue(detail.getInTime() != null ? detail.getInTime().toLocalTime().toString() : "N/A");
                row.createCell(6).setCellValue(detail.getOutTime() != null ? detail.getOutTime().toLocalTime().toString() : "N/A");
                row.createCell(7).setCellValue(detail.getAttendanceStatus());
            }
        }

        Row countRow = sheet.createRow(rowNum++);
        countRow.createCell(0).setCellValue("Total Records");
        countRow.createCell(1).setCellValue(rowNum - 1 - 1);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            workbook.write(baos);
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return baos.toByteArray();
    }
}
