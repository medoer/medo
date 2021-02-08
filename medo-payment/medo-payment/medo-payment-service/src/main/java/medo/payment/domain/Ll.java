package medo.payment.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import medo.common.mysql.model.BaseModel;

/** @Author: yangcj @Date: 2021/2/8 06:44 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "lh", autoResultMap = true)
public class Ll extends BaseModel<Ll> {

    private String type;
}
