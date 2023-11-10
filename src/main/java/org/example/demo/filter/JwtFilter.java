package org.example.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.util.JwtUtil;
import org.example.demo.util.Utils;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class JwtFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setCharacterEncoding("UTF-8");
        //获取url
        String url = httpServletRequest.getRequestURI();
        log.info("url:{}", url);
        //判断是否包含login/register请求,是就放行
        if (Utils.urlCheck(url)) {
            filterChain.doFilter(request, response);
            return;
        }
        //获取token
        String token = httpServletRequest.getHeader("token");
        //判断token是否存在
        if (!StringUtils.hasLength(token)) {
            log.error("token不存在");
            return;
        }
        //解析token是否存在
        if (JwtUtil.verifyToken(token)) {
            log.info("令牌合法，放行:{}", token);
            filterChain.doFilter(request, response);
        } else return;
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
