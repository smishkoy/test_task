package com.mishkoy.dataapi.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Value("${internal.token}")
    private String internalToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String incomingToken = request.getHeader("X-Internal-Token");

        if (incomingToken == null || !incomingToken.equals(internalToken)) {
            response.sendError(403, "Forbidden: Invalid Internal Token");
            return false;
        }
        return true;
    }
}
