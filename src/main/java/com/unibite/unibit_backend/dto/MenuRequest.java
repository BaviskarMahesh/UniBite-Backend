package com.unibite.unibit_backend.dto;

import com.unibite.unibit_backend.enums.MenuType;
//import com.unibite.unibit_backend.enums.Menutype;
import lombok.Data;

@Data
public class MenuRequest {
    private Long foodId;
    private MenuType menuType;
}
