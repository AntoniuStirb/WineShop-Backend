package com.example.wineshop.wineshopbackend.repository;

import com.example.wineshop.wineshopbackend.model.Wine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WineRepository extends JpaRepository<Wine, Long> {
    List<Wine> findByNameContainingIgnoreCase(String keyword);

    List<Wine> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Wine> findByPriceGreaterThanEqual(Double minPrice);

    List<Wine> findByPriceLessThanEqual(Double maxPrice);
}