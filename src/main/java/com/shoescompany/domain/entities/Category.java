package com.shoescompany.domain.entities;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category extends Base{


    @Column(name = "category", length = 50, nullable = false)
    private String category;

    @Column(name = "image", length = 255)
    private String image;

    @OneToMany(targetEntity = Product.class, fetch = FetchType.LAZY, mappedBy = "category", orphanRemoval = true)
    private List<Product> products;
}
