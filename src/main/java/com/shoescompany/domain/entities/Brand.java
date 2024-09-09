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
@Table(name = "brands")
public class Brand extends Base{

    @Column(name = "brand", length = 50, nullable = false)
    private String brand;

    @OneToMany(targetEntity = Product.class, fetch = FetchType.LAZY, mappedBy = "brand", orphanRemoval = true)
    private List<Product> products;
}
