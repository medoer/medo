package medo.common.mysql;

import medo.common.core.context.TenantContextHolder;
import medo.common.mysql.mapper.DateMapper;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TenantTest {

    @Autowired private DateMapper dateMapper;

    @Before
    public void setUp() {
        TenantContextHolder.setTenant("test");
    }

    @After
    public void post() {
        TenantContextHolder.clear();
    }

    @Test(expected = RuntimeException.class)
    public void testTenant() {
        String tenant = TenantContextHolder.getTenant();
        Assertions.assertThat(tenant).isNotNull();
        dateMapper.selectById("tes");
    }

    @Test
    public void testDelete() {
        dateMapper.deleteById(-1L);
    }
}
