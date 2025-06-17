package com.example.javafxhomework.models;

import jakarta.persistence.*;
import lombok.*;

@Entity @NoArgsConstructor @AllArgsConstructor @Setter @Getter
public class Pastry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private long calories;
    private double price;

    public Pastry(String title, long calories, double price) {
        this.title = title;
        this.calories = calories;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Название: '%s, %dкал, Цена: %.2f", getTitle(), getCalories(), getPrice());
    }
}