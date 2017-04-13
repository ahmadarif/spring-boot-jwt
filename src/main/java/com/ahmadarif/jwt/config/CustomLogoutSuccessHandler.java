package com.ahmadarif.jwt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Created by ARIF on 13-Apr-17.
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        String responseString = objectMapper.writeValueAsString(Collections.singletonMap("message", "Logout success"));
        response.setContentType("application/json");
        response.getWriter().write(responseString);

        // aktifkan jika aplikasi SPA yang disimpan aplikasinya disatu project dengan backend
        //response.sendRedirect("/");
    }

}