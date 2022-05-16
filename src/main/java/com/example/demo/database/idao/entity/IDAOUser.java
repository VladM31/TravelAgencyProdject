package com.example.demo.database.idao.entity;

import com.example.demo.entity.enums.Role;
import com.example.demo.entity.important.User;

import java.time.LocalDateTime;
import java.util.List;

public interface IDAOUser<T extends User> extends IDAOCore<T> {

   public T  findByNumber(long number);
   public T findByEmail(String email);
   public T findByUsername(String username);
   public List<T> findByPassword(String password);

   public List<T> findByDateRegistration(LocalDateTime dataRegistration);
   public List<T> findByDateRegistrationAfter(LocalDateTime dataRegistration);
   public List<T> findByDateRegistrationBefore(LocalDateTime dataRegistration);
   public List<T> findByDateRegistrationBetween(LocalDateTime start,LocalDateTime end);

   public List<T> findByActive(boolean active);
   public List<T> findByRole(Role role);
   public List<T> findByCountry(String country);

   public List<T> findByEmailLike(String piece);
   public List<T> findByEmailContaining(String start);

   public List<T> findByUsernameOrNumberOrEmail(T user);

   public List<T> findByName(String name);
   public List<T> findByNameContaining(String name);
}
