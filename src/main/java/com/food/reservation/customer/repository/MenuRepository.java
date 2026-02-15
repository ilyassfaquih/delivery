package com.food.reservation.customer.repository;

import com.food.reservation.customer.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for {@link MenuItem} entities. Extends {@link JpaRepository} to
 * provide standard CRUD operations and a custom search method.
 */
@Repository
public interface MenuRepository extends JpaRepository<MenuItem, Long> {

    /**
     * Finds menu items whose names contain the provided query (case insensitive).
     *
     * @param query the search term
     * @return a list of matching {@code MenuItem} records
     */
    List<MenuItem> findByNameContainingIgnoreCase(String query);
}