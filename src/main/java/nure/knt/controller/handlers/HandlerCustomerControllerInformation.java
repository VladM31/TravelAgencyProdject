package nure.knt.controller.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HandlerCustomerControllerInformation {


    private static String profileCustomerOrderUrl;

    // -=-=-=-=-=-=-=-=-=-=-=-=-=-=- set -=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    @Autowired
    public void setUrlProfileCustomerOrder( @Value("${customer.profile.order.url}") String profileCustomerOrder){
        HandlerCustomerControllerInformation.profileCustomerOrderUrl = profileCustomerOrder;
    }


    // -=-=-=-=-=-=-=-=-=-=-=-=-=-=- get -=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    public static String getProfileCustomerOrderUrl() {
        return profileCustomerOrderUrl;
    }




}
