package medo.common.spring.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/** TODO lombok 在 idea 环境中没有生效，在该类中添加必要的日志 */
public class RequestContextHelper {

    public static HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }

    public static HttpServletResponse getHttpServletResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getResponse();
        }
        return null;
    }

    public static String getHeader(String name) {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return null;
        }
        return request.getHeader(name);
    }

    public static <T> T getAttrribute(String name) {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return null;
        }
        return (T) request.getAttribute(name);
    }

    public static void setAttribute(String name, Object value) {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return;
        }
        request.setAttribute(name, value);
    }
}
