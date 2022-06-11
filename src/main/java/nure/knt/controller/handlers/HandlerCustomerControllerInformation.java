package nure.knt.controller.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HandlerCustomerControllerInformation {


    private static String profileCustomerOrderUrl;
    private static String customerEditUrl;

    // -=-=-=-=-=-=-=-=-=-=-=-=-=-=- set -=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    @Autowired
    public void setUrlProfileCustomerOrder( @Value("${customer.profile.order.url}") String profileCustomerOrder){
        HandlerCustomerControllerInformation.profileCustomerOrderUrl = profileCustomerOrder;
    }
    @Autowired
    public void setCustomerEditUrl(@Value("${customer.edit}")String customerEditUrl) {
        HandlerCustomerControllerInformation.customerEditUrl = customerEditUrl;
    }

    // -=-=-=-=-=-=-=-=-=-=-=-=-=-=- get -=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    public static String getProfileCustomerOrderUrl() {
        return profileCustomerOrderUrl;
    }

    public static String getCustomerEditUrl() {
        return customerEditUrl;
    }


}
