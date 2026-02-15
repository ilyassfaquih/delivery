package com.food.reservation.customer.controller;

import com.food.reservation.customer.domain.MenuItem;
import com.food.reservation.customer.repository.MenuRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for exposing menu endpoints. It allows clients to
 * retrieve the list of available menu items and to add new items to the
 * database. Previously this class was named OrderController, which could
 * confuse its purpose.
 */
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuRepository menuRepository;

    public MenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    /**
     * Returns the list of menu items. If a query is provided, performs a
     * case‑insensitive search on the name.
     *
     * @param q optional search term
     * @return HTTP 200 with the list of items
     */
    @GetMapping
    public ResponseEntity<List<MenuItem>> getMenu(@RequestParam(required = false) String q) {
        if (q != null && !q.isBlank()) {
            return ResponseEntity.ok(menuRepository.findByNameContainingIgnoreCase(q));
        }
        return ResponseEntity.ok(menuRepository.findAll());
    }

    /**
     * Adds a new menu item to the database.
     *
     * @param item the item to add
     * @return HTTP 200 with the saved entity
     */
    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem item) {
        return ResponseEntity.ok(menuRepository.save(item));
    }
}