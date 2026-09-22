package com.example.store.repository;

import com.example.store.model.Store;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepo extends JpaRepository<Store,Long> {
    boolean existsByStoreName(String storeName);

    List<Store> findAllByCity(String city, Pageable pageable);
}
