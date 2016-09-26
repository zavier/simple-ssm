package com.zavier.dao;

import com.zavier.entity.Customer;

public interface CustomerMapper {
    int deleteById(Long id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectById(Long id);

    int updateByIdSelective(Customer record);

    int updateByIdWithBLOBs(Customer record);

    int updateById(Customer record);
}
