package medo.common.mysql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import medo.common.mysql.domain.model.*;
import medo.common.mysql.mapper.DateMapper;
import medo.common.mysql.mapper.TestMapper;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * mybatis plus crud demo
 * <a>https://github.com/baomidou/mybatis-plus-samples/blob/master/mybatis-plus-sample-crud/src/test/java/com/baomidou/mybatisplus/samples/crud/SampleTest.java</a>
 */
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisPlusPageTest {

    @Autowired private DateMapper dateMapper;

    @Autowired private TestMapper testMapper;

    @Test
    public void testPageWithGroupBy() {
        IPage<DateDomain> page = new Page<>();
        //  fill the warehouse by mybatis plus tenant plugin
        LambdaQueryWrapper<DateDomain> queryWrapper =
                new QueryWrapper<DateDomain>()
                        .lambda()
                        .groupBy(DateDomain::getId)
                        .orderByDesc(DateDomain::getCreateTime);
        IPage<DateDomain> dateDomainIPage = dateMapper.selectPage(page, queryWrapper);
        Assertions.assertThat(dateDomainIPage.getTotal()).isGreaterThan(1);
    }

    @Test
    public void getPageWithJoin() {
        IPage<medo.common.mysql.domain.model.Test> page = new Page<>();
        //  fill the warehouse by mybatis plus tenant plugin
        LambdaQueryWrapper<medo.common.mysql.domain.model.Test> queryWrapper =
                new QueryWrapper<medo.common.mysql.domain.model.Test>()
                        .lambda()
                        .groupBy(medo.common.mysql.domain.model.Test::getId);
        testMapper.selectLeftJoin(page);
        //        Assertions.assertThat(dateDomainIPage.getTotal()).isGreaterThan(1);
    }
}
