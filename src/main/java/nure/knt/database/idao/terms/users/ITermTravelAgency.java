package nure.knt.database.idao.terms.users;

import org.springframework.lang.NonNull;

public interface ITermTravelAgency extends ITermUser<ITermTravelAgency>{

    ITermTravelAgency travelAgencyIdIn(@NonNull Long ...ids);
    ITermTravelAgency ratingBetween(Float startRating,Float endRating);
    ITermTravelAgency KVEDContaining(String kved);
    ITermTravelAgency EGRPOYorRNEKPNin(Long ...values);
    ITermTravelAgency isEGRPOY(boolean boolEGRPOY);
    ITermTravelAgency addressContaining(String address);
    ITermTravelAgency fullNameDirectorContaining(String fullNameDirector);
    ITermTravelAgency codeConfirmedIs(boolean codeConfirmed);
    ITermTravelAgency describeAgencyContaining(String describeAgency);
    ITermTravelAgency urlPhotoContaining(String urlPhoto);
}
