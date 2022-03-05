package com.example.demo.dao.idao;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface IDAOUser extends  IDAODefault<User> {

   User  findByNumber(long number);
   User findByEmail(String email);
   User findByUsername(String username);
   User findByUsernameAndPassword(String username,String password);
   List<User> findByPassword(String password);

   List<User> findByDateRegistration(LocalDateTime dataRegistration);
   List<User> findByDateRegistrationAfter(LocalDateTime dataRegistration);
   List<User> findByDateRegistrationBefore(LocalDateTime dataRegistration);
   List<User> findByDateRegistrationBetween(LocalDateTime start,LocalDateTime end);

   List<User> findByActive(boolean active);
   List<User> findByRole(Role role);
   List<User> findByCountry(String country);

   List<User> findByEmailLike(String piece);
   List<User> findByEmailStarting(String start);
   List<User> findByEmailEnding(String end);
}
