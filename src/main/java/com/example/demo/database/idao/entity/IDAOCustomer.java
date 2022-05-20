package com.example.demo.database.idao.entity;

import com.example.demo.entity.important.Customer;

import java.util.List;

public interface IDAOCustomer<C extends Customer> extends IDAOUser<C>{
     public List<C> findByMale(Boolean male);
     public List<C> findByCustomerIdIn(Iterable<C> ids);
     public C findByCustomerId(Long id);

     public List<C> findByFirstNameContaining(String part);
     public List<C> findByLastNameContaining(String part);

}
