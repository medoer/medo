package medo.common.mysql.domain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import medo.common.mysql.model.BaseModel;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test")
public class Test extends BaseModel<Model<?>> {

    private static final long serialVersionUID = -8185413579135897885L;
}
