package com.sky.service;

import com.sky.dto.DishDTO;
import org.springframework.stereotype.Service;



public interface DIshService {
    /**
     * 新增菜品
     */

    public void saveWithFlavor(DishDTO dishDTO);
}
