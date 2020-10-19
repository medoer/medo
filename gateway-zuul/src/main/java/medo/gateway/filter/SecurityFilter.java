package medo.gateway.filter;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityFilter extends ZuulFilter {

    public SecurityFilter() {
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        String origin = request.getHeader("Origin");
        if (!isEmpty(origin)) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS, PUT, DELETE");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
                    "Accept, Content-Type, Content-Length, Cookie, Accept-Encoding, X-CSRF-Token, Authorization");
        }

        // Stop here if its Preflighted OPTIONS request
        if ("OPTIONS".equals(request.getMethod())) {
            return null;
        }

        // TODO
//        if (!envConfig.isDebug()) {
//            // Check if secure
//            boolean isSecure = request.isSecure();
//            if (!isSecure) {
//                // Check if frontend proxy proxied it
//                if ("https".equals(request.getHeader("X-Forwarded-Proto"))) {
//                    isSecure = true;
//                }
//            }
//
//            // If not secure, then redirect
//            if (!isSecure) {
//                log.info("Insecure quest in uat&prod environment, redirect to https");
//                try {
//                    URI redirectUrl = new URI("https",
//                            request.getServerName(),
//                            request.getRequestURI(), null);
//                    response.sendRedirect(redirectUrl.toString());
//                } catch (URISyntaxException | IOException e) {
//                    log.error("fail to build redirect url", e);
//                }
//                return null;
//            }
//
//            // HSTS - force SSL
//            response.setHeader("Strict-Transport-Security", "max-age=315360000; includeSubDomains; preload");
//            // No iFrames
//            response.setHeader("X-Frame-Options", "DENY");
//            // Cross-site scripting protection
//            response.setHeader("X-XSS-Protection", "1; mode=block");
//        }
        return null;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

}
