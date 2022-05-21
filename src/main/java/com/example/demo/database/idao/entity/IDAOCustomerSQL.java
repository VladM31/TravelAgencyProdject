package com.example.demo.database.idao.entity;

import com.example.demo.entity.important.Customer;
import org.springframework.lang.Nullable;

import java.util.List;

public interface IDAOCustomerSQL<C extends Customer> extends IDAOUserSQL<C> {
     public List<C> findByMale(Boolean male);
     public List<C> findByCustomerIdIn(Iterable<C> ids);
     @Nullable
     public C findByCustomerId(Long id);

     public List<C> findByFirstNameContaining(String part);
     public List<C> findByLastNameContaining(String part);

}
