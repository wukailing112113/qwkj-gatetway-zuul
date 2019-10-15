package com.wjsy.wjsygatetwayzuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TokenCheckFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

    /**
     * pre：可以在请求被路由之前调用
     * route：在路由请求时候被调用
     * post：在route和error过滤器之后被调用
     * error：处理请求时发生错误时被调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true; // 是否执行该过滤器，此处为true，说明需要过滤
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        InputStream stream = ctx.getResponseDataStream();

        try {
            String responseBody = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            log.info("method: {}, requestUrl: {}, responseBody: {}", request.getMethod(), request.getRequestURL().toString(), responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = request.getRequestURL().toString();
        if (url.indexOf("login") > -1 || url.indexOf("Login") > -1 || url.indexOf("captcha") > -1) {
            return null;
        }

        String accessToken = getHeadersInfo(request, "token");
        accessToken = accessToken == null ? request.getParameter("token") : accessToken;
        if(Objects.isNull(accessToken)){
            ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(401);// 返回错误码
            ctx.setResponseBody("{\"result\":\"username is not correct!\"}");// 返回错误内容
            ctx.set("isSuccess", false);
            return null;
        }

        return null;
    }

    private String getHeadersInfo(HttpServletRequest request, String header) {
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map.get(header.toLowerCase());
    }
}
