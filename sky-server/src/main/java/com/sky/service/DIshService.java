package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;



public interface DIshService {
    /**
     * 新增菜品
     */

    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品发呢也查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);
}
