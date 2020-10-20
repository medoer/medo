package medo.common.auth.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import medo.common.mysql.model.BaseModel;

/** */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class SysUser extends BaseModel<Model<?>> {
    private static final long serialVersionUID = -5886012896705137070L;

    private String username;
    private String password;
    private String nickname;
    private String headImgUrl;
    private String mobile;
    private Integer sex;
    private Boolean enabled;
    private String type;
    private String openId;
    @TableLogic private boolean isDel;

    @TableField(exist = false)
    private List<SysRole> roles;

    @TableField(exist = false)
    private String roleId;

    @TableField(exist = false)
    private String oldPassword;

    @TableField(exist = false)
    private String newPassword;
}
