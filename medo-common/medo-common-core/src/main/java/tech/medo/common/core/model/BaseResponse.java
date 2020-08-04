package tech.medo.common.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 
 * @author: bryce
 * @date: 2020-08-04
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 7497365562636065593L;

    private T data;
    private Integer code;
    private String msg;

    public static <T> BaseResponse<T> succeed(String msg) {
        return of(null, ResponseCodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> BaseResponse<T> succeed(T model, String msg) {
        return of(model, ResponseCodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> BaseResponse<T> succeed(T model) {
        return of(model, ResponseCodeEnum.SUCCESS.getCode(), "");
    }

    public static <T> BaseResponse<T> of(T datas, Integer code, String msg) {
        return new BaseResponse<>(datas, code, msg);
    }

    public static <T> BaseResponse<T> failed(String msg) {
        return of(null, ResponseCodeEnum.ERROR.getCode(), msg);
    }

    public static <T> BaseResponse<T> failed(T model, String msg) {
        return of(model, ResponseCodeEnum.ERROR.getCode(), msg);
    }
}
