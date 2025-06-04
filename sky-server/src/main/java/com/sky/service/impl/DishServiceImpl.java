package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDIshMapper;
import com.sky.result.PageResult;
import com.sky.service.DIshService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DIshService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDIshMapper setmealDIshMapper;

    @Override
    @Transactional //事务特性，原子性
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //向菜品表插入一条数据
        dishMapper.insert(dish);
        //在xml文件中加使用主键id，并添加返回赋值给对象id
        Long id = dish.getId();
        //向口味表中批量插入数据
        List<DishFlavor> flavors = new ArrayList<>();
        if(flavors != null && flavors.size() > 0){
            flavors.forEach(flavor -> {
                flavor.setDishId(id);
            });
            dishFlavorMapper.insertBatch(flavors);

        }


    }

    /**
     * 菜品分页查询业务层
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        long total;
        List<DishVO> records;
        try (Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO)){
            total = page.getTotal();
            records = page.getResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        PageResult pageResult = new PageResult();
        pageResult.setTotal(total);
        pageResult.setRecords(records);
        return pageResult;
    }

    /**
     * 批量删除菜品业务层，根据id删除
     * @param ids
     */
    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        //判断当前菜品是否能删除--是否状态在起售中
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if(dish.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断当前菜品是否被套餐关联
        for(Long id : ids){
            if(setmealDIshMapper.getSetmealIdsBySetmealId(id) != null && !setmealDIshMapper.getSetmealIdsBySetmealId(id).isEmpty()){
                throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            };
        }
        //删除当前菜品
        dishMapper.deleteByIds(ids);
        dishFlavorMapper.deleteByDishIds(ids);
//        for(Long id : ids){
//            dishMapper.deleteById(id);
//            //删除关联的口味表
//            dishFlavorMapper.deleteByDishId(id);
//        }
    }
}
