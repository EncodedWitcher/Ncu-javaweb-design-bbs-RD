package cn.byssted.bbs.bbsrd.interceptor;

import cn.byssted.bbs.bbsrd.annotation.AdminRequired;
import cn.byssted.bbs.bbsrd.annotation.AuthRequired;
import cn.byssted.bbs.bbsrd.common.Result;
import cn.byssted.bbs.bbsrd.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

/**
 * JWT认证拦截器
 * 统一处理用户认证和权限验证
 */
@Component
public class JwtAuthInterceptor implements HandlerInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthInterceptor.class);
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是方法处理器，直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Class<?> clazz = handlerMethod.getBeanType();
        
        // 检查方法或类上是否有认证注解
        boolean needAuth = method.isAnnotationPresent(AuthRequired.class) || 
                          clazz.isAnnotationPresent(AuthRequired.class);
        boolean needAdmin = method.isAnnotationPresent(AdminRequired.class) || 
                           clazz.isAnnotationPresent(AdminRequired.class);
        
        // 如果不需要认证，直接放行
        if (!needAuth && !needAdmin) {
            return true;
        }
        
        // 获取Authorization头
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("请求缺少有效的Authorization头: {}", request.getRequestURI());
            writeErrorResponse(response, Result.unauthorized("请先登录"));
            return false;
        }
        
        try {
            // 提取JWT Token
            String token = authHeader.substring(7);
            
            // 验证Token是否过期
            if (jwtUtil.isTokenExpired(token)) {
                logger.warn("Token已过期: {}", request.getRequestURI());
                writeErrorResponse(response, Result.unauthorized("登录已过期，请重新登录"));
                return false;
            }
            
            // 获取用户信息
            Long userId = jwtUtil.getUserIdFromToken(token);
            String email = jwtUtil.getUsernameFromToken(token);
            
            // 将用户信息存储到请求属性中，供Controller使用
            request.setAttribute("userId", userId);
            request.setAttribute("userEmail", email);
            
            // 如果需要管理员权限，进行额外验证
            if (needAdmin) {
                String isAdmin = jwtUtil.getIsAdminFromToken(token);
                if (!"1".equals(isAdmin)) {
                    logger.warn("用户{}尝试访问管理员接口: {}", email, request.getRequestURI());
                    writeErrorResponse(response, Result.forbidden("只有管理员可以访问此接口"));
                    return false;
                }
                request.setAttribute("isAdmin", true);
            }
            
            logger.debug("用户{}通过认证访问: {}", email, request.getRequestURI());
            return true;
            
        } catch (Exception e) {
            logger.error("Token验证失败: {}, 错误: {}", request.getRequestURI(), e.getMessage());
            writeErrorResponse(response, Result.unauthorized("Token验证失败，请重新登录"));
            return false;
        }
    }
    
    /**
     * 写入错误响应
     */
    private void writeErrorResponse(HttpServletResponse response, Result<?> result) throws Exception {
        response.setStatus(HttpServletResponse.SC_OK); // 统一返回200状态码，错误信息在响应体中
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
