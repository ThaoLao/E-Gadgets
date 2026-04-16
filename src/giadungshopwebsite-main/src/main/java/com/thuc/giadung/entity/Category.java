package com.thuc.giadung.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category extends AbstractBase {

    @Column(name = "name")
    private String name;

    @Lob
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "category",
            cascade = {CascadeType.ALL})
    private List<Product> productList;

    // Getters and setters, validation annotations, and other fields as needed
}