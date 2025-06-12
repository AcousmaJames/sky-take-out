package com.sky.mapper;


import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    @Select("select id from setmeal_dish where dish_id = #{id}")
    List<Long> getSetmealIdsBySetmealId(Long setmealId);

    /**
     * 新增套餐中的dish
     * @param setmealDish
     */
    @Insert("insert into setmeal_dish (setmeal_id,dish_id,name,price,copies)" +
            "values (#{setmealId},#{dishId},#{name},#{price},#{copies})")
    void save(SetmealDish setmealDish);

    /**
     *  批量插入套餐菜品
     * @param setmealDishes
     */
    void saveBatch(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐id批量删除相关菜品
     * @param setmealIds
     */
    void deleteBySetmealIds(List<Long> setmealIds);

    /**
     * 根据
     * @param setmealId
     * @return
     */
    List<SetmealDish> getDishesBySetmealId(Long setmealId);
}
