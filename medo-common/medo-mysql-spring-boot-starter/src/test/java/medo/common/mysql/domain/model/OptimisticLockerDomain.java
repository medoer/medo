package medo.common.mysql.domain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import medo.common.mysql.model.BaseModel;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test")
public class OptimisticLockerDomain extends BaseModel {

    private String name;

    @Version private Integer version;
}
