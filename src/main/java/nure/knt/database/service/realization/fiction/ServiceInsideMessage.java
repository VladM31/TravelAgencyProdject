package nure.knt.database.service.realization.fiction;

import nure.knt.database.idao.fiction.IDAOInsideMessage;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.fictions.ITermInsideMessageSetUser;
import nure.knt.database.service.implement.fiction.IServiceInsideMessage;
import nure.knt.entity.enums.Role;
import nure.knt.entity.subordinate.Message;
import nure.knt.entity.subordinate.MessageShortData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@PropertySource("classpath:property/fiction/WorkerWithInsideMessage.properties")
public class ServiceInsideMessage implements IServiceInsideMessage {

    private IDAOInsideMessage dao;

    @Autowired
    public void setDao(ApplicationContext context, @Value("${dao.inside.message.for.service}") String daoBeanName) {
        this.dao = context.getBean(daoBeanName, IDAOInsideMessage.class);
    }

    @Override
    public ITermInsideMessageSetUser term() {
        return dao.term();
    }

    @Override
    public List<MessageShortData> findMessagesShortData(ITermInformation information) {
        return dao.findMessagesShortData(information);
    }

    @Override
    public Optional<String> findDescribeByMSD(MessageShortData messageShortData) {
        return dao.findDescribeByMSD(messageShortData);
    }

    @Override
    public boolean save(Message message, long fromWhom) {
        return dao.save(message,fromWhom);
    }

    @Override
    public boolean send(Message message, long fromWhom, String[] emails) {
        return dao.send(message,fromWhom,emails);
    }

    @Override
    public boolean send(Message message, long fromWhom, Role[] roles) {
        return dao.send(message,fromWhom,roles);
    }
}
