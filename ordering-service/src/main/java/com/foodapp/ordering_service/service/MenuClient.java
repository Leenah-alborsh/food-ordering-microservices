package com.foodapp.ordering_service.service;

import com.foodapp.ordering_service.dto.MenuItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MenuClient {

    private final RestTemplate restTemplate;

    @Value("${menu-service.base-url}")
    private String menuServiceBaseUrl;

    public MenuItemResponse getMenuItemById(Long menuItemId) {
        return restTemplate.getForObject(
                menuServiceBaseUrl + "/api/menu/items/" + menuItemId,
                MenuItemResponse.class
        );
    }
}
