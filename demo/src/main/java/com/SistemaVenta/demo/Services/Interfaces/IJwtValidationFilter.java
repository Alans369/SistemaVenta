package com.SistemaVenta.demo.Services.Interfaces;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IJwtValidationFilter {

    boolean validateAndSetAuthentication(String token, HttpServletRequest request);

    boolean isSignatureValid(String token);

    boolean isNotExpired(String token);

    String extractTokenFromCookie(HttpServletRequest request);

    void handleNoTokenOrInvalidToken(HttpServletRequest request, HttpServletResponse response,String type)throws IOException ;
}
