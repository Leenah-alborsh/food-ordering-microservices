package com.foodapp.menuservice.service;

import com.foodapp.menuservice.exception.MenuItemNotFoundException;
import com.foodapp.menuservice.model.MenuItem;
import com.foodapp.menuservice.repository.MenuItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuItemRepository menuItemRepository;

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException(id));
    }

    public MenuItem createMenuItem(MenuItem menuItem) {
        menuItem.setId(null);
        return menuItemRepository.save(menuItem);
    }
}
