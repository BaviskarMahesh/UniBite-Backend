package com.unibite.unibit_backend.dto;

import com.unibite.unibit_backend.enums.MenuType;
//import com.unibite.unibit_backend.enums.Menutype;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MenuRequest {
    @NotNull(message = "Food ID is required")
    private Long foodId;
    @NotNull(message = "Menu type is required")
    private MenuType menuType;
}
