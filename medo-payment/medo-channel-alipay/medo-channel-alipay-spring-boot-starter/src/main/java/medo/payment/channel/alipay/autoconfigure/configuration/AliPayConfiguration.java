// package medo.payment.channel.alipay.autoconfigure.configuration;
//
// import medo.payment.channel.alipay.AliPayChannel;
// import medo.payment.channel.alipay.AliPayFactory;
// import medo.payment.channel.alipay.AliPayProperties;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// @Configuration
// public class AliPayConfiguration {
//
//    @Bean
//    public AliPayProperties aliPayProperties() {
//        return new AliPayProperties();
//    }
//
//    @Bean
//    public AliPayFactory aliPayFactory(AliPayProperties aliPayProperties) {
//        return AliPayFactory.create(aliPayProperties);
//    }
//
//    @Bean
//    public AliPayChannel aliPayChannel() {
//        return new AliPayChannel();
//    }
// }
