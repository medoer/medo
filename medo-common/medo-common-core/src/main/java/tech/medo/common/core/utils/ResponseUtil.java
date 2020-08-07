package tech.medo.common.core.utils;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import tech.medo.common.core.model.BaseResponse;

/**
 * @author zlt
 * @date 2018/12/20
 */
public class ResponseUtil {
    private ResponseUtil() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * 通过流写到前端
     *
     * @param objectMapper 对象序列化
     * @param response
     * @param msg          返回信息
     * @param httpStatus   返回状态码
     * @throws IOException
     */
    public static void responseWriter(ObjectMapper objectMapper, HttpServletResponse response, String msg, int httpStatus) throws IOException {
        BaseResponse result = BaseResponse.succeedWith(null, httpStatus, msg);
        responseWrite(objectMapper, response, result);
    }

    /**
     * 通过流写到前端
     * @param objectMapper 对象序列化
     * @param response
     * @param obj
     */
    public static void responseSucceed(ObjectMapper objectMapper, HttpServletResponse response, Object obj) throws IOException {
        BaseResponse<?> result = BaseResponse.succeed(obj);
        responseWrite(objectMapper, response, result);
    }

    /**
     * 通过流写到前端
     * @param objectMapper
     * @param response
     * @param msg
     * @throws IOException
     */
    public static void responseFailed(ObjectMapper objectMapper, HttpServletResponse response, String msg) throws IOException {
        BaseResponse result = BaseResponse.failed(msg);
        responseWrite(objectMapper, response, result);
    }

    private static void responseWrite(ObjectMapper objectMapper, HttpServletResponse response, BaseResponse result) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try (
                Writer writer = response.getWriter()
        ) {
            writer.write(objectMapper.writeValueAsString(result));
            writer.flush();
        }
    }

    /**
     * webflux的response返回json对象
     */
//    public static Mono<Void> responseWriter(ServerWebExchange exchange, int httpStatus, String msg) {
//        BaseResponse result = BaseResponse.succeedWith(null, httpStatus, msg);
//        ServerHttpResponse response = exchange.getResponse();
//        response.getHeaders().setAccessControlAllowCredentials(true);
//        response.getHeaders().setAccessControlAllowOrigin("*");
//        response.setStatusCode(HttpStatus.valueOf(result.getResp_code()));
//        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
//        DataBufferFactory dataBufferFactory = response.bufferFactory();
//        DataBuffer buffer = dataBufferFactory.wrap(JSONObject.toJSONString(result).getBytes(Charset.defaultCharset()));
//        return response.writeWith(Mono.just(buffer)).doOnError((error) -> {
//            DataBufferUtils.release(buffer);
//        });
//    }
}
