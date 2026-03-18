package com.unibite.unibit_backend.controllers;

import com.unibite.unibit_backend.dto.MenuRequest;
import com.unibite.unibit_backend.entity.Menu;
//import com.unibite.unibit_backend.enums.Menutype;
import com.unibite.unibit_backend.enums.MenuType;
import com.unibite.unibit_backend.service.MenuService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Menu assign(@RequestBody @Valid MenuRequest request){
        return service.assign(request);
    }
    @GetMapping
    public List<Menu> get(@RequestParam(required = false) MenuType menuType){

        if(menuType == null){
            return service.getAll();
        }

        return service.get(menuType);
    }
}
