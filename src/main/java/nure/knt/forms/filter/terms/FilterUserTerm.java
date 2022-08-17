package nure.knt.forms.filter.terms;

import nure.knt.database.idao.terms.users.ITermUser;

public class FilterUserTerm  extends  FilterUserTermCore{
    public ITermUser<? extends ITermUser> filtering(ITermUser<? extends ITermUser> term){
        super.defaultFiltering(term);
        return term;
    }
}
