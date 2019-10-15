package com.wjsy.wjsygatetwayzuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.wjsy.wjsygatetwayzuul.constant.NotFilterUrlList;
import com.wjsy.wjsygatetwayzuul.remote.AccountFeign;
import com.wjsy.wjsygatetwayzuul.remote.dto.RestResponse;
import com.wjsy.wjsygatetwayzuul.remote.dto.UserAccessDto;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

public class AccessFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

//    @Autowired
//    private RedisUtils redisUtils;

    @Autowired
    private AccountFeign accountFeign;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
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
        String baseUri = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();
        System.out.println(baseUri);

//        RestResponse restResponse = accountFeign.getUserAccess(accessToken);
//        UserAccessDto accessDto = new UserAccessDto();
//
//        try{
//            BeanUtils.populate(accessDto,(Map) restResponse.get("accessDto"));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        if(accessDto.getUserId() !=null && accessDto.getUserId().equals("1")){
//            return null;
//        }
//        List<String> permsSet = accessDto.getPermsSet();
//        String uri = request.getRequestURI();
//        uri = uri.replace("/api","");
//        System.out.println("uri>>>"+uri);
//        List<String> notfilterurls = NotFilterUrlList.urls();
//        if(notfilterurls.contains(uri)){
//            return  null;
//        }
//        if (Objects.nonNull(permsSet) && permsSet.size() > 0 && permsSet.contains(uri)) {
//            ctx.set("isSuccess", true);// 设值，让下一个Filter看到上一个Filter的状态
//        } else {
//            ctx.setSendZuulResponse(false);
//            ctx.setResponseStatusCode(401);
//            return null;
//        }


        /*if(StringUtils.isEmpty(accessToken)) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }

        Object o = redisUtils.get(accessToken);
        if (o == null) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }*/

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
