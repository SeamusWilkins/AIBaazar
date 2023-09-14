package com.seamus.aibazaar.entity;

import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double trainedPrice;
    private Double untrainedPrice;

    public Product() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTrainedPrice() {
        return trainedPrice;
    }

    public void setTrainedPrice(Double trainedPrice) {
        this.trainedPrice = trainedPrice;
    }

    public Double getUntrainedPrice() {
        return untrainedPrice;
    }

    public void setUntrainedPrice(Double untrainedPrice) {
        this.untrainedPrice = untrainedPrice;
    }
}
