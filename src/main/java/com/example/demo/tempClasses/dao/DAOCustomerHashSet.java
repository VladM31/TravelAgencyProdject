package com.example.demo.tempClasses.dao;

import com.example.demo.database.idao.entity.IDAOCustomer;
import com.example.demo.entity.important.Customer;
import com.example.demo.entity.important.Role;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class DAOCustomerHashSet implements IDAOCustomer<Customer>  {

    private static Set<Customer> table = new HashSet<>();

    public DAOCustomerHashSet() {
        table.add(new Customer(1l,38066047,"laf@gmail.com","laf","laf",
                true,LocalDateTime.now(),Role.CUSTOMER,"Ukrania","Vlad/Mormul",1l,true));
        table.add(new Customer(2l,38066048,"alin@gmail.com","alin","alin",
                true,LocalDateTime.now().plusSeconds(30),Role.CUSTOMER,"Ukrania","Alina/Ficenco",2l,false));
        table.add(new Customer(3l,38066049,"tym@gmail.com","tym","tym",
                true,LocalDateTime.now().plusMinutes(1),Role.CUSTOMER,"Ukrania","Tymur/Khamzin",3l,true));
        table.add(new Customer(4l,1,"Delete","1","1",
                true,LocalDateTime.now(),Role.CUSTOMER,"Delete","Delete/Delete",4l,true));
    }

    public DAOCustomerHashSet(Set<Customer> table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "DAOSet : " + this.table.hashCode() + "\n" + super.toString();
    }

    @Override
    public List<Customer> findByMale(Boolean male) {
        return table.stream().filter( i -> i.equals(male)).collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByCustomerIdIn(Iterable<Customer> ids) {
        return  table.stream().filter( i -> {
            for (Customer id : ids) {
                if (i.getCustomerId().equals(id.getCustomerId())) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public Customer findByCustomerId(Long id) {
        for (Customer cust : table) {
            if (cust.getCustomerId().equals(id)) {
                return cust;
            }
        }
        return null;
    }

    @Override
    public List<Customer> findByFirstName(String firstName) {
        return table.stream().filter(i -> i.getFirstName().equals(firstName)).collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByLastName(String lastName) {
        return table.stream().filter(i -> i.getLastName().equals(lastName)).collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByFirstNameLike(String script) {
        return null;
    }

    @Override
    public List<Customer> findByLastNameLike(String script) {
        return null;
    }

    @Override
    public List<Customer> findByFirstNameNotLike(String script) {
        return null;
    }

    @Override
    public List<Customer> findByLastNameNotLike(String script) {
        return null;
    }

    @Override
    public List<Customer> findByFirstNameStartingWith(String starting) {
        return table.stream().filter(i -> i.getFirstName().startsWith(starting)).collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByLastNameStartingWith(String starting) {
        return table.stream().filter(i -> i.getLastName().startsWith(starting)).collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByFirstNameEndingWith(String ending) {
        return table.stream().filter(i -> i.getFirstName().endsWith(ending)).collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByLastNameEndingWith(String ending) {
        return table.stream().filter(i -> i.getLastName().endsWith(ending)).collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByFirstNameContaining(String part) {
        return table.stream().filter(i -> i.getFirstName().contains(part)).collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByLastNameContaining(String part) {
        return table.stream().filter(i -> i.getLastName().contains(part)).collect(Collectors.toList());
    }

    @Override
    public int deleteAllById(Iterable<Long> ids) {
        int toDelete = 0;
        Customer temp = null;
        for (Iterator<Customer> it = table.iterator(); it.hasNext(); ) {
            temp = it.next();
            for (long id : ids) {
                if (id == temp.getId()) {
                    ++toDelete;
                    it.remove();
                    break;
                }
            }
        }
        return toDelete;
    }

    @Override
    public int deleteAllByEntity(Iterable<Customer> entities) {
        int wasSize = this.table.size();
        for (Customer cust : entities) {
            table.remove(cust);
        }
        return wasSize - this.table.size();
    }

    @Override
    public int deleteByEntity(Customer entity) {
        return Boolean.compare(this.table.remove(entity),false);
    }

    @Override
    public int deleteById(Long id) {
        for (Iterator<Customer> it = table.iterator(); it.hasNext(); ) {
            if (it.next().getId().equals(id)) {
                it.remove();
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int deleteAll() {
        int wasSize = this.table.size();
        this.table.clear();
        return wasSize;
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(this.table);
    }

    @Override
    public List<Customer> findAllById(Iterable<Long> ids) {
        return this.table.stream().filter( i ->{
            for(long id:ids)
            {
                if(id == i.getId())
                {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public Customer findOneById(Long id) {
        for (Customer cust : table) {
            if (cust.getId().equals(id)) {
                return cust;
            }
        }
        return null;
    }

    @Override
    public boolean saveAll(Iterable<Customer> entities) {
        int wasSize = this.table.size();
        entities.forEach(table::add);
        return Integer.compare(wasSize, table.size()) != 0;
    }

    @Override
    public boolean save(Customer entity) {
        return this.table.add(entity);
    }

    @Override
    public int updateAllById(Iterable<Customer> entities) {
        return 0;
    }

    @Override
    public int updateOneById(Customer entity) {
        return 0;
    }

    @Override
    public long size() {
        return this.table.size();
    }

    @Override
    public Customer findByNumber(long number) {
        for (Customer cust : this.table) {
            if (cust.getNumber() == number) {
                return cust;
            }
        }
        return null;
    }

    @Override
    public Customer findByEmail(String email) {
        for (Customer cust : this.table) {
            if (cust.getEmail().equals(email)) {
                return cust;
            }
        }
        return null;
    }

    @Override
    public Customer findByUsername(String username) {
        for (Customer cust : this.table) {
            if (cust.getUsername().equals(username)) {
                return cust;
            }
        }
        return null;
    }

    @Override
    public List<Customer> findByPassword(String password) {
        return this.table.stream().filter(i -> i.getPassword().equals(password)).collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByDateRegistration(LocalDateTime dataRegistration) {
        return this.table
                .stream()
                .filter(i-> i.getDateRegistration().equals(dataRegistration))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByDateRegistrationAfter(LocalDateTime dataRegistration) {
        return this.table
                .stream()
                .filter(i-> i.getDateRegistration().isAfter(dataRegistration))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByDateRegistrationBefore(LocalDateTime dataRegistration) {
        return this.table
                .stream()
                .filter(i-> i.getDateRegistration().isBefore(dataRegistration))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByDateRegistrationBetween(LocalDateTime start, LocalDateTime end) {
        return this.table
                .stream()
                .filter(i-> i.getDateRegistration().isAfter(start) && i.getDateRegistration().isBefore(end))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByActive(boolean active) {
        return this.table
                .stream()
                .filter(i-> i.isActive() == active)
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByRole(Role role) {
        return this.table
                .stream()
                .filter(i-> i.getRole().equals(role))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByCountry(String country) {
        return this.table
                .stream()
                .filter(i-> i.getCountry().equals(country))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByEmailLike(String piece) {
        return null;
    }

    @Override
    public List<Customer> findByEmailContaining(String start) {
        return null;
    }


    @Override
    public List<Customer> findByUsernameOrNumberOrEmail(Customer user) {
        return table.stream().filter( i -> i.getUsername().equals(user.getUsername()) ||
                i.getPassword().equals(user.getPassword()) ||
                i.getNumber() == user.getNumber() ||
                i.getEmail().equals(user.getEmail())
        ).collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByName(String name) {
        return null;
    }

    @Override
    public List<Customer> findByNameContaining(String name) {
        return null;
    }
}
