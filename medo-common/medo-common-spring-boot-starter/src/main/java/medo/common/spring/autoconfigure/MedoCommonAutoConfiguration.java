package medo.common.spring.autoconfigure;

import medo.common.spring.context.configuration.SpringContextHelperConfiguration;
import medo.common.spring.id.configuration.IdGeneratorConfiguration;
import medo.common.spring.jackson.JacksonConfiguration;
import medo.common.spring.transactional.configuration.TransactionHelperConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    SpringContextHelperConfiguration.class,
    IdGeneratorConfiguration.class,
    JacksonConfiguration.class,
    TransactionHelperConfiguration.class
})
public class MedoCommonAutoConfiguration {}
