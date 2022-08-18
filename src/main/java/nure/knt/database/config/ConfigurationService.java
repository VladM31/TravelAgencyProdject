package nure.knt.database.config;

import nure.knt.database.idao.entity.IDAOUserEdit;
import nure.knt.database.idao.entity.IDAOUserWithTerms;
import nure.knt.database.idao.terms.users.*;
import nure.knt.database.service.implement.users.IServiceUser;
import nure.knt.database.service.implement.users.IServiceUserEdit;
import nure.knt.database.service.realization.users.ServiceUser;
import nure.knt.database.service.realization.users.ServiceUserEdit;
import nure.knt.entity.important.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

@Configuration
@PropertySource("classpath:property/users/UserProperty.properties")
public class ConfigurationService {

    @Order(50)
    @Bean("User_Service")
    public IServiceUser<User, ITermUser> getUserService(ApplicationContext context, @Value("${dao.users.for.service}") String daoBeanName){
        return new ServiceUser<>(context.getBean(daoBeanName, IDAOUserWithTerms.class));
    }

    @Order(0)
    @Bean("Customer_Service")
    public IServiceUser<Customer, ITermCustomer> getCustomerService(ApplicationContext context, @Value("${dao.customer.for.service}") String daoBeanName){
        return new ServiceUser<>(context.getBean(daoBeanName, IDAOUserWithTerms.class));
    }

    @Order(30)
    @Bean("Courier_Service")
    public IServiceUser<Courier, ITermCourier> getCourierService(ApplicationContext context, @Value("${dao.courier.for.service}") String daoBeanName){
        return new ServiceUser<>(context.getBean(daoBeanName, IDAOUserWithTerms.class));
    }

    @Order(10)
    @Bean("Travel_Agency_Service")
    public IServiceUserEdit<TravelAgency, ITermTravelAgency> getTravelAgencyService(ApplicationContext context, @Value("${dao.travel.agency.for.service}") String daoBeanName){
        return new ServiceUserEdit<>(context.getBean(daoBeanName, IDAOUserEdit.class));
    }
}
