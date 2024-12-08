package com.remiges.remigesdb.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String empid;

    @Column(nullable = false)
    private String fname;

    @Column(nullable = true)
    private String fullname;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false)
    private LocalDate doj;

    @Column(nullable = false)
    private int salary;

    @ManyToOne
    @JoinColumn(name = "reportsto", referencedColumnName = "empid")
    private Employee reportsTo;

    @ManyToOne
    @JoinColumn(name = "deptid")
    private Departments department;

    @ManyToOne
    @JoinColumn(name = "rankid")
    private Rank rank;

    @Column(nullable = true)
    private LocalDateTime createdat;

    @Column(nullable = true)
    private LocalDateTime updatedat;

    public Employee(Long id, String empid, String fname, String fullname, LocalDate dob, LocalDate doj, int salary,
            Employee reportsTo, Departments department, Rank rank) {
        this.id = id;
        this.empid = empid;
        this.fname = fname;
        this.fullname = fullname;
        this.dob = dob;
        this.doj = doj;
        this.salary = salary;
        this.reportsTo = reportsTo;
        this.department = department;
        this.rank = rank;
        this.createdat = LocalDateTime.now();
        this.updatedat = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        createdat = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedat = LocalDateTime.now();
    }

    // Constructors, getters, and setters
}
