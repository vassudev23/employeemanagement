package com.indiaNIC.employeemanagement.repository;

import com.indiaNIC.employeemanagement.model.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {
}
