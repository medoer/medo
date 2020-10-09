package medo.common.mysql.domain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import medo.common.mysql.model.BaseModel;


@Data
public class ValueObject{

    private TestEnum testEnum;
    private String name;

}
