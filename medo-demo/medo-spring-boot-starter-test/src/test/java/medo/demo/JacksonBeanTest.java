package medo.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JacksonBeanTest {

    @Autowired private Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer;

    @Autowired private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

    @Test
    public void testJacksonEnumToStringConfig() {
        Assert.assertNotNull(jackson2ObjectMapperBuilderCustomizer);
    }
}
