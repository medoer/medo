package tech.medo.demo.seata.account.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("account_tbl")
public class Account {
  @TableId
  private Long id;
  private String userId;
  private Integer money;
}
