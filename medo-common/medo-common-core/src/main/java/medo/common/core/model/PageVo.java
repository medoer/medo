package medo.common.core.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author: bryce
 * @date: 2020-08-04
 * @param <T>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageVo<T> implements Serializable {
    private static final long serialVersionUID = -275582248840137389L;

    /** 总数 */
    private Long count;
    /** 是否成功：0 成功、1 失败 */
    private int code;
    /** 当前页结果集 */
    private List<T> data;
}
