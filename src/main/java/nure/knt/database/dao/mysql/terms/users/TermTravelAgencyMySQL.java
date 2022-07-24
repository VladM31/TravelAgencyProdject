package nure.knt.database.dao.mysql.terms.users;


import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.terms.HandlerTerm;
import nure.knt.database.idao.terms.fieldenum.IUserField;
import nure.knt.database.idao.terms.users.ITermTravelAgency;
import nure.knt.entity.important.TravelAgency;

import java.util.Map;

public class TermTravelAgencyMySQL extends TermUserMySQL<ITermTravelAgency> implements ITermTravelAgency {

    public TermTravelAgencyMySQL(Map<IUserField, String> orderByValueStringMap) {
        super(orderByValueStringMap);
    }

    private static final String TRAVEL_AGENCY_ID_IN = " travel_agency.id in (" + HandlerSqlDAO.REPLACE_SYMBOL + ")";
    @Override
    public ITermTravelAgency travelAgencyIdIn(Long... ids) {
        privateWhere = HandlerTerm.setFieldsIn(privateWhere,TRAVEL_AGENCY_ID_IN,privateParametersForWhere,ids);
        return this;
    }

    private static final String RETING_BETWEEN = " travel_agency.rating BETWEEN ? AND ? ";
    @Override
    public ITermTravelAgency ratingBetween(Float startRating, Float endRating) {
        privateWhere = HandlerTerm.setScript(privateWhere,RETING_BETWEEN,privateParametersForWhere,startRating,endRating);
        return this;
    }

    private static final String KVED_CONTAINING = " travel_agency.kved LIKE ? ";
    @Override
    public ITermTravelAgency KVEDContaining(String kved) {
        privateWhere = HandlerTerm.setScript(privateWhere,KVED_CONTAINING,privateParametersForWhere,HandlerSqlDAO.containingString(kved));
        return this;
    }

    private static final String EGRPOY_OR_RNEKPN_IN = " travel_agency.egrpoy_or_rnykpn in (" + HandlerSqlDAO.REPLACE_SYMBOL + ")";
    @Override
    public ITermTravelAgency EGRPOYorRNEKPNin(Long... values) {
        privateWhere = HandlerTerm.setFieldsIn(privateWhere,EGRPOY_OR_RNEKPN_IN,privateParametersForWhere,values);
        return this;
    }

    private static final String EGRPOY_IS = " travel_agency.is_egrpoy = ? ";
    @Override
    public ITermTravelAgency isEGRPOY(boolean boolEGRPOY) {
        privateWhere = HandlerTerm.setScript(privateWhere,EGRPOY_IS,privateParametersForWhere,boolEGRPOY);
        return this;
    }

    private static final String ADDRESS_CONTAINING = " travel_agency.address LIKE ? ";
    @Override
    public ITermTravelAgency addressContaining(String address) {
        privateWhere = HandlerTerm.setScript(privateWhere,ADDRESS_CONTAINING,privateParametersForWhere,HandlerSqlDAO.containingString(address));
        return this;
    }

    private static final String FULL_NAME_DIRECTOR_CONTAINING = " travel_agency.full_name_director LIKE ? ";
    @Override
    public ITermTravelAgency fullNameDirectorContaining(String fullNameDirector) {
        privateWhere = HandlerTerm.setScript(privateWhere,FULL_NAME_DIRECTOR_CONTAINING,privateParametersForWhere,HandlerSqlDAO.containingString(fullNameDirector));
        return this;
    }

    private static final String CODE_CONFIRMED_IS = " travel_agency.code_confirmed = ? ";
    @Override
    public ITermTravelAgency codeConfirmedIs(boolean codeConfirmed) {
        privateWhere = HandlerTerm.setScript(privateWhere,CODE_CONFIRMED_IS,privateParametersForWhere,codeConfirmed);
        return this;
    }

    private static final String DESCRIBE_AGENCY_CONTAINING = " travel_agency.describe_agency LIKE ? ";
    @Override
    public ITermTravelAgency describeAgencyContaining(String describeAgency) {
        privateWhere = HandlerTerm.setScript(privateWhere,DESCRIBE_AGENCY_CONTAINING,privateParametersForWhere,HandlerSqlDAO.containingString(describeAgency));
        return this;
    }

    private static final String URL_PHOTO_CONTAINING = " travel_agency.url_photo LIKE ? ";
    @Override
    public ITermTravelAgency urlPhotoContaining(String urlPhoto) {
        privateWhere = HandlerTerm.setScript(privateWhere,URL_PHOTO_CONTAINING,privateParametersForWhere,HandlerSqlDAO.containingString(urlPhoto));
        return this;
    }
}
