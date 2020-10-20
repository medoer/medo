package medo.payment.domain;

import medo.common.mysql.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface PaymentRepository extends SuperMapper<Payment> {

    @Select("SELECT * FROM payment WHERE payment_id = #{paymentId}")
    Payment selectByPaymentId(@Param("paymentId") String paymentId);
}
