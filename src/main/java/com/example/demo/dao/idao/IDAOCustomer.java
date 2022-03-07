package com.example.demo.dao.idao;

import com.example.demo.entity.Customer;

import java.util.List;

public interface IDAOCustomer<C extends Customer> extends IDAOUser<C>{
     public List<C> findByMale(Boolean male);
     public List<C> findByCustomerIdIn(Iterable<C> ids);
     public C findByCustomerId(Long id);

     public List<C> findByFirstName(String firstName);
     public List<C> findByLastName(String lastName);
     public List<C> findByFirstNameLike(String script);
     public List<C> findByLastNameLike(String script);
     public List<C> findByFirstNameNotLike(String script);
     public List<C> findByLastNameNotLike(String script);
     public List<C> findByFirstNameStartingWith(String starting);
     public List<C> findByLastNameStartingWith(String starting);
     public List<C> findByFirstNameEndingWith(String ending);
     public List<C> findByLastNameEndingWith(String ending);
     public List<C> findByFirstNameContaining(String part);
     public List<C> findByLastNameContaining(String part);

}
