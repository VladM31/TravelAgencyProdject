package com.example.demo.dao.idao;

import com.example.demo.entity.Customer;

import java.util.List;

public interface IDAOCustomer<C extends Customer> extends IDAOUser<C>{
    List<C> findByMale(Boolean male);
    List<C> findByCustomerIdIn(Iterable<C> ids);
    C findByCustomerId(Long id);

    List<C> findByFirstName(String firstName);
    List<C> findByLastName(String lastName);
    List<C> findByFirstNameLike(String script);
    List<C> findByLastNameLike(String script);
    List<C> findByFirstNameNotLike(String script);
    List<C> findByLastNameNotLike(String script);
    List<C> findByFirstNameStartingWith(String starting);
    List<C> findByLastNameStartingWith(String starting);
    List<C> findByFirstNameEndingWith(String ending);
    List<C> findByLastNameEndingWith(String ending);
    List<C> findByFirstNameContaining(String part);
    List<C> findByLastNameContaining(String part);

}
