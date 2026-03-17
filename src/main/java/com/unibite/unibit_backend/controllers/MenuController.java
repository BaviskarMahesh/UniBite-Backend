package com.unibite.unibit_backend.controllers;

import com.unibite.unibit_backend.dto.MenuRequest;
import com.unibite.unibit_backend.entity.Menu;
//import com.unibite.unibit_backend.enums.Menutype;
import com.unibite.unibit_backend.enums.MenuType;
import com.unibite.unibit_backend.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService service;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Menu assign(@RequestBody MenuRequest request){
        return service.assign(request);
    }
    @GetMapping
    public List<Menu> get(@RequestParam MenuType menuType){
        return service.get(menuType);
    }
}
