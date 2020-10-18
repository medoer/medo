package medo.payment.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import medo.common.core.id.IdGenerator;
import medo.payment.channel.common.ChannelId;
import medo.payment.common.domain.Money;
import medo.payment.domain.Payment;
import medo.payment.domain.PaymentRepository;
import medo.payment.domain.Terminal;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest()
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private IdGenerator idGenerator;

    @Test
    public void testCreate() {
        Payment payment = Payment.createPayment(new Terminal(),
                Money.ZERO, ChannelId.ALIPAY, idGenerator.generateId().asString());
        assertThat(paymentRepository.insert(payment)).isGreaterThan(0);
    }

    @Test
    public void testQuery() {
        Payment payment = Payment.createPayment(new Terminal(),
                Money.ZERO, ChannelId.ALIPAY, idGenerator.generateId().asString());
        assertThat(paymentRepository.insert(payment)).isGreaterThan(0);
        Payment paymentById = paymentRepository.selectById(payment.getId());
        assertThat(paymentById).isNotNull();
        assertThat(paymentById.getAmount()).isEqualTo(Money.ZERO);
        assertThat(paymentById.getBalance()).isEqualTo(Money.ZERO);
    }

    @Test
    public void testUpdate() {
        Payment payment = Payment.createPayment(new Terminal(),
                Money.ZERO, ChannelId.ALIPAY, idGenerator.generateId().asString());
        assertThat(paymentRepository.insert(payment)).isGreaterThan(0);
        Money amount = payment.getAmount();
        amount.setAmount(new BigDecimal(20));
        payment.setAmount(amount);
        assertThat(paymentRepository.updateById(payment)).isGreaterThan(0);
        Payment res = paymentRepository.selectById(payment.getId());
        assertThat(res.getAmount()).isEqualTo(amount);
    }

    @Test
    public void testDelete() {
        Payment payment = Payment.createPayment(new Terminal(),
                Money.ZERO, ChannelId.ALIPAY, idGenerator.generateId().asString());
        assertThat(paymentRepository.insert(payment)).isGreaterThan(0);
        Money amount = payment.getAmount();
        LambdaQueryWrapper<Payment> queryWrapper = new QueryWrapper<Payment>().lambda().eq(Payment::getAmount, amount);
        // 根据 json 删除查询都行不通，实际也不需要根据 value object 删除
        assertThat(paymentRepository.delete(queryWrapper)).isEqualTo(0);
    }

    @Test
    public void testSelectByPaymentId() {
        Payment payment = Payment.createPayment(new Terminal(),
                Money.ZERO, ChannelId.ALIPAY, idGenerator.generateId().asString());
        assertThat(paymentRepository.insert(payment)).isGreaterThan(0);
        Payment paymentSelect = paymentRepository.selectByPaymentId(payment.getPaymentId());
        assertThat(paymentSelect).isNotNull();
        assertThat(paymentSelect.getAmount()).isNotNull();
        assertThat(paymentSelect.getBalance()).isNotNull();
    }
}
