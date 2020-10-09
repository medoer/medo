package medo.common.mysql.domain.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import medo.common.mysql.model.BaseModel;


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test")
public class EnumDomain extends BaseModel {

    private TestEnum testEnum;
}
