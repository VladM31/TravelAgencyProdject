package nure.knt.database.idao.entity;

import nure.knt.entity.important.Courier;


import java.time.LocalDate;

import java.util.List;

public interface IDAOCourierSQL<C extends Courier>  extends IDAOUserSQL<C>{

    public List<C> findByCityContaining(String city);

    public List<C> findByAddressContaining(String address);

    public List<C> findByDateBirthBetween(LocalDate start,LocalDate end);

    public List<C> findByDoesHeWant(boolean doesHeWant);

    public C findByIdCourier(Long idCourier);
}
