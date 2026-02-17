package com.food.reservation.customer.controller;

import com.food.reservation.customer.domain.MenuItem;
import com.food.reservation.customer.dto.MenuItemDTO;
import com.food.reservation.customer.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for menu item management.
 * Supports listing, searching, and adding menu items.
 */
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    /**
     * Returns menu items. If a search query is provided, filters by name.
     *
     * @param q optional search term (case-insensitive)
     * @return HTTP 200 with the list of items
     */
    @GetMapping
    public ResponseEntity<List<MenuItem>> getMenu(@RequestParam(required = false) String q) {
        return ResponseEntity.ok(menuService.getMenuItems(q));
    }

    /**
     * Adds a new menu item.
     *
     * @param dto the menu item payload (validated)
     * @return HTTP 201 with the created item
     */
    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@Valid @RequestBody MenuItemDTO dto) {
        MenuItem saved = menuService.addMenuItem(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}