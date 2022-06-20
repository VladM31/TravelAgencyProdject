package nure.knt.database.idao.entity;

import nure.knt.database.idao.core.IDAOCore;
import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.User;
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
   public List<T> findByTypeState(TypeState conditionCommodity);

   public List<T> findByCountry(String country);
   public List<T> findByCountryNameContaining(String country);
   public List<T> findByNameContaining(String name);

   default public boolean canUpdate(T origin,T update){
      return false;
   }
}
