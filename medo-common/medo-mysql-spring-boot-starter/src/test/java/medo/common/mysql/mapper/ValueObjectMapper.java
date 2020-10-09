package medo.common.mysql.mapper;

import medo.common.mysql.domain.model.ValueObjectDomain;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ValueObjectMapper extends SuperMapper<ValueObjectDomain>{
}