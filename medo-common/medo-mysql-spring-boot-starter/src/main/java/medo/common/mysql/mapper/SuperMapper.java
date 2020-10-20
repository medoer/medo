package medo.common.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * mapper supper class
 *
 * @author: bryce
 * @date: 2020-08-04
 * @param <T>
 */
public interface SuperMapper<T> extends BaseMapper<T> {
    // 这里可以放一些公共的方法
}
