package com.indiaNIC.employeemanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@Table(name = "employee_attendance_details")
public class EmployeeAttendanceDetail {
    @Id
    @Column(name = "employee_attendance_detail_id")
    private Long employeeAttendanceDetailId;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "in_time")
    private LocalDateTime inTime;

    @Column(name = "out_time")
    private LocalDateTime outTime;

    @Column(name = "attendance_date")
    private LocalDate attendanceDate;

    @Column(name = "attendance_status")
    private String attendanceStatus;

}
