package medo.common.mysql.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * db model base class
 *
 * @author: bryce
 * @date: 2020-08-04
 * @param <T>
 */
@Setter
@Getter
public class BaseModel<T extends Model<?>> extends Model<T> {

    private static final long serialVersionUID = 3199300382158796479L;

    /** primary key */
    @TableId private Long id;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
