package com.wangze.core.service;

import com.wangze.common.utils.Page;
import com.wangze.core.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    // 查询客户列表

    public Page<Customer> findCustomerList(Integer page,Integer rows,String custName,String custSource,String custIndustry,String custLevel);

    //新建客户
     int createCustomer(Customer customer);
    // 通过id查询客户
    public Customer getCustomerById(Integer id);
    // 更新客户
    public int updateCustomer(Customer customer);
    // 删除客户
    public int deleteCustomer(Integer id);



}
