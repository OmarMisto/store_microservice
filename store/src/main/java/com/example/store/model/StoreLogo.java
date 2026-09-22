package com.example.store.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class StoreLogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long storeLogoId;
    @Lob
    private byte[] logoBytes;
    private String contentType;
    @Column(name = "size",columnDefinition = "BIGINT CHECK(size<=10485760)")
    private long size;
    private String name;
    @OneToOne
    @JoinColumn(name = "storeId")
    private Store store;
}
