package com.example.javafxhomework.models;

import jakarta.persistence.*;
import lombok.*;

@Entity @NoArgsConstructor @AllArgsConstructor @Setter @Getter
public class Seller {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    private String fullName;

    private Long salary;

    public Seller(String fullName, Long salary) {
        this.fullName = fullName;
        this.salary = salary;
    }

    @Override public String toString() {
        return String.format("%s, з.п.: %d", getFullName(), getSalary());
    }
}
