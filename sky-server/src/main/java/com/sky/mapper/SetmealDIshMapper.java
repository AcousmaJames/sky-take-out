package com.sky.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDIshMapper {
    @Select("select id from setmeal_dish where dish_id = #{id}")
    List<Long> getSetmealIdsBySetmealId(Long setmealId);
}
