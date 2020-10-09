package medo.common.mysql.domain.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import medo.common.mysql.model.BaseModel;

import java.time.LocalDateTime;


@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test")
public class DateDomain extends BaseModel {

}
