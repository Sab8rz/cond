package com.example.javafxhomework.models;

import com.example.javafxhomework.DAO.PastryDAO;
import com.example.javafxhomework.DAO.SellerDAO;
import jakarta.persistence.*;
import lombok.*;

@Entity @NoArgsConstructor @AllArgsConstructor @Setter @Getter
public class Deal {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "seller_id")
    private int sellerId;
    @Column(name = "pastry_id")
    private int pastryId;

    public Deal(int sellerId, int pastryId) {
        this.sellerId = sellerId;
        this.pastryId = pastryId;
    }

    public Seller getSeller() {
        return SellerDAO.findById(sellerId);
    }

    public Pastry getPastry() {
        return PastryDAO.findById(pastryId);
    }
}