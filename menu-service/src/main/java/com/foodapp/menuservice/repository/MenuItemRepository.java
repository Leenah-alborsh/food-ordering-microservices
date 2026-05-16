package com.foodapp.menuservice.repository;

import com.foodapp.menuservice.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
