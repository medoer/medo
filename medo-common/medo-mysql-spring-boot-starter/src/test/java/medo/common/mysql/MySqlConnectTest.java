package medo.common.mysql;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import medo.common.mysql.mapper.TestMapper;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class MySqlConnectTest {

    @Autowired
    private TestMapper testMapper;

    /**
     * rollback 未生效
     * 
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        medo.common.mysql.domain.model.Test test = new medo.common.mysql.domain.model.Test();
        testMapper.insert(test);
        medo.common.mysql.domain.model.Test test2 = testMapper.selectById(test.getId());
        Assert.assertTrue(test2.getId().equals(test.getId()));
    }

}
