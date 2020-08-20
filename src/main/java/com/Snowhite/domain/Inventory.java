package com.Snowhite.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "{inventory.notNull}")
    @Column(nullable = false)
    private double weight;

    @NotNull(message = "{inventory.notNull}")
    @Column(nullable = false)
    private WeightUnite weightUnite;

    @NotNull(message = "{inventory.notNull}")
    @Column(nullable = false)
    private double karat;

    @Column(nullable = false)
    private int prob;

    @NotNull(message = "{inventory.notNull}")
    @Column(nullable = false)
    private double cost;

    private double salePrice;

    @ManyToOne
    private Product product;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Status status;
}
