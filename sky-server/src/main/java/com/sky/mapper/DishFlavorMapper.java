package com.sky.mapper;


import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 批量插入口味表
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 删除关联口味表
     * @param dishId
     */
    void deleteByDishId(Long dishId);

    /**
     * 批量删除口味表，根据dishIds
     * @param ids
     */
    void deleteByDishIds(List<Long> ids);

    /**
     * 根据dish_id获取flavor
     * @param id
     * @return
     */
    List<DishFlavor> getByDishId(Long id);

    /**
     * 修改口味表
     * @param dishFlavors
     */
//    @AutoFill(value = OperationType.UPDATE)
    void upDate(List<DishFlavor> dishFlavors);
}
