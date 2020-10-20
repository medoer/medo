package medo.payment.channel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AliPayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AliPayServiceApplication.class);
    }
}
