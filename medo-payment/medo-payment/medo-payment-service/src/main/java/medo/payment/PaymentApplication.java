package medo.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PaymentApplication {
    public static void main(String[] args) {
        log.info("Payment Service Started");
        SpringApplication.run(PaymentApplication.class, args);
    }
}
