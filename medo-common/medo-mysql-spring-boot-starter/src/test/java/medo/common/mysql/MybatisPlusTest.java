package medo.common.mysql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.time.LocalDateTime;
import medo.common.mysql.domain.model.*;
import medo.common.mysql.mapper.DateMapper;
import medo.common.mysql.mapper.EnumMapper;
import medo.common.mysql.mapper.ValueObjectMapper;
import org.junit.Assert;
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
public class MybatisPlusTest {

    @Autowired private DateMapper dateMapper;

    @Autowired private EnumMapper enumMapper;

    @Autowired private ValueObjectMapper valueObjectMapper;

    @Test
    public void testInsertLocalDateTime() {
        DateDomain dateDomain = DateDomain.builder().build();
        dateDomain.setCreateTime(LocalDateTime.now());
        dateMapper.insert(dateDomain);
    }

    @Test
    public void testInsertLocalDateTimeFillValue() {
        DateDomain dateDomain = DateDomain.builder().build();
        dateMapper.insert(dateDomain);
        DateDomain value = dateMapper.selectById(dateDomain.getId());
        Assert.assertNotNull(value);
        Assert.assertNotNull(value.getCreateTime());
        Assert.assertNotNull(value.getUpdateTime());
    }

    @Test
    public void testUpdateLocalDateTimeFillValue() {
        DateDomain dateDomain = DateDomain.builder().build();
        dateMapper.insert(dateDomain);
        dateDomain.setId(dateDomain.getId());
        dateMapper.updateById(dateDomain);
    }

    @Test
    public void testInsertWithEnum() {
        EnumDomain value = new EnumDomain();
        value.setTestEnum(TestEnum.VALUE);
        enumMapper.insert(value);
        EnumDomain enumDomain = enumMapper.selectById(value.getId());
        Assert.assertTrue(enumDomain.getTestEnum().equals(TestEnum.VALUE));
    }

    @Test
    public void testInsertWithValueObject() {
        ValueObjectDomain valueObjectDomainRequest = new ValueObjectDomain();
        ValueObject valueObject = new ValueObject();
        valueObject.setName("value");
        valueObject.setTestEnum(TestEnum.VALUE);
        valueObjectDomainRequest.setTestEnum(TestEnum.VALUE);
        valueObjectDomainRequest.setValueObject(valueObject);
        valueObjectMapper.insert(valueObjectDomainRequest);
        ValueObjectDomain valueObjectDomainRes =
                valueObjectMapper.selectById(valueObjectDomainRequest.getId());
        Assert.assertTrue(valueObject.equals(valueObjectDomainRes.getValueObject()));
        Assert.assertTrue(TestEnum.VALUE.equals(valueObjectDomainRes.getTestEnum()));
    }

    @Test
    public void testDelete() {
        ValueObjectDomain valueObjectDomainRequest = new ValueObjectDomain();
        valueObjectMapper.insert(valueObjectDomainRequest);
        ValueObjectDomain valueObjectDomainRes =
                valueObjectMapper.selectById(valueObjectDomainRequest.getId());
        Assert.assertNotNull(valueObjectDomainRes);
        // delete by id
        valueObjectMapper.deleteById(valueObjectDomainRequest.getId());
        ValueObjectDomain valueObjectDomainRes2 =
                valueObjectMapper.selectById(valueObjectDomainRequest.getId());
        Assert.assertNull(valueObjectDomainRes2);

        valueObjectDomainRequest.setTestEnum(TestEnum.VALUE);
        valueObjectMapper.insert(valueObjectDomainRequest);
        // delete by wrapper
        LambdaQueryWrapper<ValueObjectDomain> queryWrapper =
                new QueryWrapper<ValueObjectDomain>()
                        .lambda()
                        .eq(ValueObjectDomain::getTestEnum, TestEnum.VALUE);
        valueObjectMapper.delete(queryWrapper);
        ValueObjectDomain valueObjectDomainRes3 =
                valueObjectMapper.selectById(valueObjectDomainRequest.getId());
        Assert.assertNull(valueObjectDomainRes3);
    }
}
