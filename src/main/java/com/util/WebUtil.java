package com.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class WebUtil {
    /**
     * 获取request的utf-8
     * @param request
     * @param name
     * @return
     */
    public static String getParameter(HttpServletRequest request,String name){
        String ans = request.getParameter(name);
        if(ans!=null)
        return StringUtil.getUTF_8(ans);
        else return null;
    }

    /**
     *将传来的json参数转换成java对象
     * @param request
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getObjectParameter(HttpServletRequest request,String name,Class<T> clazz){
        return JSON.parseObject(getParameter(request, name), clazz);
    }

    public static String getCookie(HttpServletRequest servletRequest, String keyname){
        Cookie[] cookies = servletRequest.getCookies();
        if(cookies==null){
            return null;
        }
        for(Cookie cookie: cookies){
            if(keyname.equals(cookie.getName())){
                return  cookie.getValue();
            }
        }
        return null;
    }

}
