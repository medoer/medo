package medo.common.auth.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import medo.common.mysql.model.BaseModel;

/**
 * 角色
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class SysRole extends BaseModel<Model<?>> {
    private static final long serialVersionUID = 4497149010220586111L;
    private String code;
    private String name;
    private Long userId;
}
