package com.indiaNIC.employeemanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "designation_master")
public class Designation {

    @Id
    @Column(name = "designation_id")
    private Long designationId;

    @Column(name = "designation_code")
    private String designationCode;

    @Column(name = "designation_name")
    private String designationName;
}
