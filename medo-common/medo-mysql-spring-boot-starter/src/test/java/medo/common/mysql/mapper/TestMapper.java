package medo.common.mysql.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import medo.common.mysql.domain.model.Test;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * test
 *
 * @author: bryce
 * @date: 2020-08-04
 */
@Mapper
public interface TestMapper extends SuperMapper<Test> {

    @Select("select t1.* from test t1 left join test t2 on t1.id = t2.id")
    IPage<Test> selectLeftJoin(IPage<Test> page);

}
