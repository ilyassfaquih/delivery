package com.food.reservation.customer.service;

import com.food.reservation.customer.domain.MenuItem;
import com.food.reservation.customer.dto.MenuItemDTO;
import com.food.reservation.customer.mapper.MenuItemMapper;
import com.food.reservation.customer.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service responsible for menu item management.
 */
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuItemMapper menuItemMapper;

    /**
     * Returns all menu items, optionally filtered by a search query.
     *
     * @param query optional search term (case-insensitive name match)
     * @return list of matching menu items
     */
    @Transactional(readOnly = true)
    public List<MenuItem> getMenuItems(String query) {
        if (query != null && !query.isBlank()) {
            return menuRepository.findByNameContainingIgnoreCase(query);
        }
        return menuRepository.findAll();
    }

    /**
     * Creates a new menu item from the given DTO.
     *
     * @param dto the menu item creation payload
     * @return the persisted menu item
     */
    @Transactional
    public MenuItem addMenuItem(MenuItemDTO dto) {
        MenuItem item = menuItemMapper.toEntity(dto);
        return menuRepository.save(item);
    }
}
