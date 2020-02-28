package com.imooc.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LoginInterceptor implements HandlerInterceptor {

    private List<String> unCheckUrls;

    public List<String> getUnCheckUrls() {
        return unCheckUrls;
    }

    public void setUnCheckUrls(List<String> unCheckUrls) {
        this.unCheckUrls = unCheckUrls;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String url = request.getRequestURI();
        url = url.replaceAll(request.getContextPath(), "");

        if (unCheckUrls.contains(url)) {
            return true;
        }
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/users/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
