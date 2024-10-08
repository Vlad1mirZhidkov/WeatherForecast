package com.example.demo.utils;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class RequestUtil {

    public static void logRequestDetails(HttpServletRequest request) {
        // Log basic request info
        System.out.println("Request Method: " + request.getMethod());
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Request Protocol: " + request.getProtocol());

        // Log headers
        System.out.println("Headers:");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + ": " + request.getHeader(headerName));
        }

        // Log parameters
        System.out.println("Parameters:");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println(paramName + ": " + request.getParameter(paramName));
        }
    }
}
