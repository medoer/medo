package medo.common.mysql.domain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import medo.common.mysql.model.BaseModel;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test")
public class DateDomain extends BaseModel {}
