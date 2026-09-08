package com.jkweilai.config.handler;

import com.jkweilai.constant.Constants;
import com.jkweilai.model.TUser;
import com.jkweilai.result.R;
import com.jkweilai.service.RedisService;
import com.jkweilai.util.JSONUtils;
import com.jkweilai.util.JWTUtils;
import com.jkweilai.util.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //登录成功，执行该方法，在该方法中返回json给前端，就行了
        TUser tUser = (TUser) authentication.getPrincipal();

        String userJson = JSONUtils.toJSON(tUser);


        String jwt = JWTUtils.createJWT(userJson);

        redisService.setValue(Constants.REDIS_JWT_KEY + tUser.getId(), jwt);

        //登录成功的统一结果
        R result = R.OK(tUser);

        //把R对象转成json
        String resultJSON = JSONUtils.toJSON(result);

        //把R以json返回给前端
        ResponseUtils.write(response, resultJSON);
    }
}
