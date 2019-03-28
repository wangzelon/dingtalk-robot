package com.whaa.dingtalk.interceptor;

import com.alibaba.fastjson.JSON;
import com.whaa.dingtalk.bean.RegisterDingTalk;
import com.whaa.dingtalk.core.WriteJsonFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by wangzelong 2019/3/22 16:46
 */

@Component
public class MsgInterceptor implements HandlerInterceptor {

    @Autowired
    private com.whaa.dingtalk.core.WriteJsonFile writeJsonFile;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String auth = request.getHeader("auth");
        if (StringUtils.isEmpty(auth)) {
            setApiResponse(response, 401, "认证失败");
            return false;
        }
        boolean flag = false;
        List<RegisterDingTalk> registerDingTalks = writeJsonFile.readFile();
        for (RegisterDingTalk registerDingTalk : registerDingTalks) {
            if (auth.equals(registerDingTalk.getAuthToken())) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            setApiResponse(response, 401, "认证失败");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public static void setApiResponse(HttpServletResponse response, Integer code, String msg) {
        Map result = new HashMap(2);
        result.put("code", code);
        result.put("msg", msg);
        String resultJson = JSON.toJSONString(result);
        response.setContentType("application/json;charset=utf-8");
        java.io.Writer out;
        try {
            out = response.getWriter();
            out.write(resultJson);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
