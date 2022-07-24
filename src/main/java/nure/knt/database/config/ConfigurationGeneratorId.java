package nure.knt.database.config;

import nure.knt.database.dao.mysql.entity.HandlerUser;
import nure.knt.database.idao.tools.IConnectorGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.atomic.AtomicLong;

@Configuration
@PropertySource("classpath:property/users/UserProperty.properties")
public class ConfigurationGeneratorId {

    @Autowired
    @Bean("Getter_User_Id")
    public AtomicLong getAtomicUserId(IConnectorGetter connector,
                                         @Value("${dao.for.users.id.script}") String script,
                                         @Value("${dao.for.users.id.script.name.id}") String nameId){
        AtomicLong atomicLong = new AtomicLong();
        atomicLong.set(HandlerUser.getLongByScriptAndParametersName(connector,script,nameId));
        return atomicLong;
    }

    @Autowired
    @Bean("Getter_Customer_Id")
    public AtomicLong getAtomicCustomerId(IConnectorGetter connector,
                                          @Value("${dao.for.customer.id.script}") String script,
                                          @Value("${dao.for.customer.id.script.name.id}") String nameId){
        AtomicLong atomicLong = new AtomicLong();
        atomicLong.set(HandlerUser.getLongByScriptAndParametersName(connector,script,nameId));
        return atomicLong;
    }
}
