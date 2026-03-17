package com.unibite.unibit_backend.repository;

import com.unibite.unibit_backend.entity.Menu;
//import com.unibite.unibit_backend.enums.Menutype;
import com.unibite.unibit_backend.enums.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu,Long> {
    List<Menu> findByMenuType(MenuType menutype);
}
