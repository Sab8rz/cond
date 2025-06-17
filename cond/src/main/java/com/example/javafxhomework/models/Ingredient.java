package com.example.javafxhomework.models;

import com.example.javafxhomework.DAO.PastryDAO;
import jakarta.persistence.*;
import lombok.*;

@Entity @NoArgsConstructor @AllArgsConstructor @Setter @Getter
public class Ingredient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "pastry_idi")
    private int pastryId;
    private long quantity;

    public Ingredient(int pastryId, long quantity) {
        this.pastryId = pastryId;
        this.quantity = quantity;
    }

    public Pastry getPastry() {
        return PastryDAO.findById(pastryId);
    }
}
