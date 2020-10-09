package medo.common.mysql.domain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import medo.common.mysql.model.BaseModel;


@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "test", autoResultMap = true)
public class ValueObjectDomain extends BaseModel {

    // 使用 typehandler 需要指定 @TableName#autoResultMap 为 true
    @TableField(typeHandler = JacksonTypeHandler.class)
    private ValueObject valueObject;
    private TestEnum testEnum;

}
