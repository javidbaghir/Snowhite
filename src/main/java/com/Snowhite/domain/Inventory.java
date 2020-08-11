package com.Snowhite.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private String weightUnite;

    @Column(nullable = false)
    private double karat;

    @Column(nullable = false)
    private double cost;

    private double salePrice;

    @Column(nullable = true)
    @ManyToOne
    private Product product;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private String status = "available";
}
