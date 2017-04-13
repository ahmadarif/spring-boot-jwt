package com.ahmadarif.jwt.config.jwt;

import com.ahmadarif.jwt.entity.User;
import com.ahmadarif.jwt.model.TokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Created by ARIF on 13-Apr-17.
 */
@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${jwt.expires_in}")
    private int EXPIRES_IN;

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    @Value("${app.user_cookie}")
    private String USER_COOKIE;

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        clearAuthenticationAttributes(request);

        User user = (User)authentication.getPrincipal();
        String jws = tokenHelper.generateToken(user.getUsername() );

        // membuat cookie token
        Cookie authCookie = new Cookie(TOKEN_COOKIE, jws);
        authCookie.setPath("/");
        authCookie.setHttpOnly(true);
        authCookie.setMaxAge(EXPIRES_IN);

        // membuat cookie dari username
        Cookie userCookie = new Cookie(USER_COOKIE, (user.getFirstname()));
        userCookie.setPath("/");
        userCookie.setMaxAge(EXPIRES_IN);

        // tambahkan cookie ke response
        response.addCookie(authCookie);
        response.addCookie(userCookie);

        // menyiapkan response yang akan dikirim
        TokenResponse tokenResponse = new TokenResponse(jws, EXPIRES_IN);
        String jwtResponse = objectMapper.writeValueAsString(tokenResponse);

        // kirim JWT sebagai response
        response.setContentType("application/json");
        response.getWriter().write(jwtResponse);
    }

}