package nure.knt;

import nure.knt.database.idao.entity.IDAOCustomerSQL;
import nure.knt.entity.important.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDAOCustomer {

    @Autowired
    private IDAOCustomerSQL<Customer> idaoCustomerSQL;

    @Test
    public void test_whenSaveAndRetrieveEntityWhereUserIdIs_thenOK(){
        Customer genericCustomer = new Customer();
        genericCustomer.setId(100l);

        Customer foundCustomer = idaoCustomerSQL.findOneById(100l);

        assertNotNull(foundCustomer);
        assertEquals(genericCustomer.getId(), foundCustomer.getId());
    }

    @Test
    public void test_whenSaveAndRetrieveEntity_whereUsernameIs_thenOK(){
        final String username = "olivia982977527";
        Customer genericCustomer = new Customer();
        genericCustomer.setUsername(username);

        Customer foundCustomer = idaoCustomerSQL.findByUsername(username);

        assertNotNull(foundCustomer);
        assertEquals(genericCustomer.getUsername(), foundCustomer.getUsername());
    }

    @Test
    public void test_whenSaveAndRetrieveEntity_whereNumberIs_thenOK(){
        final String number = "0662417417";
        Customer genericCustomer = new Customer();
        genericCustomer.setNumber(number);

        Customer foundCustomer = idaoCustomerSQL.findByNumber(number);

        assertNotNull(foundCustomer);
        assertEquals(genericCustomer.getNumber(), foundCustomer.getNumber());
    }
}
