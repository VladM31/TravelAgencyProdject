package nure.knt.database.idao.terms.fictions;

import nure.knt.database.idao.terms.fictions.ITermInsideMessage;

public interface ITermInsideMessageSetUser {
    ITermInsideMessage idUserToWhom(Long toWhom);
    ITermInsideMessage idUserFromWhom(Long fromWhom);
}
