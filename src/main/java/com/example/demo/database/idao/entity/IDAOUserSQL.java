package com.example.demo.database.idao.entity;

import com.example.demo.database.idao.core.IDAOCore;
import com.example.demo.entity.enums.ConditionCommodity;
import com.example.demo.entity.enums.Role;
import com.example.demo.entity.important.User;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public interface IDAOUserSQL<T extends User> extends IDAOCore<T> {

   public T findByNumber(String number);
   @Nullable
   public T findByEmail(String email);
   @Nullable
   public T findByUsername(String username);

   public List<T> findByNumberContaining(String number);
   public List<T> findByUsernameContaining(String username);
   public List<T> findByEmailContaining(String start);

   public List<T> findByPassword(String password);
   public List<T> findByDateRegistrationBetween(LocalDateTime start,LocalDateTime end);

   public List<T> findByActive(boolean active);
   public List<T> findByRole(Role role);
   public List<T> findByConditionCommodity(ConditionCommodity conditionCommodity);

   public List<T> findByCountry(String country);

   public List<T> findByNameContaining(String name);


}
