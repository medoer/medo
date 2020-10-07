package medo.demo;

import medo.common.spring.transactional.TransactionHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionalHelperTest {

    @Autowired
    private TransactionHelper transactionHelper;

    @Test
    public void testTransactionHelper() {
        transactionHelper.required((x) -> {
           return x;
        }, 1);
    }
}
