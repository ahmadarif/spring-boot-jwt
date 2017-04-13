package com.ahmadarif.jwt.config.jwt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ARIF on 13-Apr-17.
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Value("${jwt.header}")
    private String AUTH_HEADER;

    @Value("${jwt.cookie}")
    private String AUTH_COOKIE;

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Mengambil token dari HTTP Request
     *
     * @param request
     * @return
     */
    private String getToken(HttpServletRequest request) {
        // mengambil token dari cookie
        Cookie authCookie = getCookieValueByName(request, AUTH_COOKIE);
        if (authCookie != null) {
            return authCookie.getValue();
        }

        // mengambil token dari header
        // Authorization Bearer YOUR_TOKEN
        String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    /**
     * Mencari spesifk cookie dari HTTP Request
     *
     * @param request
     * @param name
     * @return
     */
    private Cookie getCookieValueByName(HttpServletRequest request, String name) {
        if (request.getCookies() == null) {
            return null;
        }
        for (int i = 0; i < request.getCookies().length; i++) {
            if (request.getCookies()[i].getName().equals(name)) {
                return request.getCookies()[i];
            }
        }
        return null;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String authToken = getToken(request);
        if (authToken != null) {
            // mengambil username berdasarkan token
            String username = tokenHelper.getUsernameFromToken(authToken);

            if (username != null) {
                // mengambil informasi user
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // membuat autentikasi
                TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                authentication.setToken(authToken);

                // daftarkan autentikasi ke session
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else {
            // menghapus security context
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }

}