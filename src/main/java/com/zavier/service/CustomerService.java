package com.zavier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zavier.dao.CustomerMapper;
import com.zavier.entity.Customer;

@Service
public class CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Transactional
    public void testTransaction() {
        Customer customer = new Customer();
        customer.setContact("qqq");
        customer.setName("zzz");
        customer.setEmail("123@123.com");
        customer.setTelephone("12345678");
        customer.setRemark("1111");
        customerMapper.insert(customer);
    }

}
