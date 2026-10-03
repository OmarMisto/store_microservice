package com.example.store.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "store_sec")
    @SequenceGenerator(name = "store_seq",allocationSize = 10,sequenceName = "store_sec")
    private long storeId;
    @Column(unique = true,nullable = false)
    private String storeName;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String country;
    private String bio;
    @OneToOne(mappedBy = "store",cascade = CascadeType.ALL)
    private StoreLogo storeLogo;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false,unique = true)
    private String phoneNumber;
}
