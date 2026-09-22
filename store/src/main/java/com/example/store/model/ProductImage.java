package com.example.store.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "product_image_sec")
    @SequenceGenerator(name = "product_image_sec",allocationSize = 10,sequenceName = "product_image_sec")
    private long productImageId;
    @Lob
    private byte[] image;
    private String contentType;
    @Column(name = "size",columnDefinition = "BIGINT CHECK(size<=104857600)")
    private long size;
    private String name;
    @ManyToOne()
    @JoinColumn(name = "productId")
    private Product product;
}
