package org.example.demo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.entity.ResResult;
import org.example.demo.enums.CommonEnum;
import org.example.demo.util.JwtUtil;
import org.example.demo.util.Utils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;

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
        if (JwtUtil.verifyToken(token)) {//token验证成功
            log.info("令牌合法，放行:{}", token);
            filterChain.doFilter(request, response);
        } else {//token失效
            // 设置响应的内容类型为 JSON
            response.setContentType("application/json");
            ResResult responseData = new ResResult<String>();
            responseData.setCode(CommonEnum.LOGIN_TOKEN_ERROR.getCode());
            responseData.setMsg(CommonEnum.LOGIN_TOKEN_ERROR.getMessage());

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(responseData);

            // 设置响应的字符编码和内容长度
            response.setCharacterEncoding("UTF-8");
            response.setContentLength(jsonResponse.length());

            // 将 JSON 字符串作为响应内容写入响应输出流
            PrintWriter writer = response.getWriter();
            writer.write(jsonResponse);
            writer.flush();
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
