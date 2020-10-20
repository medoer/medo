package medo.common.mysql.mapper;

import medo.common.mysql.domain.model.OptimisticLockerDomain;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OptimisticLockerMapper extends SuperMapper<OptimisticLockerDomain> {}
