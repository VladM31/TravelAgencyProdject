package nure.knt.database.idao.terms.users;

import nure.knt.database.idao.terms.core.ITermEnd;
import nure.knt.database.idao.terms.core.ITermLimit;
import nure.knt.database.idao.terms.core.ITermOrderBy;
import nure.knt.database.idao.terms.fieldenum.IUserField;
import nure.knt.entity.enums.HowSortSQL;
import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public interface ITermUser<IT extends ITermUser> extends ITermEnd, ITermLimit<IT>, ITermOrderBy<IUserField,IT> {

    public IT idUserIn(Long ...ids);

    public IT numberContaining(String number);
    public IT emailContaining(String email);
    public IT usernameContaining(String username);
    public IT countryContaining(String country);
    public IT nameContaining(String name);

    public IT numberIs(String number);
    public IT emailIs(String email);
    public IT usernameIs(String username);
    public IT confirmationСodeIs(String code);

    public IT activeIs(boolean active);

    public IT roleIn(Role ...roles);
    public IT typeStateIn(TypeState ...typeStates);

    public IT dateRegistrationBetween(LocalDateTime startDateRegistration,LocalDateTime endDateRegistration);
    public IT dateRegistrationBefore(LocalDateTime dateRegistration);
    public IT dateRegistrationAfter(LocalDateTime dateRegistration);

}
