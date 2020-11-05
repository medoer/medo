package medo.payment.domain;

import medo.common.core.id.IdGenerator;
import medo.common.spring.transactional.TransactionHelper;
import medo.payment.common.domain.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class PaymentRepositoryTest {

    @Autowired private PaymentRepository paymentRepository;

    @Autowired private IdGenerator idGenerator;

    @Autowired private TransactionHelper transactionHelper;

    @Test
    public void testCreate() {
        Payment payment =
                Payment.createPayment(
                        new Terminal(), Money.ZERO, 1L, idGenerator.generateId().asString());
        //        int insert = paymentRepository.insert(payment);
        //        Assert.assertEquals(insert, 1);
    }
}
