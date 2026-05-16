package com.foodapp.menuservice.exception;

public class MenuItemNotFoundException extends RuntimeException {

    public MenuItemNotFoundException(Long menuItemId) {
        super("Menu item not found with id " + menuItemId);
    }
}
