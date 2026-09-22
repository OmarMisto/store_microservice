package com.example.store.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "product_sec")
    @SequenceGenerator(name = "product_sec",allocationSize = 20,sequenceName = "product_sec")
    private long productId;
    private String productName;
    private String productDescription;
    private float rating;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @BatchSize(size = 4)
    private ArrayList<ProductImage> productImages;
    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;
}
