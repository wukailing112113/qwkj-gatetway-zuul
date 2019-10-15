package com.wjsy.wjsygatetwayzuul.remote;

import com.wjsy.wjsygatetwayzuul.remote.dto.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("qwkj-account-center")
public interface AccountFeign {
    @ResponseBody
    @GetMapping(value = "/account/sys/usertoken/access/{token}")
    RestResponse getUserAccess(@PathVariable("token") String token);
}
