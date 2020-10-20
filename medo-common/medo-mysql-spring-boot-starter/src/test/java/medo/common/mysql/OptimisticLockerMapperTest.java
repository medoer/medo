package medo.common.mysql;

import medo.common.mysql.domain.model.OptimisticLockerDomain;
import medo.common.mysql.mapper.OptimisticLockerMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class OptimisticLockerMapperTest {

    @Autowired private OptimisticLockerMapper optimisticLockerMapper;

    @Test
    public void testUpdateFailed() {
        OptimisticLockerDomain optimisticLockerDomain = new OptimisticLockerDomain();
        optimisticLockerDomain.setName("test");
        optimisticLockerMapper.insert(optimisticLockerDomain);

        Long id = optimisticLockerDomain.getId();

        OptimisticLockerDomain domainToBeUpdate = new OptimisticLockerDomain();
        domainToBeUpdate.setName("test2");
        domainToBeUpdate.setId(id);
        domainToBeUpdate.setVersion(0);
        Assert.assertEquals(0, optimisticLockerMapper.updateById(domainToBeUpdate));
    }
}
