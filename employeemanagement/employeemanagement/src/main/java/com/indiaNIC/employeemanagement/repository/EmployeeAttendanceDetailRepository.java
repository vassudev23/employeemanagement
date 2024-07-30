package com.indiaNIC.employeemanagement.repository;

import com.indiaNIC.employeemanagement.model.EmployeeAttendanceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeAttendanceDetailRepository extends JpaRepository<EmployeeAttendanceDetail, Long> {
}