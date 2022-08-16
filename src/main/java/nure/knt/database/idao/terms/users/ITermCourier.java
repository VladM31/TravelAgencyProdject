package nure.knt.database.idao.terms.users;

import java.time.LocalDate;

public interface ITermCourier extends ITermUser<ITermCourier>{
    ITermCourier cityContaining(String city);
    ITermCourier addressContaining(String address);
    ITermCourier dateBirthBetween(LocalDate start, LocalDate end);
    ITermCourier doesHeWant(boolean doesHeWant);
    ITermCourier IdCourier(Long idCourier);
    ITermCourier IdCourierIn(Long ...idCouriers);
}
