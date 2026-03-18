package com.unibite.unibit_backend.service;

import com.unibite.unibit_backend.dto.MenuRequest;
import com.unibite.unibit_backend.entity.FoodItem;
import com.unibite.unibit_backend.entity.Menu;
import com.unibite.unibit_backend.enums.MenuType;
//import com.unibite.unibit_backend.enums.Menutype;
import com.unibite.unibit_backend.repository.FoodItemRepository;
import com.unibite.unibit_backend.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final FoodItemRepository foodItemRepository;
    public Menu assign(MenuRequest menuRequest) {

        FoodItem foodItem = foodItemRepository.findById(menuRequest.getFoodId())
                .orElseThrow(() -> new RuntimeException("Food not found"));

        return menuRepository.save(
                Menu.builder()
                        .foodItem(foodItem)
                        .menuType(menuRequest.getMenuType())
                        .available(true)
                        .build()
        );
    }

    public List<Menu> get(MenuType type){
        return menuRepository.findByMenuType(type);
    }
    public List<Menu> getAll(){
        return menuRepository.findAll();
    }

}
