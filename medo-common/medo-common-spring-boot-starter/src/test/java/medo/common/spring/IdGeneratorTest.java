package medo.common.spring;

import medo.common.core.id.IdGenerator;
import medo.common.core.id.Int128;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IdGeneratorTest {

    @Autowired private IdGenerator idGenerator;

    @Test
    public void testGenerateId() {
        Int128 id = idGenerator.generateId();
        Assert.assertNotNull(id);
    }
}
