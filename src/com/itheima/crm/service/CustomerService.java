package com.itheima.crm.service;

import java.util.List;

import com.itheima.crm.pojo.Customer;

import com.itheima.crm.utils.Page;

/**
 * 客户信息业务逻辑接口
 * 
 * @author Steven
 *
 */
public interface CustomerService {

	/**
	 * 查询查询条件，分页查询用户列表
	 * 
	 * @param vo
	 * @return
	 */
	List<Customer> getCustomer();

	/**
	 * 跟据id查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	Customer getCustomerById(Integer id);

	/**
	 * 更新用户信息
	 * 
	 * @param customer
	 */
	void updateCustomer(Customer customer);

	/**
	 * 删除用户信息
	 * 
	 * @param id
	 */
	void deleteCustomer(Integer id);

	void add(Customer customer);
}
