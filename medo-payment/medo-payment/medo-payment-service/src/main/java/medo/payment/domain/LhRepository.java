package medo.payment.domain;

import medo.common.mysql.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface LhRepository extends SuperMapper<Ll> {}
